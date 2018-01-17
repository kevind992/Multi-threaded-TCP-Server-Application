//Author Kevin Delassus - G00270791
//Server Class

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class Server implements Serializable {

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) throws Exception {
		ServerSocket m_ServerSocket = new ServerSocket(2004, 10);
		int id = 0;
		while (true) {
			Socket clientSocket = m_ServerSocket.accept();
			ClientServiceThread cliThread = new ClientServiceThread(clientSocket, id++);
			cliThread.start();
			System.out.println("Server Running..");
		}
	}
}

class ClientServiceThread extends Thread {
	transient Socket clientSocket;
	transient String message;
	transient int clientID = -1;
	transient boolean running = true;
	transient ObjectOutputStream out;
	transient ObjectInputStream in;

	ClientServiceThread(Socket s, int i) {
		clientSocket = s;
		clientID = i;
	}
	//Method for sending Strings through a socket
	void sendMessage(String msg) {
		try {
			out.writeObject(msg);
			out.flush();

		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}
	//Method for sending integars through a socket
	void sendInt(int msg) {
		try {
			out.writeObject(msg);
			out.flush();
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}
	//Method for sending a boolean variable through a socket
	void sendLock(boolean msg) {
		try {
			out.writeObject(msg);
			out.flush();
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}

	@SuppressWarnings("resource")
	public void run() {
		System.out.println(
				"Accepted Client : ID - " + clientID + " : Address - " + clientSocket.getInetAddress().getHostName());
		try {
			out = new ObjectOutputStream(clientSocket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(clientSocket.getInputStream());
			System.out.println("Accepted Client : ID - " + clientID + " : Address - "
					+ clientSocket.getInetAddress().getHostName());

			do {

				//Setting Variables
				int id, logCount = 0;
				String n, pwd, add, p, a, w, h;
				
				//Creating 3 File objects. If a .txt file isnt in the filing system a new one is created
				File memberFile = new File("members.txt");
				File fitnessFile = new File("fitnessRecord.txt");
				File mealFile = new File("mealRecord.txt");
				
				//Creating 3 Arraylists for storing the information from the text files
				ArrayList<Member> memberList = new ArrayList<Member>();
				ArrayList<FitnessRecord> fitRec = new ArrayList<FitnessRecord>();
				ArrayList<MealRecord> mealRec = new ArrayList<MealRecord>();

				// Filling the ArrayList when the user connects
				try {
					memberList = fileRead(memberFile);
					fitRec = fileFitnessRead(fitnessFile);
					mealRec = fileMealRead(mealFile);
					
				} catch (Exception e) {

				}
				checkList(fitRec,mealRec);
				try {
					
					//Displaying the option menu
					sendMessage("\n==========================================================\n"
							+ "Please Select an Option"
							+ "\n==========================================================\n"
							+ "Select '1' if you are a New User\nSelect '2' if you are an Existing User\nSelect '3' to Exit"
							+ "\n==========================================================\n");
					message = (String) in.readObject();
					
					//If option 1 is selected the client is asked to register up with the system.
					if (message.compareToIgnoreCase("1") == 0) {

						System.out.println("Client has selected 'New User'.");
						//Client being asked for name
						sendMessage("Enter your Name: ");
						message = (String) in.readObject();
						n = message;
						//Client being asked for password
						sendMessage("Enter a Password: ");
						message = (String) in.readObject();
						pwd = message;
						//Client being asked for address
						sendMessage("Enter your Address: ");
						message = (String) in.readObject();
						add = message;
						//client being asked for ppsn
						sendMessage("Enter your PPSN: ");
						message = (String) in.readObject();
						p = message;
						//Client being asked for age
						sendMessage("Enter your Age: ");
						message = (String) in.readObject();
						a = message;
						//Client being asked to weight
						sendMessage("Enter your Weight: ");
						message = (String) in.readObject();
						w = message;
						//Client being asked to height
						sendMessage("Enter your Height: ");
						message = (String) in.readObject();
						h = message;
						
						//Generating a random number.
						id = randomId();
						
						//Creating a new member object with all the data that has come from the client
						Member member = new Member(id,0,0, n, pwd, add, p, a, w, h);

						//adding the member object to a ArrayList of members
						memberList.add(member);

						//That member list is then writen to a text file
						fileWrite(memberList, memberFile);

						// Success Messages for both Server and Client
						sendMessage("You have been Successfully Added.");
						System.out.println("Member added to file..");
						
						
					//If option 2 is selected the user is be prompted for a name and a password
					//If the name and password are correct the client will be send a option menu	
					} else if (message.compareToIgnoreCase("2") == 0) {
						
						//Setting Variables
						String logName, logPwd;
						boolean lock = false;
						int i = 0;
						int j = 0;
						int memberID = 0;
						int recMealCount = 0;
						int	recFitCount = 0;
						int size = 0;
						int size2 = 0;

						sendInt(logCount);

						System.out.println("Client has selected 'Existing User'.");
						//Asking Client for name and password
						do {
							
							System.out.println("User is being asked for details..");
							//Asking for name
							sendMessage("Enter your Name: ");
							message = (String) in.readObject();
							logName = message;
							//Asking for Password
							sendMessage("Enter your Password: ");
							message = (String) in.readObject();
							logPwd = message;

							System.out.println("Infomation is being checked..");
							
							//Checking members Array list for User
							for (Member m : memberList) {
								
								//If Correct User
								if (m.getName().equalsIgnoreCase(logName) && m.getPassword().equals(logPwd)) {
									System.out.println("User Entered correct Details..");
									//Setting lock to true
									lock = true;
									memberID = m.getId();
									size = m.getRecFitCount() -1;
									size2 = m.getRecMealCount() -1;
									j = i;
									//Breaking out of loop
									break;
								//If it isnt the correct user	
								} else {
									//Setting lock to false
									lock = false;
									
									logCount++;
								}
								i++;
							}
							//If lock is ture
							if (lock == true) {
								//Sending information back to client
								sendLock(lock);
								sendInt(logCount);
								break;
							//If lock is false	
							} else {
								//Sending information back to Client
								sendLock(lock);
								sendInt(logCount);
								//Telling Client that Password of name was wrong
								sendMessage("Incorrect Password or Name");
							}
						//The client is given 3 chances to login otherwise client is thrown back to main menu 	
						} while (logCount != 3);

						//if log in is less then 3
						if (logCount < 3) {
							
							//and lock is true
							if (lock == true) {
								//Success message is shown
								sendMessage("You have successfully logged in..");
								
								System.out.println("Displaying Option Menu..");

								do {
									// Asking User for which option they wish to select.
									sendMessage("\n==========================================================\n"
											+ "Please Select an Option"
											+ "\n==========================================================\n"
											+ "Select '1' to Add a Fitness Record\n"
											+ "Select '2' to Add a Meal Record\n"
											+ "Select '3' to View the Last 10 records on your File\n"
											+ "Select '4' to View the Last 10 Fitness Records\n"
											+ "Select '5' to Delete a record\n"
											+ "Select '6' to Exit to the Login Menu"
											+ "\n==========================================================");

									// Getting Response from client
									System.out.println("Asking for user info..");
									message = (String) in.readObject();
									
									//Option is to add a Fitness Record
									if (message.compareToIgnoreCase("1") == 0) {
										//Setting Variables
										String mode, duration;

										System.out.println("Client wants to add a Fitness Record..");

										//Asking client what type of exercise was preformed
										sendMessage("Please enter the type of Exercise preformed: ");
										message = (String) in.readObject();
										mode = message;

										//Asking client the duration of the exercise
										sendMessage("Please enter the duration of the Exercise: ");
										message = (String) in.readObject();
										duration = message;
										
										//Getting the date and time for a timestamp of the exercise
										String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
										
										recFitCount = memberList.get(j).getRecFitCount() + 1;
										memberList.get(j).setRecFitCount(recFitCount);
										
										//Creating a fitness object with all the data
										FitnessRecord fit = new FitnessRecord(memberID,recFitCount, timeStamp, mode, duration);
										
										//Adding to a fitness Array List
										fitRec.add(fit);
										
										//Writing the data to a file
										writeFitnessRecord(fitnessFile,fitRec);
										fileWrite(memberList,memberFile);
										//Sending Client a message saying the record was stored
										sendMessage("Record Added..");
									
									//Option 2 lets the Client create a Meal Record	
									} else if (message.compareToIgnoreCase("2") == 0) {
										//Setting Variables
										String type, discription;

										System.out.println("Client wants to add a Meal Record..");

										//Asking Client for the type of meal had
										sendMessage("Please enter the type of meal you had (ie. breakfast, lunch or dinner): ");
										message = (String) in.readObject();
										type = message;

										//Asking Client for a discription of the meal they had
										sendMessage("Please enter a discription of your meal: ");
										message = (String) in.readObject();
										discription = message;
										
										//Getting a timestamp
										String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
										
										recMealCount = memberList.get(j).getRecMealCount() + 1;
										memberList.get(j).setRecMealCount(recMealCount);
										
										//Creating a MealRecord Object with all the data 
										MealRecord meal = new MealRecord(memberID,recMealCount,timeStamp, type, discription);
										
										//Adding the meal object to an Array List
										mealRec.add(meal);
										
										//Writing the Array list to a text file
										writeMealRecord(mealFile,mealRec);
										fileWrite(memberList,memberFile);

										//Sending a message to the client saying the record has been added 
										sendMessage("Record Added..");
									
									//Option 3 lets the user View the last 10 Records created either in Fitness of Meal	
									} else if (message.compareToIgnoreCase("3") == 0) {
										System.out.println("Client wants to view the last 10 records..");
											
										//Creating a new ArrayList to add records of Meal and Fitness
										ArrayList<String> records = new ArrayList<String>();
										
										//Header
										sendMessage("\n==========================================================\n"
												+ "Last 10 Records on Your File"
												+ "\n==========================================================");
										//Getting the fitness records for the indivadual user
										for(int c = 0; c < fitRec.size(); c++) {
											if(memberList.get(j).getId() == fitRec.get(c).getId()) {
												//Storing the records into the ArrayList
												records.add("Fitness: " + fitRec.get(c).getMode() + " " + fitRec.get(c).getDuration());
											
												System.out.println("fitness record added..");
											}
											
										}
										//Getting the meal records for the indvidual user
										for(int c = 0; c < mealRec.size(); c++) {
											
											if(memberList.get(j).getId() == mealRec.get(c).getId()) {
												//Storing the records into the ArrayList
												records.add("Meal: " + mealRec.get(c).getTypeOfMeal() + " " + mealRec.get(c).getDiscription());
											
												System.out.println("Meal record added..");
											}
										}
										//Getting the total size of the ArrayList
										int total = records.size();
										//Sending the total to the Client
										sendInt(total);
										
										//If there is more then 10 records only 10 are shown
										if(total > 10) {
											for(int c = 0;c < 10; c++) {
												//Sending the records to the Client.
												sendMessage(records.get(c).toString());
											}
										//If there is less then 10 records then the total amount in the ArrayList are send	
										}else {
											for(int c = 0;c < total; c++) {
												//Sending the records to the Client.
												sendMessage(records.get(c).toString());
											}
										}
										//Clearing the ArrayList when finished
										records.clear();
									
									//If option 4 is selected the client will view the last 10 fitness records	
									} else if (message.compareToIgnoreCase("4") == 0) {
										System.out.println("Client wants to view last 10 records fitness records..");
										//Header
										sendMessage("\n==========================================================\n"
												+ "Last 10 Fitness Records on Your File"
												+ "\n==========================================================");
										
										//Sending size to client
										sendInt(size);
										//If there is more then 10 records only 10 are shown
										if(size > 10) {
											for(int c = 0; c < 10; c++) {
												if(memberList.get(j).getId() == fitRec.get(c).getId()) {
													//Sending data to Client
													sendMessage(fitRec.get(c).getRecCount() + " " + fitRec.get(c).getMode() + " " + fitRec.get(c).getDuration());
												}
											}
										//If there is less then 10 records then the total amount in the ArrayList are send	
										}else {
											for(int c = 0;c < fitRec.size(); c++) {
												if(memberList.get(j).getId() == fitRec.get(c).getId()) {
													//Sending data to Client
													sendMessage(fitRec.get(c).getRecCount() + " " + fitRec.get(c).getMode() + " " + fitRec.get(c).getDuration());
												}
											}
										}
										//recFitCount = 0;
									//If the Client selects 5 they will be able to delete a record	
									} else if (message.compareToIgnoreCase("5") == 0) {
										//
										System.out.println("Client want to delete a record..");
										//Sending Options
										sendMessage("\n==========================================================\n"
												+ "Please Select an Option"
												+ "\n==========================================================\n"
												+ "Select '1' if you wish to Delete a Fitness Record\n"
												+ "Select '2' if you wish to Delete a Meal Record\n"
												+ "Select '3' if you wish to Back to the Options"
												+ "\n==========================================================");
										//Recieving response
										message = (String) in.readObject();
										//Option 1 to delete a fitness record
										if(message.compareToIgnoreCase("1") == 0) {
											System.out.println("Client wishes to delete a Fitness Record..");
											
											//Client asked to enter the number of the record they wish to delete
											sendMessage("Please Enter the Number of the Record you wish to Delete: ");
											int remove = (int)in.readObject();
											//trying to delete record
											try {
												for(int c = 0;c < size; c++) {
													if(memberList.get(j).getId() == fitRec.get(c).getId()) {
														System.out.println(remove + " == " + fitRec.get(c).getRecCount());
														if(remove == fitRec.get(c).getRecCount()) {
															//Deleting record
															fitRec.remove(c);
															System.out.println("File deleted");
															break;
														}
													}
												}
												
												sendMessage("Record Deleted..");
											//If record cannot be deleted a message to sent to Client	
											}catch(IndexOutOfBoundsException ex) {
												sendMessage("Error: There is no Record with that Number..");
						
											}	
											
											//rewriting textfiles
											writeFitnessRecord(fitnessFile,fitRec);
											fileWrite(memberList,memberFile);
										
										//If Option 2 is selected the client can delete a meal record
										}else if(message.compareToIgnoreCase("2") == 0) {
											System.out.println("Client wishes to delete a Meal Record..");
											//Asking the client the number of the record they wish to delete.
											sendMessage("Please Enter the Number of the Record you wish to Delete: ");
											int remove = (int)in.readObject();
											//Trying to delete a record
											try {
												
												for(int c = 0;c < size; c++) {
													if(memberList.get(j).getId() == mealRec.get(c).getId()){
														if(remove == mealRec.get(c).getRecCount()) {
															//Deleteing the selected record
															mealRec.remove(c);
															break;
														}
													}
												}
											
												sendMessage("Record Deleted..");
											//If the record wasnt deleted then a message is sent to the client.	
											}catch(IndexOutOfBoundsException ex) {
												sendMessage("Error: There is no Record with that Number..");
											}
											
											//Rewriting textfiles
											writeMealRecord(mealFile,mealRec);
											fileWrite(memberList,memberFile);
											
											//message = (String) in.readObject();
										}else {
											System.out.println("Client wishes to go back to option menu..");
										}
										
									}else {
										System.out.println("Client wants to Exit...");
									}
								//Do while message not equaled to 6
								} while (!message.equals("6"));
								j = 0;
								i = 0;
							}
						} else {
							//Message telling client they have entered incorrectly 3 times
							sendMessage("You have incorrectly attempted to login 3 times..");
						}
					}
				} catch (ClassNotFoundException classnot) {
					System.err.println("Data received in unknown format");
				}
			//Do while message not equaled to 3	
			} while (!message.equals("3"));

			System.out.println(
					"Ending Client : ID - " + clientID + " : Address - " + clientSocket.getInetAddress().getHostName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//Method for writing Members to a textfile
	public void fileWrite(ArrayList<Member> mems, File file) throws FileNotFoundException, IOException {
		
		
		//Creating the FileOutputStream, BufferedOutputStream and ObjectOutputStream
		FileOutputStream fo = new FileOutputStream(file);
		BufferedOutputStream bout = new BufferedOutputStream(fo);
		ObjectOutputStream output = new ObjectOutputStream(bout);

		//Writing the objects to the textfile
		for (Member m : mems) {
			output.writeObject(m);
		}

		//When finished closing the last opened stream
		output.close();
	}
	//Method for reading Members from a textfile
	public ArrayList<Member> fileRead(File file) throws FileNotFoundException, IOException, ClassNotFoundException {

		//Creating a FileInputStream, BufferedInputStream and ObjectInputStream.
		FileInputStream fi = new FileInputStream(file);
		BufferedInputStream binpt = new BufferedInputStream(fi);
		ObjectInputStream input = new ObjectInputStream(binpt);

		//Creating a ArrayList
		ArrayList<Member> memberInput = new ArrayList<Member>();

		//trying to read from the textfile
		try {
			while (true) {
				//Reading and adding to the Arraylist
				Member m = (Member) input.readObject();
				memberInput.add(m);
			}
		} catch (EOFException ex) {
		}
		System.out.println("Member File Read Successful..");
		//Closing the last opened stream
		input.close();
		//Sending the arraylist
		return memberInput;
	}
	//Method for reading fitness records from a textfile
	public ArrayList<FitnessRecord> fileFitnessRead(File file)
			throws FileNotFoundException, IOException, ClassNotFoundException {

		//Creating a FileInputStream, BufferedInputStream and ObjectInputStream.
		FileInputStream fi = new FileInputStream(file);
		BufferedInputStream binpt = new BufferedInputStream(fi);
		ObjectInputStream input = new ObjectInputStream(binpt);

		//Creating a Arraylist
		ArrayList<FitnessRecord> fit = new ArrayList<FitnessRecord>();

		//Trying to read from a textfile
		try {
			while (true) {

				FitnessRecord f = (FitnessRecord) input.readObject();
				fit.add(f);
			}
		} catch (EOFException ex) {
		}
		System.out.println("Fitness File Read Successful..");
		input.close();
		return fit;
	}
	//Method for reading meal records from a textfile
	public ArrayList<MealRecord> fileMealRead(File file)
			throws FileNotFoundException, IOException, ClassNotFoundException {

		//Creating a FileInputStream, BufferedInputStream and ObjectInputStream.
		FileInputStream fi = new FileInputStream(file);
		BufferedInputStream binpt = new BufferedInputStream(fi);
		ObjectInputStream input = new ObjectInputStream(binpt);

		//Creating an arraylist
		ArrayList<MealRecord> meal = new ArrayList<MealRecord>();

		//Trying to read from a textfile
		try {
			while (true) {

				MealRecord m = (MealRecord) input.readObject();
				meal.add(m);
			}
		} catch (EOFException ex) {
		}
		System.out.println("Meal File Read Successful..");
		input.close();
		return meal;
	}
	//Method used for creating a random number for members
	public int randomId() {

		Random rand = new Random();

		//Creating a random number between 1 and 50
		int num = rand.nextInt(50) + 1;

		return num;
	}
	//Method for writing fitness records to a textfile
	public void writeFitnessRecord(File file,ArrayList<FitnessRecord> fit)
			throws FileNotFoundException, IOException {
		
		//Creating the FileOutputStream, BufferedOutputStream and ObjectOutputStream
		FileOutputStream fo = new FileOutputStream(file);
		ObjectOutputStream output = new ObjectOutputStream(fo);

		//Writing to a textfile
		for (FitnessRecord f: fit) {
			output.writeObject(f);
		}
		//Closing last opened stream
		output.close();
	}
	
	//Method for writing meal records to a textfile
	public void writeMealRecord(File file, ArrayList<MealRecord> mealRec)
			throws FileNotFoundException, IOException {
		
		//Creating the FileOutputStream, BufferedOutputStream and ObjectOutputStream
		FileOutputStream fo = new FileOutputStream(file);
		ObjectOutputStream output = new ObjectOutputStream(fo);

		//Writing to a textfile
		for(MealRecord m: mealRec) {
			output.writeObject(m);
		}
		//Closing last opened Stream.
		output.close();
	}
	//Method for checking what items are in meal and fitness Array Lists
	public void checkList(ArrayList<FitnessRecord> mem, ArrayList<MealRecord> meal) {

		for (FitnessRecord m : mem) {
			System.out.println(m.getId()+" "+ m.getRecCount() + " " + m.getMode());
		}
		for(MealRecord me : meal) {
			System.out.println(me.getId() +" "+ me.getRecCount() + " " + me.getDiscription());
		}
	}
}
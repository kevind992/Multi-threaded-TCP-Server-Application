//Author: Kevin Delassus - G00270791
//Client Class
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
	Socket requestSocket;
	ObjectOutputStream out;
	ObjectInputStream in;
	String message = "";
	String ipaddress;
	Scanner stdin;
	String fileName = "log.txt";
	int logCount = 0;
	boolean lock = false;

	Client() {
	}

	void run() {
		stdin = new Scanner(System.in);
		try {
			// 1. creating a socket to connect to the server
			System.out.println("Please Enter your IP Address");
			ipaddress = stdin.next();
			//System.out.println("Ip pre-entered..");
			//ipaddress = "35.195.167.218";
			requestSocket = new Socket(ipaddress, 2004);
			System.out.println("Connected to " + ipaddress + " in port 2004");
			// 2. get Input and Output streams
			out = new ObjectOutputStream(requestSocket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(requestSocket.getInputStream());
			// 3: Communicating with the server
			do {
				try {
					//Getting header from Server
					message = (String) in.readObject();
					System.out.println(message);
					message = stdin.nextLine();
					sendMessage(message);

					if (message.compareToIgnoreCase("1") == 0) {
						System.out.println("You are a New User.");

						// Asking User for name
						message = (String) in.readObject();
						System.out.println(message);
						message = stdin.nextLine();
						sendMessage(message);
						// Asking User for a password
						message = (String) in.readObject();
						System.out.println(message);
						message = stdin.nextLine();
						sendMessage(message);
						// Asking User for Address
						message = (String) in.readObject();
						System.out.println(message);
						message = stdin.nextLine();
						sendMessage(message);
						// Asking User for PPSN
						message = (String) in.readObject();
						System.out.println(message);
						message = stdin.nextLine();
						sendMessage(message);
						// Asking User for Age
						message = (String) in.readObject();
						System.out.println(message);
						message = stdin.nextLine();
						sendMessage(message);
						// Asking User for Weight
						message = (String) in.readObject();
						System.out.println(message);
						message = stdin.nextLine();
						sendMessage(message);
						// Asking User for Height
						message = (String) in.readObject();
						System.out.println(message);
						message = stdin.nextLine();
						sendMessage(message);

						// Final Server Message
						message = (String) in.readObject();
						System.out.println(message);

					} else if (message.compareToIgnoreCase("2") == 0) {

						System.out.println("You are an Existing User.");
						
						//Getting log count from client
						logCount = (int) in.readObject();

						do {

							// Asking User there name
							message = (String) in.readObject();
							System.out.println(message);
							message = stdin.nextLine();
							sendMessage(message);

							// Asking User for there a password
							message = (String) in.readObject();
							System.out.println(message);
							message = stdin.nextLine();
							sendMessage(message);

							// Reading lock from Server
							lock = (boolean) in.readObject();
							logCount = (int) in.readObject();

							if (lock == true) {
								break;
							} else {
								message = (String) in.readObject();
								System.out.println(message);

							}
						//loop while logCount not equaled to 3	
						} while (logCount != 3);

						if (lock == true) {

							// Success Message
							message = (String) in.readObject();
							System.out.println(message);

							do {

								// Getting Option Menu from Server and displaying it to user
								message = (String) in.readObject();
								System.out.println(message);
								// Getting user input
								message = stdin.nextLine();
								sendMessage(message);

								if (message.compareToIgnoreCase("1") == 0) {

									// Asking user for Exercise Preformed
									message = (String) in.readObject();
									System.out.println(message);
									message = stdin.nextLine();
									sendMessage(message);

									// Asking user for Exercise Duration
									message = (String) in.readObject();
									System.out.println(message);
									message = stdin.nextLine();
									sendMessage(message);

									message = (String) in.readObject();
									System.out.println(message);

								} else if (message.compareToIgnoreCase("2") == 0) {

									// Asking user for Exercise Preformed
									message = (String)in.readObject();
									System.out.println(message);
									message = stdin.nextLine();
									sendMessage(message);

									// Asking user for Exercise Duration
									message = (String)in.readObject();
									System.out.println(message);
									message = stdin.nextLine();
									sendMessage(message);

									message = (String)in.readObject();
									System.out.println(message);

								} else if (message.compareToIgnoreCase("3") == 0) {

									message = (String)in.readObject();
									System.out.println(message);
									int total = (int)in.readObject();

									//If greater then 10 client is only shown 10
									if (total > 10) {
										for(int c = 0; c <  10; c++) {
											message = (String)in.readObject();
											System.out.println(message);
										}
									//if less then 10 client is only shown amount that is in arraylist	
									} else {
										for(int c = 0; c <  total; c++) {
											message = (String)in.readObject();
											System.out.println(message);
										}
									}

								} else if (message.compareToIgnoreCase("4") == 0) {

									message = (String)in.readObject();
									System.out.println(message);
									
									int size = (int) in.readObject();

									if (size > 10) {
										for (int c = 0; c < 10; c++) {
											message = (String) in.readObject();
											System.out.println(message);
										}
									} else {
										for (int c = 0; c < size; c++) {
											message = (String) in.readObject();
											System.out.println(message);
										}
									}

								} else if (message.compareToIgnoreCase("5") == 0) {

									message = (String) in.readObject();
									System.out.println(message);
									message = stdin.nextLine();
									sendMessage(message);
									if (message.compareToIgnoreCase("1") == 0
											|| message.compareToIgnoreCase("2") == 0) {

										message = (String) in.readObject();
										System.out.println(message);
										int remove = stdin.nextInt();
										sendInt(remove);
										message = (String) in.readObject();
										System.out.println(message);
									}

								} else {
									System.out.println("you are going back to main menu");
								}
							} while (!message.equals("6"));

							System.out.println("You have exited..");

						} else {

							message = (String) in.readObject();
							System.out.println(message);

						}
					}
				} catch (ClassNotFoundException classNot) {
					System.err.println("data received in unknown format");
				}
			} while (!message.equals("3"));
		} catch (UnknownHostException unknownHost) {
			System.err.println("You are trying to connect to an unknown host!");
		} catch (IOException ioException) {
			ioException.printStackTrace();
		} finally {
			// 4: Closing connection
			try {
				in.close();
				out.close();
				requestSocket.close();
			} catch (IOException ioException) {
				ioException.printStackTrace();
			}
		}
	}

	void sendMessage(String msg) {
		try {
			out.writeObject(msg);
			out.flush();
			// System.out.println("client>" + msg);
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}

	void sendInt(int msg) {
		try {
			out.writeObject(msg);
			out.flush();
			// System.out.println("client>" + msg);
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}

	public static void main(String args[]) {
		Client client = new Client();
		client.run();
	}
}
# README.md

# Running the Program:
To run the program the following steps need to be do
•	The class files need to be added to a IDE. The class files in the sever folder need to be added to the google cloud sever while the class files in the client folder need to stay on your PC.
•	Once the files are where they are suppose to be you are ready to compile and run the program. To run the program you first need to get the sever class up and running. 
•	Once the server is running you can then run the client. 
•	A message is promote asking you to enter the I.P. of the sever you are trying to connect too
•	Once the client has connected to the sever you will be shown a option menu. 

•	Before you can progress onto Existing user, you first need to create a new user. You will be asked to fill in all you details.

•	Once you have filled in all your details you can now select 2. You will be asked for your name and password. Name is not case sensitive but password is case sensitive.

•	Once Logged in you will be show a new menu.

 

•	To add a Fitness Record you need to select 1. You will be asked to enter the type of exercise you preformed and you will be asked for the duration for the exercise.
•	To add a Meal Record you need to select 2. You will be asked to enter the Meal you had (i.e. Breakfast, Lunch or Dinner) and a description of the meal.
•	To View the last 10 records on you file you need to select 3. If there is less then 10 you will only be shown the amount of records saved.  

•	To view only the last 10 fitness records on file you need to select 4. Again if there is less then 10 you will only be shown the amount of records saved.  

•	To Delete a file you need to select 5. You will be asked whether you wish to delete a Fitness Record or a Meal Record. Once you have selected which one you wish to delete you will be ask to select the number of the record you wish to delete. Once you confirm you will either get a message saying that the deletion was successful or not successful.

•	You also have the option to navigate back through the program. You may want to add another user.

# Design Aspects:
One of my design aspects was to have object classes. I felt it would be easier when handling all the users information. There is 3 Object classes. Member, Fitness and Meals classes. These classes are used by the server class.
When writing to a textfile if there isn’t already a textfile created the program will create one for you. 
With storing the user data I decided to use File writing using ObjectOutputStreaming. With Object steaming you are obliged to serialize your objects. This meant that the user data wasn’t readable. Once the Program ran the text files would be deserialized using ObjectInputStreaming and stored into there Array Lists where they would be accessed by the program. Every time there was a change done to the data the files would be rewritten.
I tried to include as much error handling as I could but I ran out of time. If a user enters a wrong password or name, the user is told. The user is only aloud enter a wrong password 3 times. After the 3 times the user is thrown out of the login area. I also included a lot of try and catch statements.
When creating new Records , each record is given a timestamp. I would have liked to have done more with the timestamp but again I ran out of time. Each record has a timestamp.
When a new user is being created, they are given a unique id. This means that when they are creating a record that id is linked to the user id. It made it easier when displaying and deleting records.



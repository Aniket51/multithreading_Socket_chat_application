# multithreading_Socket_chat_application
Chatting application using multi threading and socket programming. One class implement the application using multithreading where one process id is  maintained. another class contains socket programming where different process id's  are maintained. 

Steps to Run the Java Code
Step 1: Extract the player.zip file to a directory of your choice.
Step 2: Open a terminal and navigate to the player folder.
Step 3: run command  “ mvn clean package”
Step 4: Choose the scenario you want to run:


Scenario 1: Sending Messages via Same Java Process
To run this scenario, execute the following command :  “./sameProcess.sh”
If you encounter permission issues, use:  “sh ./sameProcess.sh”
This script initiates communication between players within the same Java process using multithreading.

Scenario 2: Every Player in Separate Java Process(make sure port 5000 and 5001 are not busy)
To run this scenario, execute the following command: “./diffProcess.sh”
If you encounter permission issues, use: “sh ./diffProcess.sh”
This script launches separate Java processes for each player, communicating over sockets.
If you face of port already used then you can either kill the PID or change to available ports by making changes into diffProcess.sh


Scenario 1: Sending Messages via Same Java Process
PlayersCommunication: The PlayersCommunication class manages the coordination and communication between two Player instances within the same Java process. It initializes both players, starts them as threads (thread1 and thread2), and ensures they complete their tasks using thread synchronization (join()). This approach allows player1 and player2 to operate concurrently and handle inter-player communication or synchronization as necessary.
Player: The Player class facilitates synchronized communication between two players using shared resources. Each Player instance is initialized with a name, a synchronization lock (lock), and a reference to the other player (otherPlayer). It manages message passing via sendMessage() and receiveMessage() methods, ensuring thread-safe communication through synchronized blocks on the lock object.
	•	Initiator (isInitiator == true): Sends a predetermined number of initial messages and waits for responses. Each received message is processed by incrementing a counter and generating a response.
	•	Non-initiator (isInitiator == false): Waits to receive messages from the initiator, processing each message similarly.
The run() method, implemented from the Runnable interface, defines the main behavior of each player within its own thread. Upon exchanging a specified number of messages (MAX_MESSAGES), players terminate execution gracefully. This design ensures efficient and coordinated communication while adhering to thread safety principles.



Scenario 2: Every Player in Separate Java Process
PlayerMain: The PlayerMain class serves as the entry point of the application. It parses command line arguments to configure Player instances, starts player threads, and waits for their completion.
Player: The Player class utilizes socket programming for message exchange between two players, each running in a separate Java process. It manages sending and receiving messages:
	•	Initiator: Sends the initial message to start the exchange. Listens for responses, processes each message by appending a counter, and sends back a response.
	•	Non-initiator: Listens for incoming messages, processes each by appending a counter, and sends a response back.
The class ensures graceful connection closure after exchanging a fixed number of messages (10 messages in this case).

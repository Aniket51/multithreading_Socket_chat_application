package com.seperate.process.player;

import java.io.*;
import java.net.*;

public class Player implements Runnable {
    private final String name;
    private final int port;
    private final String otherPlayerHost;
    private final int otherPlayerPort;
    private int messageCounter = 0;
    private final boolean isInitiator;

    public Player(String name, int port, String otherPlayerHost, int otherPlayerPort, boolean isInitiator) {
        this.name = name;
        this.port = port;
        this.otherPlayerHost = otherPlayerHost;
        this.otherPlayerPort = otherPlayerPort;
        this.isInitiator = isInitiator;
    }
    //this method Print the messages passed in parameter
    private void sendMessage(String message) throws IOException {
        try (Socket socket = new Socket(otherPlayerHost, otherPlayerPort);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            out.println(message);
        }
    }
    // This method is to handle player 2 messages
    private void listenForMessages() {
        int retryCount = 0;
        while (retryCount < 5) {
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                while (true) {
                    try (Socket socket = serverSocket.accept();
                         BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                        String message = in.readLine();
                        if (message != null) {
                            processMessage(message);
                        }
                    }
                }
            } catch (IOException e) {
                System.err.println("Port " + port + " is already in use, retrying...");
                retryCount++;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
        if (retryCount >= 5) {
            System.err.println("Failed to bind to port " + port + " after 5 attempts. Exiting.");
        }
    }
    
    // This method handle initiator player 1 messages processing
    private void listenForMessages1() {
        int retryCount = 0;
        while (retryCount < 5) {
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                while (true) {
                    try (Socket socket = serverSocket.accept();
                         BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                        String message = in.readLine();
                        if (message != null) {
                            processMessage1(message);
                        }
                    }
                }
            } catch (IOException e) {
                System.err.println("Port " + port + " is already in use, retrying...");
                retryCount++;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
        if (retryCount >= 5) {
            System.err.println("Failed to bind to port " + port + " after 5 attempts. Exiting.");
        }
    }
    
    
    // processing and printing messages of player2
    private void processMessage(String message) {
      
        String response = message + messageCounter;
        System.out.println(name + ": " + response);

        if (messageCounter < 9) {
            try {
                sendMessage(response);
            } catch (IOException e) {
                e.printStackTrace();
            }
            messageCounter++;
        } else {
        	System.out.println("Sent 10 messages stopping the messages. Thank you !");
            System.exit(0);
        }
        
    }
    // processing messages of player 1
    private void processMessage1(String message) {
    	messageCounter++;
        String response = message + messageCounter;
        System.out.println(name + ": " + response);

        if (messageCounter < 10) {
            try {
                sendMessage(response);
            } catch (IOException e) {
                e.printStackTrace();
            }
            
        } else {
            System.out.println(name + ": Reached 10 messages. Stopping.");
            System.exit(0);
        }
        
    }
    //run method is over ridded to distinguish between if player is initiator and based on it 2 diffirent methods are getting called
    @Override
    public void run() {
        if (isInitiator) {
            try {
                String initialMessage = "Hello";
                System.out.println(name + " (Initiator): " + initialMessage);
                sendMessage(initialMessage);
                listenForMessages1();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            listenForMessages();
        }
    }
}

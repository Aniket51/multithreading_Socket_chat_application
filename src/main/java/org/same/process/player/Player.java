package org.same.process.player;

public class Player implements Runnable {
    private String name;
    private final Object lock;
    Player otherPlayer;
    private int messageCounter = 0;
    private final boolean isInitiator;
    private static final int MAX_MESSAGES = 10; // Each player sends/receives 4 messages
    private String message = null;

    // Constructor to initialize the Player instance
    public Player(String name, Object lock, Player otherPlayer, boolean isInitiator) {
        this.name = name;
        this.lock = lock;
        this.otherPlayer = otherPlayer;
        this.isInitiator = isInitiator;
    }

    // Method to receive a message and notify the waiting thread
    public void receiveMessage(String message) {
        synchronized (lock) {
            this.message = message;
            lock.notify();
        }
    }

    // Method to send a message to the other player
    private void sendMessage(String message) {
        otherPlayer.receiveMessage(message);
    }

    // Method to process the received message, increment counter, and prepare the next message
    private void processMessage() {
    	
        String response = message + messageCounter;
        System.out.println(name + ": " + response);
        messageCounter++;
        message = null; // Clear the message after processing
        sendMessage(response);
    }
 private void processMessage1() {
	    messageCounter++;
        String response = message + messageCounter;
        
        System.out.println(name + ": " + response);
        
        message = null; // Clear the message after processing
        sendMessage(response);
    }
 

    // Main run method executed by the thread
    @Override
    public void run() {
        try {
            if (isInitiator) {
                // Initiator sends the first message and processes responses
                String initialMessage = "Hello";
                System.out.println(name + ": " + initialMessage);
                // Change name to remove "(Initiator)" after first message
                name = name.replace(" (Initiator)", "");
                sendMessage(initialMessage);

                for (int i = 0; i < MAX_MESSAGES-1 ; i++) {
                    synchronized (lock) {
                        while (message == null) {
                            lock.wait();
                        }
                        processMessage1();
                    }
                }
                
            } else {
                // Non-initiator waits to receive messages and processes them
                for (int i = 0; i < MAX_MESSAGES; i++) {
                    synchronized (lock) {
                        while (message == null) {
                            lock.wait();
                        }
                        processMessage();
                    }
                }
                System.out.println("Sent and received " + MAX_MESSAGES + " messages. Stopping the messages... Thank you");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println(name + " was interrupted.");
        }
    }
}

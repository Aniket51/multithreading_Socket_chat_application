package org.same.process.player;

public class PlayersCommunication {
    public static void main(String[] args) {
    	// shared lock for implementation of synchronization.
        Object lock = new Object();

        // Create Player 2 (non-initiator) with a shared lock and no other player initially
        Player player2 = new Player("Player 2", lock, null, false);

        // Create Player 1 (initiator) with a shared lock and a reference to Player 2
        Player player1 = new Player("Player 1 (Initiator)", lock, player2, true);

        // Set Player 1 as the other player for Player 2
        player2.otherPlayer = player1;

        // Create threads for each player and start them
        Thread thread1 = new Thread(player1);
        Thread thread2 = new Thread(player2);

        thread1.start();
        thread2.start();

        // Wait for both threads to finish
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}


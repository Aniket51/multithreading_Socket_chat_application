package com.seperate.process.player;

public class PlayerMain {
    public static void main(String[] args) {
        if (args.length != 5) {
            System.out.println("Usage: PlayerMain <name> <port> <otherPlayerHost> <otherPlayerPort> <isInitiator>");
            return;
        }
        // Retrieve below command-line arguments and parse them
        String name = args[0];
        int port = Integer.parseInt(args[1]);
        String otherPlayerHost = args[2];
        int otherPlayerPort = Integer.parseInt(args[3]);
        boolean isInitiator = Boolean.parseBoolean(args[4]);
        
     // Create a new Player instance with the parsed arguments
        Player player = new Player(name, port, otherPlayerHost, otherPlayerPort, isInitiator);
        
        // Start a new thread to run the Player's logic concurrently
        new Thread(player).start();
    }
}
//Player2 5001 localhost 5002 false &
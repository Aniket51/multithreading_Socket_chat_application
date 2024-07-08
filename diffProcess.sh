#!/bin/bash

# Start Player 1
java -cp target/player-0.0.1-SNAPSHOT.jar com.seperate.process.player.PlayerMain Player1 5002 localhost 5001 true &
PLAYER1_PID=$!

# Start Player 2
java -cp target/player-0.0.1-SNAPSHOT.jar com.seperate.process.player.PlayerMain Player2 5001 localhost 5002 false &
PLAYER2_PID=$!
Exit 1
	
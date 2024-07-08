#!/bin/bash

cd "$(dirname "$0")"


mkdir -p target


javac -d target src/main/java/org/same/process/player/*.java

java -cp target org.same.process.player.PlayersCommunication
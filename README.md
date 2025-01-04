# Tic Tac Toe - Distributed Game

Welcome to the **Distributed Tic Tac Toe Game**! This project demonstrates a simple implementation of the classic Tic Tac Toe game using Java and sockets for communication. It supports two players, running on two separate windows on the same device, communicating over TCP.

## Features

- **Distributed Setup**: Two-player game with real-time communication over TCP sockets.
- **Simple UI**: Easy-to-use console-based interface.
- **Local Host Communication**: Players connect on the same device (localhost).
- **Turn-Based Gameplay**: Enforces turn-taking with clear win, lose, and draw states.
- **No Middleware**: Implements direct communication using Java sockets without any middleware layer.

## Prerequisites

- **Java JDK**: Version 8 or higher installed on your system.
- **IDE or Command Line**: Any Java-supported IDE (e.g., IntelliJ, Eclipse) or terminal for execution.

## How It Works

1. **Server**: Acts as the game manager, responsible for coordinating communication between the two clients (players).
2. **Clients**: Each player runs a client program that connects to the server to play the game.
3. **TCP Communication**: Messages are exchanged over a TCP connection to update the game state and synchronize player moves.

## Game Flow

1. Start the server program on your machine.
2. Open two client windows (Player 1 and Player 2) on the same device.
3. Player 1 starts the game by making the first move.
4. Players take turns until a win, loss, or draw condition is met.
5. The server informs both players of the result.

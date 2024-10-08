# Simple Java Chat Application

A basic command-line chat application implemented in Java using sockets. This application allows multiple users to connect to a chat server, send public messages, and exchange private messages.

## Features

- Multi-user chat functionality
- User authentication with unique usernames
- Public messaging
- Private messaging
- Server broadcasts for user join/leave events

## Prerequisites

- Java Development Kit (JDK) 8 or higher

## How to Run

### Starting the Server

1. Compile the Server class: javac Server.java
2. Run the java class: java Server

The server will start and listen on port 5000.

### Connecting Clients

1. Compile the Client class: javac Client.java
2. Run the Client (do this in separate terminal windows for each client): java Client
3. Follow the prompts to enter a username and start chatting.

## Usage

- To send a public message, simply type your message and press Enter.
- To send a private message, use the format: `/pm <username> <message>`
- To quit the chat, type `/quit`

## Code Structure

- `Server.java`: Contains the server-side logic, including the `ClientHandler` inner class.
- `Client.java`: Implements the client-side functionality.

## Features Explained

1. **User Authentication**: Users must provide a unique username to join the chat.
2. **Public Messaging**: All messages are broadcasted to all connected clients by default.
3. **Private Messaging**: Users can send private messages using the `/pm` command.
4. **Concurrent Connections**: The server uses threading to handle multiple client connections simultaneously.
5. **Error Handling**: Both server and client implement error handling to manage disconnections and other issues gracefully.

## Limitations and Potential Improvements

- The application uses a simple command-line interface. A graphical user interface could be implemented for better user experience.
- Messages are not persisted. Adding a database to store chat history could be a valuable enhancement.
- The current implementation doesn't support encryption. Adding secure communication would be important for a production environment.
- More advanced features like file sharing, emojis, or chat rooms could be added to expand functionality.

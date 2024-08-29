package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ClientHandler extends  Thread{
    private Socket socket;
    private PrintWriter out;
    BufferedReader in;
    private static Map<String, ClientHandler> clients = new ConcurrentHashMap<>();
    private String username;



    public ClientHandler (Socket socket){
        this.socket = socket;
    }

    public void run() {
        try {
            in = new BufferedReader(new BufferedReader(new InputStreamReader(socket.getInputStream())));
            out = new PrintWriter(socket.getOutputStream(), true);

            while (true) {
                out.println("Enter your username:");
                username = in.readLine();
                if (username == null) {
                    return;
                }

                synchronized (clients) {
                    if (!clients.containsKey(username)) {
                        clients.put(username, this);
                        break;
                    }
                }
            }
            out.println("Username accepted " + username);
            //broadcast
            broadcast("SERVER: " + username + "has joined the chat");
            while (true) {
                String input = in.readLine();
                if (input == null || input.equalsIgnoreCase("/quit")) {
                    return;
                }
                if (input.startsWith("/pm")) {
                    handlePrivateMessage(input);
                } else {
                    broadcast(username + ": " + input);
                }
            }
        } catch (IOException e) {
            System.err.println("Error handling client: " + e.getMessage());
        } finally {
            if (username != null) {
             clients.remove(username);
             broadcast("SERVER: " + username + " has left the chat");
            }
        }
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println("Error closing client socket: " + e.getMessage());
            }
        }

        private void handlePrivateMessage(String input) {
        String[] parts = input.split(" ", 3);
        if(parts.length < 3) {
            out.println("Usage: /pm <username> <message>");
            return;
        }
        String recipient = parts[1];
        String message = parts[2];
        ClientHandler clientRecipient =  clients.get(recipient);
        if (clientRecipient != null) {
            clientRecipient.out.println("(Private from " + username + "): " + message);
            out.println("(Private to " + recipient + "): " + message);
        } else {
            out.println("User " + recipient + "not found.");
        }
    }
    private void broadcast(String message) {
        for (ClientHandler client : clients.values()) {
            client.out.println(message);
        }
    }
}

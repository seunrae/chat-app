package chat;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.util.HashSet;
import java.util.Set;

public class ChatServer {
    private static final int PORT = 6000;


    public static void main(String[] args) throws IOException {
        System.out.println("Server is running on port " + PORT);
        try (ServerSocket listener = new ServerSocket(PORT)) {
            while (true) {
                try {
                    new ClientHandler(listener.accept()).start();
                } catch (IOException e){
                    System.err.println("Error accepting client connection: " + e.getMessage());
                }
            }
        }

    }
}

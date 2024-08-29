package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 6000;

    public static void main(String[] args) throws Exception {
        Socket socket = new Socket(SERVER_IP, SERVER_PORT);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Connected to chat server. Start typing messages.");

        // Thread for reading messages from the server
        new Thread(() -> {
            String message;
            try {
                while ((message = in.readLine()) != null) {
                    System.out.println(message);
                }
            } catch (IOException e) {
                System.out.println("Disconnected from the server");
            }
        }).start();

        String userInput;
        while ((userInput = consoleInput.readLine()) != null) {
            out.println(userInput);
            if (userInput.equalsIgnoreCase("exit")) {
                break;
            }
        }
        socket.close();
    }
}

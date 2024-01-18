package org.example.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class MySocketClient {
    private static final String END_OF_MESSAGE_MARK = "\n";
    private static final String EXIT_MESSAGE = "Q\n";

    public static void main(String[] args) {
        final int PORT = 9999;

            try (Socket socket = new Socket("localhost", PORT);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 OutputStream out = socket.getOutputStream();
                 BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in))) {
                System.out.printf("Connected to server with address: http://localhost:%s%n", PORT);

                while (true) {
                    System.out.println("Enter message to send (Q to finish the work):");

                    String messageToSend = consoleIn.readLine() + END_OF_MESSAGE_MARK;
                    out.write(messageToSend.getBytes());

                    String response = in.readLine();
                    System.out.printf("Server response: '%s'%n", response);
                    if (EXIT_MESSAGE.equals(messageToSend)) {
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}

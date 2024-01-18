package org.example.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MySocketServer {
    private static final String END_OF_MESSAGE_MARK = "\n";
    private static final String EXIT_MESSAGE = "Q";

    public static void main(String[] args) throws IOException {
        final int PORT = 9999;

        startServer(PORT);
    }

    private static void startServer(int port) throws IOException {
        BufferedReader in = null;
        OutputStream out = null;
        String receivedMessage;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started on port: " + port);
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected");

            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = clientSocket.getOutputStream();

            while (true) {
                receivedMessage = in.readLine();
                handleRequest(receivedMessage, out);
                if (EXIT_MESSAGE.equals(receivedMessage)) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }

    private static void handleRequest(String receivedMessage, OutputStream out) throws IOException {
        if (receivedMessage != null) {
            System.out.println("Client request message: " + receivedMessage);

            String serverResponse = receivedMessage.toUpperCase() + END_OF_MESSAGE_MARK;

            out.write(serverResponse.getBytes());
            out.flush();
        }
    }
}

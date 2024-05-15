package org.LanceOfDestiny.domain.network;
import java.io.*;
import java.net.Socket;
import java.net.ServerSocket;

    public class NetworkManager {
        private Socket socket;
        private ServerSocket serverSocket;
        private BufferedReader in;
        private PrintWriter out;

        public void hostGame(int port) throws IOException {
            serverSocket = new ServerSocket(port);
            socket = serverSocket.accept();
            setupStreams();
        }

        public void joinGame(String ip, int port) throws IOException {
            socket = new Socket(ip, port);
            setupStreams();
        }

        private void setupStreams() throws IOException {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        }

        public void sendGameState(String gameState) {
            out.println(gameState);
            System.out.printf("Sent state: %s%n", gameState);
        }

        public String receiveGameState() throws IOException {
            String gameState = in.readLine();
            System.out.printf("Received state: %s%n", gameState);
            return gameState;
        }

        public void closeConnection() throws IOException {
            if (in != null) in.close();
            if (out != null) out.close();
            if (socket != null) socket.close();
            if (serverSocket != null) serverSocket.close();
        }
    }



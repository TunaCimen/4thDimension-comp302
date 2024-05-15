package org.LanceOfDestiny.domain.network;

import org.LanceOfDestiny.domain.events.Events;
import org.LanceOfDestiny.domain.managers.BarrierManager;
import org.LanceOfDestiny.domain.managers.ScoreManager;
import org.LanceOfDestiny.domain.managers.SessionManager;
import org.LanceOfDestiny.ui.WindowManager;
import org.LanceOfDestiny.ui.Windows;

import java.io.*;
import java.net.Socket;
import java.net.ServerSocket;

public class NetworkManager {
    private Socket socket;
    private ServerSocket serverSocket;
    private BufferedReader in;
    private PrintWriter out;
    private static NetworkManager instance;

    private NetworkManager() {
        Events.SendChanceUpdate.addRunnableListener(() -> sendGameState("Chances: " + SessionManager.getInstance().getPlayer().getChancesLeft()));
        Events.SendScoreUpdate.addRunnableListener(() -> sendGameState("Score: " + ScoreManager.getInstance().getScore()));
        Events.SendBarrierCountUpdate.addRunnableListener(() -> sendGameState("Barrier Count: " + BarrierManager.barriers.size()));

        Events.TryJoiningSession.addListener(this::joinGame);

    }

    public static NetworkManager getInstance() {
        if (instance == null) {
            instance = new NetworkManager();
        }
        return instance;
    }

    public void hostGame(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        socket = serverSocket.accept();
        setupStreams();
    }


    public void joinGame(Object ip) {
        try {
            joinGame((String)ip, 12345);
        } catch (IOException e) {
            throw new RuntimeException(e);//TODO: A prompt about failed connection.
        }
    }

    public void joinGame(String ip, int port) throws IOException {
        socket = new Socket(ip, port);
        setupStreams();
    }

    private void setupStreams() throws IOException {
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        // Launch game view window after connecting
        WindowManager.getInstance().showWindow(Windows.GameViewWindow);
    }

    public void sendGameState(String gameState) {
        out.println(gameState);
    }

    public void receiveGameState() throws IOException {
        new Thread(() -> {
            try {
                String gameState;
                while ((gameState = in.readLine()) != null) {
                    handleReceivedGameState(gameState);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }


    private void handleReceivedGameState(String gameState) {
        if (gameState == null || gameState.isEmpty()) {
            return;
        }
        String[] parts = gameState.split(":");
        if (parts.length != 2) {
            System.out.println("Invalid game state received: " + gameState);
            return;
        }
        String eventType = parts[0].trim();
        String eventData = parts[1].trim();
        switch (eventType) {
            case "Chances":
                Events.ReceiveChanceUpdate.invoke(Integer.parseInt(eventData));
                break;
            case "Score":
                Events.ReceiveScoreUpdate.invoke(Integer.parseInt(eventData));
                break;
            case "Barrier Count":
                Events.ReceiveBarrierCountUpdate.invoke(Integer.parseInt(eventData));
                break;
            default:
                System.out.println("Unknown event type: " + eventType);
                break;
        }
    }

    public void closeConnection() throws IOException {
        if (in != null) in.close();
        if (out != null) out.close();
        if (socket != null) socket.close();
        if (serverSocket != null) serverSocket.close();
    }
}




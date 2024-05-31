package org.LanceOfDestiny.domain.network;

import org.LanceOfDestiny.domain.events.Event;
import org.LanceOfDestiny.domain.managers.BarrierManager;
import org.LanceOfDestiny.domain.managers.SessionManager;

import java.io.*;
import java.net.*;
import java.util.Enumeration;
import java.util.Objects;

public class NetworkManager {
    private Socket socket;
    private ServerSocket serverSocket;
    private BufferedReader in;
    private PrintWriter out;
    private static NetworkManager instance;
    private final NetworkEventHandler eventHandler;

    private NetworkManager() {
        this.eventHandler = new NetworkEventHandler();
        Event.TryJoiningSession.addListener(this::joinGame);
        Event.TryHostingSession.addRunnableListener(this::hostGame);
        Event.SendGameStarted.addRunnableListener(() -> out.println("STARTED"));
        Event.SingleplayerSelected.addRunnableListener(this::closeConnection);
        Event.ShowInitGame.addRunnableListener(this::closeConnection);
        Event.EndGame.addRunnableListener(this::closeConnection);
    }

    public static NetworkManager getInstance() {
        if (instance == null) {
            instance = new NetworkManager();
        }
        return instance;
    }

    public static void resetInstance() {
        if (instance != null) {
            instance.closeStreams();
        }
        instance = new NetworkManager();
    }

    public void hostGame(int port) {
        new Thread(() -> {
            try {
                serverSocket = new ServerSocket(port);
                socket = serverSocket.accept();
                setupStreams();
                Event.OtherPlayerJoined.invoke();
                out.println(BarrierManager.getInstance().serializeAllBarriers());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    public void hostGame() {
        try {
            InetAddress ipAddress = getIPAddress();
            if (ipAddress != null) {
                Event.SendIPAddress.invoke(ipAddress.getHostAddress());
                System.out.println("IP Address: " + ipAddress.getHostAddress());
            } else {
                System.out.println("No IP address found.");
            }
        } catch (SocketException e) {
            System.err.println("Unable to retrieve network interfaces.");
            e.printStackTrace();
        }
        hostGame(12345);
    }

    private static InetAddress getIPAddress() throws SocketException {
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        while (networkInterfaces.hasMoreElements()) {
            NetworkInterface networkInterface = networkInterfaces.nextElement();
            if (networkInterface.isLoopback() || !networkInterface.isUp()) {
                continue; // Skip loopback and inactive interfaces
            }

            Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
            while (inetAddresses.hasMoreElements()) {
                InetAddress inetAddress = inetAddresses.nextElement();
                if (!inetAddress.isLoopbackAddress() && inetAddress.isSiteLocalAddress()) {
                    return inetAddress; // Return the first non-loopback and site-local address
                }
            }
        }
        return null; // No appropriate IP address found
    }

    public void joinGame(Object ip) {
        try {
            joinGame((String) ip, 12345);
        } catch (IOException e) {
            Event.ErrorOccured.invoke("Failed to join session. Please check the IP address and try again."); // TODO: A prompt about failed connection.
        }

        System.out.println("Joined Successfully ");
    }

    public void joinGame(String ip, int port) throws IOException {
        socket = new Socket(ip, port);
        setupStreams();
        if (out == null) {
            Event.ErrorOccured.invoke("Failed to join session. Please check the IP address and try again.");
            return;
        }
        Event.Reset.invoke();
        Event.JoinedTheHost.invoke();
        BarrierManager.getInstance().loadBarriersFromString(in.readLine());
        Event.ReceiveBarrierCountUpdate.invoke(BarrierManager.getInstance().getBarriers().size());
        Event.ReceiveChanceUpdate.invoke(SessionManager.getInstance().getPlayer().getChancesLeft());
        new Thread(() -> {
            while (true) {
                try {
                    if (Objects.equals(in.readLine(), "STARTED")) {
                        Event.StartCountDown.invoke();
                        break;
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    private void setupStreams() throws IOException {
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    public void sendGameState(String gameState) {
        out.println(gameState);
    }

    public String receiveGameState() throws IOException {
        return in.readLine();
    }

    public NetworkEventHandler getEventHandler() {
        return eventHandler;
    }

    public void closeConnection() {
        if (out != null) {
            out.println("SHUTDOWN");
        }
        closeStreams();
    }

    public void closeStreams() {
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (socket != null) socket.close();
            if (serverSocket != null) serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            in = null;
            out = null;
            socket = null;
            serverSocket = null;
        }
    }
}

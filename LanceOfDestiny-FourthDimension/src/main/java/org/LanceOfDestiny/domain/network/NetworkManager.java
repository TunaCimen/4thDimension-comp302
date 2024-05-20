package org.LanceOfDestiny.domain.network;

import org.LanceOfDestiny.domain.events.Events;
import org.LanceOfDestiny.ui.UIUtilities.WindowManager;
import org.LanceOfDestiny.ui.UIUtilities.Windows;


import java.io.*;
import java.net.*;
import java.util.Enumeration;

public class NetworkManager {
    private Socket socket;
    private ServerSocket serverSocket;
    private BufferedReader in;
    private PrintWriter out;
    private static NetworkManager instance;
    private final NetworkEventHandler eventHandler;

    private NetworkManager() {
        this.eventHandler = new NetworkEventHandler();
        Events.TryJoiningSession.addListener(this::joinGame);
        Events.TryHostingSession.addRunnableListener(this::hostGame);
    }

    public static NetworkManager getInstance() {
        if (instance == null) {
            instance = new NetworkManager();
        }
        return instance;
    }

    public void hostGame(int port){
        new Thread(() -> {
            try{
                serverSocket = new ServerSocket(port);
                socket = serverSocket.accept();
                setupStreams();
            }catch(Exception e){
                throw new RuntimeException(e);
            }
        }).start();

        System.out.println("Hosted the game connect ");
    }

    public void hostGame(){

        System.out.println("here hosting a session niga");
        try {
            InetAddress ipAddress = getIPAddress();
            if (ipAddress != null) {
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
            joinGame((String)ip, 12345);
        } catch (IOException e) {
            throw new RuntimeException(e);//TODO: A prompt about failed connection.
        }

        System.out.println("Joined Successfuly ");
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

    public String receiveGameState() throws IOException {
        return in.readLine();
    }

    public NetworkEventHandler getEventHandler() {
        return eventHandler;

    }

    public void closeConnection() throws IOException {
        if (in != null) in.close();
        if (out != null) out.close();
        if (socket != null) socket.close();
        if (serverSocket != null) serverSocket.close();
    }
}
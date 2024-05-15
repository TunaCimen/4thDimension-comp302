package org.LanceOfDestiny;

import org.LanceOfDestiny.domain.network.NetworkManager;

import java.io.IOException;
import java.util.Scanner;

public class NetworkTest {
    public static void main(String[] args) {
        NetworkManager networkManager = NetworkManager.getInstance();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose mode: ");
        System.out.println("1. Host a Game");
        System.out.println("2. Join a Game");
        System.out.print("Enter choice (1 or 2): ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume the newline

        if (choice == 1) {
            System.out.print("Enter port to host on (default 12345): ");
            String portInput = scanner.nextLine();
            int port = portInput.isEmpty() ? 12345 : Integer.parseInt(portInput);

            try {
                networkManager.hostGame(port);
                System.out.println("Hosting game on port " + port);
                networkManager.receiveGameState();
                simulateGameStateExchange(networkManager, scanner);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (choice == 2) {
            System.out.print("Enter host IP (default 127.0.0.1): ");
            String hostIp = scanner.nextLine();
            hostIp = hostIp.isEmpty() ? "127.0.0.1" : hostIp;

            System.out.print("Enter host port (default 12345): ");
            String portInput = scanner.nextLine();
            int port = portInput.isEmpty() ? 12345 : Integer.parseInt(portInput);

            try {
                networkManager.joinGame(hostIp, port);
                System.out.println("Joined game at " + hostIp + ":" + port);
                networkManager.receiveGameState();
                simulateGameStateExchange(networkManager, scanner);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Invalid choice. Please restart the program and select either 1 or 2.");
        }
    }

    private static void simulateGameStateExchange(NetworkManager networkManager, Scanner scanner) {
        while (true) {
            String gameState = scanner.nextLine();
            networkManager.sendGameState(gameState);
        }
    }
}
package org.LanceOfDestiny.domain.network;

import org.LanceOfDestiny.domain.behaviours.MonoBehaviour;
import org.LanceOfDestiny.domain.events.Event;
import org.LanceOfDestiny.domain.managers.SessionManager;

import java.io.IOException;

public class NetworkBehavior extends MonoBehaviour {

    private final NetworkManager networkManager;
    private Thread updateThread;
    private volatile boolean running;

    public NetworkBehavior() {
        running = true;
        this.networkManager = NetworkManager.getInstance();
        Event.ShowInitGame.addRunnableListener(() -> stopUpdateThread());
    }

    @Override
    public void update() {
        if (updateThread == null) {
            startUpdateThread();
        }
    }

    private void startUpdateThread() {
        updateThread = new Thread(() -> {
            while (running) {
                try {
                    if (SessionManager.getInstance().getGameMode() == SessionManager.GameMode.SINGLEPLAYER) continue;
                    String gameState = networkManager.receiveGameState();
                    System.out.println("Received state" + gameState);
                    networkManager.getEventHandler().handleReceivedGameState(gameState);
                    System.out.println("Handled State");
                } catch (IOException e) {
                    running = false;
                }
            }
        });
        updateThread.start();
    }

    public void stopUpdateThread() {
        running = false;
    }
}

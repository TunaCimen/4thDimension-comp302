package org.LanceOfDestiny.domain.network;
import org.LanceOfDestiny.domain.behaviours.MonoBehaviour;
import org.LanceOfDestiny.domain.managers.SessionManager;

import java.io.IOException;

public class NetworkBehavior extends MonoBehaviour {

    private final NetworkManager networkManager;

    Thread updateThread;

    public NetworkBehavior()
    {
        this.networkManager = NetworkManager.getInstance();
        updateThread = new Thread(()->{
            while(true){
                try {
                    if(SessionManager.getInstance().getGameMode() == SessionManager.GameMode.SINGLEPLAYER)continue;
                    String gameState = networkManager.receiveGameState();
                    System.out.println("Recieved state");
                    if (gameState != null && !gameState.isEmpty()) {
                        networkManager.getEventHandler().handleReceivedGameState(gameState);
                        System.out.println("Handled State");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });
    }
    @Override
    public void update() {
        if(updateThread.isAlive())return;
        updateThread.start();
    }
}

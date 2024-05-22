package org.LanceOfDestiny.domain.network;
import org.LanceOfDestiny.domain.behaviours.MonoBehaviour;
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
                    String gameState = networkManager.receiveGameState();
                    if (gameState != null && !gameState.isEmpty()) {
                        networkManager.getEventHandler().handleReceivedGameState(gameState);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });
    }
    @Override
    public void update() {
        System.out.println("UPDATING NETWORK MANAGER");
        if(updateThread.isAlive())return;
        updateThread.start();
    }
}

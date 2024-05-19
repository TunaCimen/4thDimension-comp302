package org.LanceOfDestiny.domain.network;
import org.LanceOfDestiny.domain.behaviours.MonoBehaviour;
import java.io.IOException;

public class NetworkBehavior extends MonoBehaviour {

    private final NetworkManager networkManager;

    public NetworkBehavior() {
        this.networkManager = NetworkManager.getInstance();
    }

    @Override
    public void update() {
        super.update();
        try {
            String gameState = networkManager.receiveGameState();
            if (gameState != null && !gameState.isEmpty()) {
                networkManager.getEventHandler().handleReceivedGameState(gameState);
            }
        } catch (IOException e) {
            e.printStackTrace();
    }
    }
}

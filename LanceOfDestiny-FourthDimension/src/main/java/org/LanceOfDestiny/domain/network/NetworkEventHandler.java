package org.LanceOfDestiny.domain.network;

import org.LanceOfDestiny.domain.events.Events;
import org.LanceOfDestiny.domain.managers.BarrierManager;
import org.LanceOfDestiny.domain.managers.ScoreManager;
import org.LanceOfDestiny.domain.managers.SessionManager;

public class NetworkEventHandler {

    public NetworkEventHandler() {
        Events.SendChanceUpdate.addRunnableListener(() -> sendGameState("Chances: " + SessionManager.getInstance().getPlayer().getChancesLeft()));
        Events.SendScoreUpdate.addRunnableListener(() -> sendGameState("Score: " + ScoreManager.getInstance().getScore()));
        Events.SendBarrierCountUpdate.addRunnableListener(() -> sendGameState("Barrier Count: " + BarrierManager.barriers.size()));

    }

    public void sendGameState(String gameState) {
        NetworkManager.getInstance().sendGameState(gameState);
    }

    public void handleReceivedGameState(String gameState) {
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
}

package org.LanceOfDestiny.domain.network;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.events.Events;
import org.LanceOfDestiny.domain.managers.BarrierManager;
import org.LanceOfDestiny.domain.managers.ScoreManager;
import org.LanceOfDestiny.domain.managers.SessionManager;
import org.LanceOfDestiny.domain.spells.SpellActivation;
import org.LanceOfDestiny.domain.spells.SpellType;

public class NetworkEventHandler {

    public NetworkEventHandler() {
        Events.SendChanceUpdate.addRunnableListener(() -> sendGameState("Chances: " + SessionManager.getInstance().getPlayer().getChancesLeft()));
        Events.SendScoreUpdate.addRunnableListener(() -> sendGameState("Score: " + ScoreManager.getInstance().getScore()));
        Events.SendBarrierCountUpdate.addRunnableListener(() -> sendGameState("Barrier Count: " + BarrierManager.barriers.size()));
        Events.SendGameDataToLoad.addRunnableListener(() -> sendGameState("Game Data: " + BarrierManager.getInstance().serializeAllBarriers()));
        Events.SendPauseUpdate.addRunnableListener(() -> sendGameState("Pause Game: true"));
        Events.SendResumeUpdate.addRunnableListener(() -> sendGameState("Resume Game: true"));
        Events.SendDoubleAccelUpdate.addRunnableListener(()->sendGameState("Double Accel: true"));
        Events.SendHollowPurpleUpdate.addRunnableListener(()->sendGameState("Hollow Purple: true"));
        Events.SendInfiniteVoidUpdate.addRunnableListener(()->sendGameState("Infinite Void: true"));
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
            case "Game Data":
                Events.ReceiveGameDataToLoad.invoke(eventData);
                break;
            case "Pause Game":
                Events.PauseGame.invoke();
                break;
            case "Resume Game":
                Events.ResumeGame.invoke();
                break;
            case "Double Accel":
                new SpellActivation(SpellType.DOUBLE_ACCEL, Constants.SPELL_DURATION).activate();
                break;
            case "Hollow Purple":
                new SpellActivation(SpellType.HOLLOW_PURPLE, Constants.SPELL_DURATION).activate();
                break;
            case "Infinite Void":
                new SpellActivation(SpellType.INFINITE_VOID, Constants.SPELL_DURATION).activate();
                break;
            default:
                System.out.println("Unknown event type: " + eventType);
                break;
        }
    }
}

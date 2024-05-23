package org.LanceOfDestiny.domain.network;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.events.Event;
import org.LanceOfDestiny.domain.managers.BarrierManager;
import org.LanceOfDestiny.domain.managers.ScoreManager;
import org.LanceOfDestiny.domain.managers.SessionManager;
import org.LanceOfDestiny.domain.managers.Status;
import org.LanceOfDestiny.domain.spells.SpellActivation;
import org.LanceOfDestiny.domain.spells.SpellType;

public class NetworkEventHandler {

    private SessionManager sessionManager;
    public NetworkEventHandler() {
        sessionManager = SessionManager.getInstance();
        Event.SendChanceUpdate.addRunnableListener(() -> sendGameState("Chances: " + SessionManager.getInstance().getPlayer().getChancesLeft()));
        Event.SendScoreUpdate.addRunnableListener(() -> {
            sendGameState("Score: " + ScoreManager.getInstance().getScore());
            System.out.println("Send score update");
        });
        Event.SendBarrierCountUpdate.addRunnableListener(() -> sendGameState("Barrier Count: " + BarrierManager.getInstance().barriers.size()));
        Event.SendGameDataToLoad.addRunnableListener(() -> sendGameState("Game Data: " + BarrierManager.getInstance().serializeAllBarriers()));
        Event.SendPauseUpdate.addRunnableListener(() -> sendGameState("Pause Game: true"));
        Event.SendResumeUpdate.addRunnableListener(() -> sendGameState("Resume Game: true"));
        Event.SendDoubleAccelUpdate.addRunnableListener(()->sendGameState("Double Accel: true"));
        Event.SendHollowPurpleUpdate.addRunnableListener(()->sendGameState("Hollow Purple: true"));
        Event.SendInfiniteVoidUpdate.addRunnableListener(()->sendGameState("Infinite Void: true"));
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
        System.out.println("Event type: " + eventType);
        switch (eventType) {
            case "Chances":
                Event.ReceiveChanceUpdate.invoke(Integer.parseInt(eventData));
                break;
            case "Score":
                Event.ReceiveScoreUpdate.invoke(Integer.parseInt(eventData));
                break;
            case "Barrier Count":
                Event.ReceiveBarrierCountUpdate.invoke(Integer.parseInt(eventData));
                break;
            case "Game Data":
                Event.ReceiveGameDataToLoad.invoke(eventData);
                break;
            case "Pause Game":
                if (sessionManager.getStatus() == Status.PausedMode) {
                    return;
                }
                Event.PauseGame.invoke();
                break;
            case "Resume Game":
                if (sessionManager.getStatus() == Status.RunningMode) {
                    return;
                }
                Event.ResumeGame.invoke();
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

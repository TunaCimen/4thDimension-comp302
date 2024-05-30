package org.LanceOfDestiny.domain.managers;

import org.LanceOfDestiny.domain.behaviours.MonoBehaviour;
import org.LanceOfDestiny.domain.events.Event;

public class GameStateHandler extends MonoBehaviour {
    @Override
    public void update() {
       var barriersLeft = BarrierManager.getInstance().getBarriers().size();
       if (barriersLeft == 0) {
           Event.GameWon.invoke();
       }
       var chancesLeft = SessionManager.getInstance().getPlayer().getChancesLeft();
       if (chancesLeft == 0) {
           Event.GameLost.invoke();
       }
    }
}

package org.LanceOfDestiny.domain.spells;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.barriers.Barrier;
import org.LanceOfDestiny.domain.barriers.BarrierFactory;
import org.LanceOfDestiny.domain.events.Event;
import org.LanceOfDestiny.domain.managers.BarrierManager;

import java.util.List;

public class CurseManager {
    private static CurseManager instance;
    private List<Barrier> barriersToFreeze;


    private CurseManager() {
        Event.ActivateCurse.addListener(this::activateCurse);
        Event.ActivateHollowPurple.addListener(this::handleHollowPurple);
        Event.ActivateInfiniteVoid.addListener(this::handleInfiniteVoid);
        Event.Reset.addRunnableListener(this::resetCurseManager);
    }

    public static CurseManager getInstance() {
        if (instance == null) instance = new CurseManager();
        return instance;
    }

    public void activateCurse(Object objectSpelltype) {
        System.out.println("Activating Curse: " + objectSpelltype);
        new SpellActivation(((SpellType) objectSpelltype), Constants.CURSE_DURATION).activate();
    }

    public void handleHollowPurple(Object isActivate) {
        if ((boolean) isActivate) activateHollowPurple();
    }

    public void activateHollowPurple() {
        var locations = BarrierManager.getInstance().getPossibleHollowBarrierLocations(8);
        for (var location : locations) {
            BarrierFactory.createHollowBarrier(location);
        }
    }

    public void handleInfiniteVoid(Object object) {
        var isActive = (boolean) object;
        if (isActive) activateInfiniteVoid();
        else deactivateInfiniteVoid();
    }

    public void activateInfiniteVoid() {
        this.barriersToFreeze = BarrierManager.getInstance().getRandomBarriersWithAmount(8);
        for (Barrier barrier : barriersToFreeze) {
            barrier.activateFrozen();
        }
    }

    public void deactivateInfiniteVoid() {
        for (Barrier barrier : barriersToFreeze) {
            barrier.deactivateFrozen();
        }
        barriersToFreeze.clear();
        barriersToFreeze = null;
    }

    private void resetCurseManager() {
        barriersToFreeze = null;
    }
}

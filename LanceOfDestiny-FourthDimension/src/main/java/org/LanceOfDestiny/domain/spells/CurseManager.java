package org.LanceOfDestiny.domain.spells;

import org.LanceOfDestiny.domain.barriers.Barrier;
import org.LanceOfDestiny.domain.barriers.BarrierFactory;
import org.LanceOfDestiny.domain.behaviours.MonoBehaviour;
import org.LanceOfDestiny.domain.events.Events;
import org.LanceOfDestiny.domain.managers.BarrierManager;

import java.util.List;

/**
 * Curse Manager for singleplayer mode.
 *
 * **/
public class CurseManager extends MonoBehaviour {
    private static CurseManager instance;
    private SpellType activeCurse;
    private boolean isCurseActive;
    private List<Barrier> barriersToFreeze;


    private CurseManager() {
        Events.TryUsingCurse.addListener(this::tryUsingCurse);
        Events.ActivateHollowPurple.addRunnableListener(this::activateHollowPurple);
        Events.ActivateInfiniteVoid.addListener(this::handleInfiniteVoid);
        this.isCurseActive = false;
    }

    private void tryUsingCurse(Object object) {
        SpellType spellType = (SpellType) object;
        if(isCurseActive) return;
        isCurseActive = true;
        System.out.println("Trying using Curse: " + spellType);
        spellType.activate();
        activeCurse = spellType;
    }

    public static CurseManager getInstance() {
        if (instance == null) instance = new CurseManager();
        return instance;
    }

    public void activateHollowPurple() {
        System.out.println("Activating Hollow Purple");
        var locations = BarrierManager.getInstance().getPossibleHollowBarrierLocations();
        for (var location : locations) {
            BarrierFactory.createHollowBarrier(location);
        }

        isCurseActive = false;
    }

    public void handleInfiniteVoid(Object object) {
        var isActive = (boolean) object;
        if (isActive) activateInfiniteVoid();
        else deactivateInfiniteVoid();
    }

    public void activateInfiniteVoid() {
        this.barriersToFreeze = BarrierManager.getInstance().getRandomBarriersToFreeze();
        for (Barrier barrier : barriersToFreeze) {
            barrier.activateFrozen();
        }
    }

    public void deactivateInfiniteVoid() {
        for (int i = barriersToFreeze.size() - 1; i >= 0; i--) {
            barriersToFreeze.get(i).deactivateFrozen();
        }
        this.barriersToFreeze.clear();
    }
}

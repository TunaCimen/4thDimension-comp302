package org.LanceOfDestiny.domain.spells;

import org.LanceOfDestiny.domain.barriers.BarrierFactory;
import org.LanceOfDestiny.domain.events.Events;
import org.LanceOfDestiny.domain.managers.BarrierManager;

public class CurseManager {
    private static CurseManager instance;
    private SpellType activeCurse;
    private boolean isCurseActive;


    private CurseManager() {
        Events.TryUsingCurse.addListener(this::tryUsingCurse);
        Events.ActivateHollowPurple.addListener(this::handleHollowPurple);
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

    public void handleHollowPurple(Object object) {
        var isActive = (boolean) object;
        if (isActive) activateHollowPurple();
        else deactivateHollowPurple();
    }

    public void activateHollowPurple() {
        System.out.println("Activating Hollow Purple");
        var locations = BarrierManager.getInstance().getPossibleHollowBarrierLocations();
        for (var location : locations) {
            BarrierFactory.createHollowBarrier(location);
        }
    }

    public void deactivateHollowPurple() {

    }

    public void handleInfiniteVoid(Object object) {
        var isActive = (boolean) object;
        if (isActive) activateInfiniteVoid();
        else deactivateInfiniteVoid();
    }

    public void activateInfiniteVoid() {

    }

    public void deactivateInfiniteVoid() {

    }
}

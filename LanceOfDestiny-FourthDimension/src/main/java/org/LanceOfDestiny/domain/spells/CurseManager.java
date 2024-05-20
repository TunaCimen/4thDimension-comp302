package org.LanceOfDestiny.domain.spells;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.barriers.Barrier;
import org.LanceOfDestiny.domain.barriers.BarrierFactory;
import org.LanceOfDestiny.domain.behaviours.MonoBehaviour;
import org.LanceOfDestiny.domain.events.Events;
import org.LanceOfDestiny.domain.looper.LoopExecutor;
import org.LanceOfDestiny.domain.managers.BarrierManager;
import org.LanceOfDestiny.domain.managers.SessionManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Curse Manager for singleplayer mode.
 * **/
public class CurseManager extends MonoBehaviour {
    private static CurseManager instance;
    private boolean isCurseActive;
    private SpellType activeCurse;
    private int curseEndSecond;
    private List<Barrier> barriersToFreeze;
    private LoopExecutor loopExecutor;

    private CurseManager() {
        this.loopExecutor = SessionManager.getInstance().getLoopExecutor();
        Events.TryUsingCurse.addListener(this::tryUsingCurse);
        Events.ActivateHollowPurple.addListener(this::handleHollowPurple);
        Events.ActivateInfiniteVoid.addListener(this::handleInfiniteVoid);
        Events.Reset.addRunnableListener(this::resetCurseManager);
        this.isCurseActive = false;
    }

    public static CurseManager getInstance() {
        if (instance == null) instance = new CurseManager();
        return instance;
    }

    @Override
    public void update() {
        super.update();
        if (isCurseActive) {

            if (loopExecutor.getSecondsPassed() >= curseEndSecond) {
                deactivateActiveCurse();
            }
        }
    }

    private void tryUsingCurse(Object object) {
        if(isCurseActive) return;
        isCurseActive = true;
        activeCurse = (SpellType) object;
        curseEndSecond = loopExecutor.getSecondsPassed() + Constants.CURSE_DURATION;
        activeCurse.activate();
    }

    private void deactivateActiveCurse() {
        isCurseActive = false;
        activeCurse.deactivate();
    }

    public void handleHollowPurple(Object isActivate) {
        if ((boolean) isActivate) activateHollowPurple();
        else deactivateHollowPurple();
    }

    public void activateHollowPurple() {
        System.out.println("Activating Hollow Purple");
        var locations = BarrierManager.getInstance().getPossibleHollowBarrierLocations(8);
        for (var location : locations) {
            BarrierFactory.createHollowBarrier(location);
        }
    }

    public void deactivateHollowPurple() {
        System.out.println("Deactivating Hollow Purple");
        isCurseActive = false;
    }

    public void handleInfiniteVoid(Object object) {
        var isActive = (boolean) object;
        if (isActive) activateInfiniteVoid();
        else deactivateInfiniteVoid();
    }

    public void activateInfiniteVoid() {
        System.out.println("Activating Infinite Void");
        this.barriersToFreeze = BarrierManager.getInstance().getRandomBarriersWithAmount(8);
        for (Barrier barrier : barriersToFreeze) {
            barrier.activateFrozen();
        }
    }

    public void deactivateInfiniteVoid() {
        System.out.println("Deactivating Infinite Void");
        isCurseActive = false;
        var n = barriersToFreeze.size();
        for (int i = 0; i < n; i++) {
            barriersToFreeze.get(i).deactivateFrozen();
        }
        barriersToFreeze.clear();
        barriersToFreeze = null;
    }

    private void resetCurseManager() {
        isCurseActive = false;
        activeCurse = null;
        curseEndSecond = 0;
        barriersToFreeze = null;
    }
}

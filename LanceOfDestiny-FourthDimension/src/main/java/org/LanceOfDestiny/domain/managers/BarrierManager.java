package org.LanceOfDestiny.domain.managers;

import org.LanceOfDestiny.domain.barriers.Barrier;
import org.LanceOfDestiny.domain.events.Events;

import java.util.ArrayList;

public class BarrierManager {

    private static BarrierManager instance;
    private ArrayList<Barrier> barriers = new ArrayList<Barrier>();

    private BarrierManager() {
    }

    public static BarrierManager getInstance() {
        if (instance == null) instance = new BarrierManager();
        return instance;
    }

    public void addBarrier(Barrier barrier) {
        barriers.add(barrier);
    }

    public ArrayList<Barrier> getBarriers() {
        return barriers;
    }

    public void removeBarrier(Barrier barrier) {
        barriers.remove(barrier);
        if(barriers.isEmpty()) Events.WinGame.invoke();
    }

    public void removeAllBarriers() {
        for (Barrier barrier : barriers) {
            barrier.destroy();
        }
        barriers.clear();
    }
}

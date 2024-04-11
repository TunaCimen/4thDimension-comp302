package org.LanceOfDestiny.domain.managers;

import org.LanceOfDestiny.domain.barriers.Barrier;

import java.util.ArrayList;

public class BarrierManager {

    private ArrayList<Barrier> barriers = new ArrayList<Barrier>();

    protected BarrierManager() {}

    public void addBarrier(Barrier barrier){
        barriers.add(barrier);
    }

    public ArrayList<Barrier> getBarriers() {
        return barriers;
    }

    public void removeBarrier(Barrier barrier){
        barriers.remove(barrier);
    }

    public void removeAllBarriers(){
        for (Barrier barrier : barriers) {
            barrier.Destroy();
        }
        barriers.clear();
    }
}

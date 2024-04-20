package org.LanceOfDestiny.domain.managers;

import org.LanceOfDestiny.LanceOfDestiny;
import org.LanceOfDestiny.domain.barriers.Barrier;
import org.LanceOfDestiny.domain.barriers.BarrierTypes;
import org.LanceOfDestiny.domain.events.Events;
import org.LanceOfDestiny.domain.physics.Vector;

import java.awt.*;
import java.util.ArrayList;

public class BarrierManager {

    private static BarrierManager instance;
    private ArrayList<Barrier> barriers = LanceOfDestiny.getInstance().getGameMap().getBarriers();
    private BarrierTypes selectedBarrierType;
    private Barrier clickedBarrier;
    private Vector oldLocationOfBarrier;
    private BarrierManager() {
        selectedBarrierType = BarrierTypes.SIMPLE;
    }

    public static BarrierManager getInstance() {
        if (instance == null) instance = new BarrierManager();
        return instance;
    }

    public void addBarrier(Barrier barrier) {
        barriers.add(barrier);
    }

    public void createBarrierList(Barrier barrier) {
        // to initialize a new list of barriers
        barriers = new ArrayList<Barrier>();
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

    public BarrierTypes getSelectedBarrierType() {
        return selectedBarrierType;
    }
    public void setSelectedBarrierType(BarrierTypes selectedBarrierType) {
        this.selectedBarrierType = selectedBarrierType;
    }

    public Barrier getClickedBarrier() {
        return clickedBarrier;
    }

    public void setClickedBarrier(Barrier clickedBarrier) {
        this.clickedBarrier = clickedBarrier;
    }

    public Vector getOldLocationOfBarrier() {
        return oldLocationOfBarrier;
    }

    public void setOldLocationOfBarrier(Vector oldLocationOfBarrier) {
        this.oldLocationOfBarrier = oldLocationOfBarrier;
    }

    public Barrier getBarrierByLocation(int x, int y) {
        for (Barrier barrier : barriers) {
            if (barrier.getPosition().getX() <= x &&
                    barrier.getPosition().getX() + 28 >= x &&
                    barrier.getPosition().getY() <= y &&
                    barrier.getPosition().getY() + 20 >= y) {
                return barrier;
            }
        }
        return null;
    }

    public boolean validateBarrierPlacement(int x, int y) {
        // this method allows 6 rows of barriers to be placed
        return (BarrierManager.getInstance().getBarrierByLocation(x, y).getPosition().getY() <= 290);
    }

    public boolean isBarrierColliding(int x, int y) {
        Barrier barrier = BarrierManager.getInstance().getBarrierByLocation(x, y);
        if (barrier != null) {
            for(int i = 0; i < barriers.size(); i++) {
                if (!barriers.get(i).equals(barrier)) {
                    if ((barriers.get(i).getPosition().getX()) <= barrier.getPosition().getX()  &&
                            barrier.getPosition().getX() <= ((barriers.get(i).getPosition().getX()) + 28) &&
                            (barriers.get(i).getPosition().getY()) <= barrier.getPosition().getY() &&
                            barrier.getPosition().getY() <= ((barriers.get(i).getPosition().getY()) + 20)) {
                        return true;
                    }
                }
            }
    }
        return false;
    }



}

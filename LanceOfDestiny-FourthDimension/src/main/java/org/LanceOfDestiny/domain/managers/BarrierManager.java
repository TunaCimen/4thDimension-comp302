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

    public ArrayList<Barrier> getBarriers() {
        return barriers;
    }

    public void removeBarrier(Barrier barrier) {
        barriers.remove(barrier);
        if(barriers.isEmpty()) Events.WinGame.invoke();
    }

    public void deleteBarrier(Barrier barrier) {
        barrier.removeBarrierSprite();
        barriers.remove(barrier);
    }

    public void removeAllBarriers() {
        for (Barrier barrier : LanceOfDestiny.getInstance().getGameMap().getBarriers()) {
            barrier.removeBarrierSprite();
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

            if (barrier.getBarrierType() == BarrierTypes.EXPLOSIVE) {
                if (barrier.getPosition().getX() <= x + 16 &&
                        barrier.getPosition().getX() + 28 >= x + 16 &&
                        barrier.getPosition().getY() <= y + 8 &&
                        barrier.getPosition().getY() + 20 >= y + 8) {
                    return barrier;
                }
            } else if (barrier.getPosition().getX() <= x &&
                    barrier.getPosition().getX() + 28  >= x &&
                    barrier.getPosition().getY() <= y &&
                    barrier.getPosition().getY() + 20 >= y) {
                return barrier;
            }
        }
        return null;
    }

    public boolean validateBarrierPlacement(int x, int y) {
        // this method allows 6 rows of barriers to be placed
        return (y <= 290);
    }

    public boolean isBarrierColliding(int x, int y) {
        // Retrieve the list of barriers, assuming barriers is a member of the class
        ArrayList<Barrier> barriers = BarrierManager.getInstance().getBarriers();

        for (int i = 0; i < barriers.size(); i++) {
            double barrierX = barriers.get(i).getPosition().getX();
            double barrierY = barriers.get(i).getPosition().getY();

            // Check right and below
            if (x >= barrierX && x <= barrierX + 28 &&
                    y >= barrierY && y <= barrierY + 20) {
                System.out.println("Collision on the right or below detected");
                return true;
            }

            // Check left (28 pixels to the left of the barrier)
            if (x <= barrierX && x >= barrierX - 28 &&
                    y >= barrierY && y <= barrierY + 20) {
                System.out.println("Collision on the left detected");
                return true;
            }

            // Check above (20 pixels above the barrier)
            if (x >= barrierX && x <= barrierX + 28 &&
                    y <= barrierY && y >= barrierY - 20) {
                System.out.println("Collision above detected");
                return true;
            }
        }

        return false;
    }




}

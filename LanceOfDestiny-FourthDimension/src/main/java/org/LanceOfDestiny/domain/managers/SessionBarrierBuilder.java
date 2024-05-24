package org.LanceOfDestiny.domain.managers;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.barriers.Barrier;
import org.LanceOfDestiny.domain.barriers.BarrierFactory;
import org.LanceOfDestiny.domain.barriers.BarrierTypes;
import org.LanceOfDestiny.domain.physics.Vector;

import java.util.Collections;

public class SessionBarrierBuilder {
    private Integer numOfSimple;
    private Integer numOfReinforced;
    private Integer numOfExplosive;
    private Integer numOfRewarding;
    public SessionBarrierBuilder() {}

    public void initializeBarriers() {
        //BarrierManager.getInstance().deleteAllBarriers();

        // Add barrier types to the list based on the counts
        int i;
        for (i = 0; i < numOfSimple; i++) {
            BarrierFactory.createBarrier(new Vector(0,0), BarrierTypes.SIMPLE);
        }
        for (i = 0; i < numOfReinforced; i++) {
            BarrierFactory.createBarrier(new Vector(0,0), BarrierTypes.REINFORCED);
        }
        for (i = 0; i < numOfExplosive; i++) {
            BarrierFactory.createBarrier(new Vector(0,0), BarrierTypes.EXPLOSIVE);
        }
        for (i = 0; i < numOfRewarding; i++) {
            BarrierFactory.createBarrier(new Vector(0,0), BarrierTypes.REWARDING);
        }

        // Shuffling for randomization
        Collections.shuffle(BarrierManager.getInstance().getBarriers());

        int x = 40;
        int y = 40;

        for (Barrier barrier : BarrierManager.getInstance().getBarriers()) {
            barrier.setPosition(new Vector(x, y));
            if (barrier.getType() == BarrierTypes.EXPLOSIVE) {
                barrier.shiftPosition(new Vector(x, y));
            }

            x += Constants.BARRIER_X_OFFSET;
            if (x >= Constants.SCREEN_WIDTH - 40) {
                x = 40;
                y += Constants.BARRIER_Y_OFFSET;
            }
        }
    }

    public void setBarrierCounts(int numOfSimple, int numOfReinforced, int numOfExplosive, int numOfRewarding) {
        setNumOfSimple(numOfSimple);
        setNumOfReinforced(numOfReinforced);
        setNumOfExplosive(numOfExplosive);
        setNumOfRewarding(numOfRewarding);
    }
    public Integer getNumOfSimple() {
        return numOfSimple;
    }

    public void setNumOfSimple(Integer numOfSimple) {
        this.numOfSimple = numOfSimple;
    }

    public Integer getNumOfReinforced() {
        return numOfReinforced;
    }

    public void setNumOfReinforced(Integer numOfFirm) {
        this.numOfReinforced = numOfFirm;
    }

    public Integer getNumOfExplosive() {
        return numOfExplosive;
    }

    public void setNumOfExplosive(Integer numOfExplosive) {
        this.numOfExplosive = numOfExplosive;
    }

    public Integer getNumOfRewarding() {
        return numOfRewarding;
    }

    public void setNumOfRewarding(Integer numOfRewarding) {
        this.numOfRewarding = numOfRewarding;
    }
}

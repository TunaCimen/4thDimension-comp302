package org.LanceOfDestiny.domain.managers;

import org.LanceOfDestiny.LanceOfDestiny;
import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.GameMap;
import org.LanceOfDestiny.domain.barriers.Barrier;
import org.LanceOfDestiny.domain.barriers.BarrierFactory;
import org.LanceOfDestiny.domain.barriers.BarrierTypes;
import org.LanceOfDestiny.domain.behaviours.Behaviour;
import org.LanceOfDestiny.domain.physics.Vector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class SessionBuilder {
    private Integer numOfSimple;
    private Integer numOfReinforced;
    private Integer numOfExplosive;
    private Integer numOfRewarding;

    public SessionBuilder(Integer numOfSimple, Integer numOfReinforced, Integer numOfExplosive, Integer numOfRewarding) {
        this.numOfSimple = numOfSimple;
        this.numOfReinforced = numOfReinforced;
        this.numOfExplosive = numOfExplosive;
        this.numOfRewarding = numOfRewarding;
    }


    public void initializeBarriers() {

        BarrierManager.getInstance().removeAllBarriers();

         //Add barrier types to the list based on the counts
        int i;
        for (i = 0; i < numOfSimple; i++) {
            Barrier barrier = BarrierFactory.createBarrier(new Vector(0,0), BarrierTypes.SIMPLE);
            LanceOfDestiny.getInstance().getGameMap().getBarriers().add(barrier);
        }
        for (i = 0; i < numOfReinforced; i++) {
            Barrier barrier = BarrierFactory.createBarrier(new Vector(0,0), BarrierTypes.REINFORCED);
            LanceOfDestiny.getInstance().getGameMap().getBarriers().add(barrier);
        }
        for (i = 0; i < numOfExplosive; i++) {
            Barrier barrier = BarrierFactory.createBarrier(new Vector(0,0), BarrierTypes.EXPLOSIVE);
            LanceOfDestiny.getInstance().getGameMap().getBarriers().add(barrier);
        }
        for (i = 0; i < numOfRewarding; i++) {
            Barrier barrier = BarrierFactory.createBarrier(new Vector(0,0), BarrierTypes.REWARDING);
            LanceOfDestiny.getInstance().getGameMap().getBarriers().add(barrier);
        }

        // shuffling for randomization
        Collections.shuffle(LanceOfDestiny.getInstance().getGameMap().getBarriers());

        int x = 40;
        int y = 40;

        for (Barrier barrier1 : LanceOfDestiny.getInstance().getGameMap().getBarriers()) {
            barrier1.setPosition(new Vector(x, y));

            x += 50;
            if (x >= Constants.SCREEN_WIDTH - 40) {
                x = 40;
                y += 30;
            }
        }
    }




    private BarrierTypes determineTypeToCreate(int numSimpleCreated, int numFirmCreated, int numExplosiveCreated, int numGiftCreated) {

        //Weighted Randomization
        double totalWeight = numOfSimple + numOfReinforced + numOfExplosive + numOfRewarding;
        double randomValue = Math.random() * totalWeight;

        if (randomValue < numOfSimple) {
            return BarrierTypes.SIMPLE;
        } else if (randomValue < numOfSimple + numOfReinforced) {
            return BarrierTypes.REINFORCED;
        } else if (randomValue < numOfSimple + numOfReinforced + numOfExplosive) {
            return BarrierTypes.EXPLOSIVE;
        } else {
            return BarrierTypes.REWARDING;
        }


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

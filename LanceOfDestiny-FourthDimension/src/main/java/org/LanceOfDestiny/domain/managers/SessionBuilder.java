package org.LanceOfDestiny.domain.managers;

import org.LanceOfDestiny.LanceOfDestiny;
import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.barriers.Barrier;
import org.LanceOfDestiny.domain.barriers.BarrierFactory;
import org.LanceOfDestiny.domain.barriers.BarrierTypes;
import org.LanceOfDestiny.domain.physics.Vector;

import java.util.Collections;

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

        // shuffling for randomization
        Collections.shuffle(BarrierManager.getInstance().barriers);

        int x = 40;
        int y = 40;

        for (Barrier barrier1 : BarrierManager.getInstance().barriers) {
            if (barrier1.getType() == BarrierTypes.EXPLOSIVE) {
                barrier1.setPosition(new Vector(x, y));
                barrier1.shiftPosition(new Vector(x , y));
            }
            else {
                barrier1.setPosition(new Vector(x, y));


            }

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

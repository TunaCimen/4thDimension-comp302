package org.LanceOfDestiny.domain.managers;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.barriers.BarrierFactory;
import org.LanceOfDestiny.domain.barriers.BarrierTypes;
import org.LanceOfDestiny.domain.physics.Vector;

import java.util.ArrayList;
import java.util.Collections;
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

    public void buildBarriers() {
        int totalBarriers = numOfSimple + numOfReinforced + numOfExplosive + numOfRewarding;
        List<BarrierTypes> barrierTypes = new ArrayList<>();

        // Add barrier types to the list based on the counts
        for (int i = 0; i < numOfSimple; i++) {
            barrierTypes.add(BarrierTypes.SIMPLE);
        }
        for (int i = 0; i < numOfReinforced; i++) {
            barrierTypes.add(BarrierTypes.REINFORCED);
        }
        for (int i = 0; i < numOfExplosive; i++) {
            barrierTypes.add(BarrierTypes.EXPLOSIVE);
        }
        for (int i = 0; i < numOfRewarding; i++) {
            barrierTypes.add(BarrierTypes.REWARDING);
        }

        // Shuffle the list to randomize barrier placement
        Collections.shuffle(barrierTypes);

        int x = 20;
        int y = 20;

        for (BarrierTypes type : barrierTypes) {
            BarrierFactory.createBarrier(new Vector(x, y), type);

            x += 40;
            if (x >= Constants.SCREEN_WIDTH - 10) {
                x = 20;
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

package org.LanceOfDestiny.domain.managers;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.barriers.BarrierFactory;
import org.LanceOfDestiny.domain.barriers.BarrierTypes;
import org.LanceOfDestiny.domain.physics.Vector;

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
        // Track how many of each type are created
        int numSimpleCreated = 0;
        int numFirmCreated = 0;
        int numExplosiveCreated = 0;
        int numGiftCreated = 0;

        for (int i = 10; i < Constants.SCREEN_WIDTH - 10; i += 30) {
            for (int j = 10; j < Constants.SCREEN_HEIGHT - 400; j += 30) {
                BarrierTypes typeToCreate = determineTypeToCreate(numSimpleCreated, numFirmCreated, numExplosiveCreated, numGiftCreated);

                BarrierFactory.createBarrier(new Vector(i, j), typeToCreate);

                // Update counters for the relevant type
                switch (typeToCreate) {
                    case SIMPLE:
                        numSimpleCreated++;
                        break;
                    case REINFORCED:
                        numFirmCreated++;
                        break;
                    case EXPLOSIVE:
                        numExplosiveCreated++;
                        break;
                    case REWARDING:
                        numGiftCreated++;
                        break;
                }
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

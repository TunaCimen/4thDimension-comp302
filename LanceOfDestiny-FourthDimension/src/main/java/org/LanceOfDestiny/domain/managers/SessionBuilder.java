package org.LanceOfDestiny.domain.managers;

import org.LanceOfDestiny.LanceOfDestiny;
import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.GameMap;
import org.LanceOfDestiny.domain.barriers.Barrier;
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


//    /**
//     * This method is responsible for building and positioning the barriers in the game.
//     * It first creates the specified number of each type of barrier and adds them to the game's barrier list.
//     * Then it shuffles the barrier list for randomization.
//     * Finally, it positions the barriers on the game map, ensuring they do not exceed the screen width.
//     */
//    public void initializeBarriers() {
//        LanceOfDestiny.getInstance().setGameMap(new GameMap());
//        ArrayList<Barrier> barrierList = LanceOfDestiny.getInstance().getGameMap().getBarriers();
//        int i;
//        for (i = 0; i < numOfSimple; i++) {
//            Barrier barrier = BarrierFactory.createBarrier(new Vector(0,0), BarrierTypes.SIMPLE);
//           barrierList.add(barrier);
//        }
//        for (i = 0; i < numOfReinforced; i++) {
//            Barrier barrier = BarrierFactory.createBarrier(new Vector(0,0), BarrierTypes.REINFORCED);
//            barrierList.add(barrier);
//        }
//        for (i = 0; i < numOfExplosive; i++) {
//            Barrier barrier = BarrierFactory.createBarrier(new Vector(0,0), BarrierTypes.EXPLOSIVE);
//            barrierList.add(barrier);
//        }
//        for (i = 0; i < numOfRewarding; i++) {
//            Barrier barrier = BarrierFactory.createBarrier(new Vector(0,0), BarrierTypes.REWARDING);
//            barrierList.add(barrier);
//        }
//        // everyday i'm shuffling
//        // shuffling for randomization
//        Collections.shuffle(barrierList);
//
//
//        Barrier barrierZero = barrierList.get(0);
//        barrierZero.setPosition(new Vector(68, 40));
//        float barrierAndGapWidth = 68; // 28 is the width of the barrier and 40 is the gap between barriers
//        int barrierAndGapPairs = 2;
//        float verticalGap = 40;
//        for(i = 1; i < barrierList.size(); i++) {
//            float widthCheck = (barrierAndGapWidth * barrierAndGapPairs) + 40;
//            Barrier nextBarrier = barrierList.get(i);
//            if (widthCheck + barrierAndGapWidth > Constants.SCREEN_WIDTH) {
//                barrierAndGapPairs = 1;
//                verticalGap += 40;
//                nextBarrier.setPosition(new Vector(barrierAndGapWidth*barrierAndGapPairs, verticalGap));
//            }
//            else {
//                float x = (barrierAndGapWidth * barrierAndGapPairs);
//                nextBarrier.setPosition(new Vector(x, verticalGap));
//                barrierAndGapPairs++;
//            }
//
//        }
//    }


    public void initializeBarriers() {
        LanceOfDestiny.getInstance().setGameMap(new GameMap());
        ArrayList<Barrier> barrierList = LanceOfDestiny.getInstance().getGameMap().getBarriers();


         //Add barrier types to the list based on the counts
        int i;
        for (i = 0; i < numOfSimple; i++) {
            Barrier barrier = BarrierFactory.createBarrier(new Vector(0,0), BarrierTypes.SIMPLE);
           barrierList.add(barrier);
        }
        for (i = 0; i < numOfReinforced; i++) {
            Barrier barrier = BarrierFactory.createBarrier(new Vector(0,0), BarrierTypes.REINFORCED);
            barrierList.add(barrier);
        }
        for (i = 0; i < numOfExplosive; i++) {
            Barrier barrier = BarrierFactory.createBarrier(new Vector(0,0), BarrierTypes.EXPLOSIVE);
            barrierList.add(barrier);
        }
        for (i = 0; i < numOfRewarding; i++) {
            Barrier barrier = BarrierFactory.createBarrier(new Vector(0,0), BarrierTypes.REWARDING);
            barrierList.add(barrier);
        }

        // shuffling for randomization
        Collections.shuffle(barrierList);

        int x = 40;
        int y = 40;

        for (Barrier barrier1 : barrierList) {
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

package org.LanceOfDestiny.domain.barriers;

import org.LanceOfDestiny.domain.managers.BarrierManager;
import org.LanceOfDestiny.domain.physics.Vector;
import org.LanceOfDestiny.domain.spells.SpellType;

import java.util.Random;

public class BarrierFactory {

    public static Barrier createBarrier(Vector position, BarrierTypes type) {
        Barrier createdBarrier = switch (type) {
            case SIMPLE -> new SimpleBarrier(position);
            case REINFORCED -> new ReinforcedBarrier(position, calculateHitsRequired());
            case EXPLOSIVE -> new ExplosiveBarrier(position);
            case REWARDING -> new RewardingBarrier(position);
            case HOLLOW -> new HollowBarrier(position);
        };
        BarrierManager.getInstance().addBarrier(createdBarrier);
        return createdBarrier;
    }

    private static int calculateHitsRequired() {
        int chance = (new Random()).nextInt(100);
        if (chance < 50) {                // 50% probability
            return 3;
        } else if (chance < 70) {         // 20% probability
            return 2;
        } else if (chance < 90) {         // 20% probability
            return 4;
        } else {                          // 10% probability
            return 5;
        }
    }

    /**
     * Factory method for loading barriers. Used for save/load and multiplayer map sharing.
     * @param position
     * @param barrierType
     * @param hitsLeft
     * @param moving
     * @return the created barrier
     */
    public static Barrier createBarrier(Vector position, String barrierType, int hitsLeft, boolean moving) {
        Barrier barrier;
        switch (barrierType) {
            case "SIMPLE":
                barrier = new SimpleBarrier(position);
                break;
            case "EXPLOSIVE":
                barrier = new ExplosiveBarrier(position);
                break;
            case "REINFORCED":
                barrier = new ReinforcedBarrier(position, hitsLeft);
                break;
            case "REWARDING":
                barrier = new RewardingBarrier(position);
                break;
            case "HOLLOW":
                barrier = new HollowBarrier(position);
                break;
            default:
                throw new IllegalArgumentException("Unknown barrier type: " + barrierType);
        }
        barrier.setMoving(moving);
        barrier.initDirection();
        barrier.start();
        BarrierManager.getInstance().addBarrier(barrier);
        return barrier;
    }

    public static Barrier createBarrier(Vector position, String barrierType, int hitsLeft, boolean isMoving, SpellType spellType) {
        var barrier = createBarrier(position, barrierType, hitsLeft, isMoving);
        if ((barrier instanceof RewardingBarrier rewarding) && spellType != null) {
            rewarding.setReward(spellType);
        }
        return barrier;
    }
}

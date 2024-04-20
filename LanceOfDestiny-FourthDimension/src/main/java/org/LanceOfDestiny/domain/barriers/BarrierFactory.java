package org.LanceOfDestiny.domain.barriers;

import org.LanceOfDestiny.domain.managers.BarrierManager;
import org.LanceOfDestiny.domain.physics.Vector;

import java.util.Random;

public class BarrierFactory {

    public static Barrier createBarrier(Vector position, BarrierTypes type) {
        Barrier createdBarrier = switch (type) {
            case SIMPLE -> new SimpleBarrier(position);
            case REINFORCED -> new ReinforcedBarrier(position, calculateHitsRequired());
            case EXPLOSIVE -> new ExplosiveBarrier(position);
            case REWARDING -> new RewardingBarrier(position);
            default -> null;
        };
        if (createdBarrier == null) {
            return null;
        }
        //todo delete this line
//        BarrierManager.getInstance().createBarrierList(createdBarrier);
        return createdBarrier;
    }

/**
 * This method is used to add a new barrier to the BarrierManager.
 * It calls the singleton instance of the BarrierManager and invokes its addBarrier method.
 *
 * @param barrier The barrier object to be added to the BarrierManager.
 */
public static void addBarrier(Barrier barrier) {
    BarrierManager.getInstance().addBarrier(barrier);
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

}

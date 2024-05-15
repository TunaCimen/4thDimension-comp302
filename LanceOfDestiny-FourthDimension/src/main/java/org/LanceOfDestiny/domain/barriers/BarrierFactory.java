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
        };
        BarrierManager.getInstance().addBarrier(createdBarrier);
        return createdBarrier;
    }

    public static HollowBarrier createHollowBarrier(Vector position) {
        var hollowBarrier = new HollowBarrier(position);
        BarrierManager.getInstance().addBarrier(hollowBarrier);
        BarrierManager.getInstance().addHollowBarrier(hollowBarrier);
        return hollowBarrier;
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

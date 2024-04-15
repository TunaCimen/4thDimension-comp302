package org.LanceOfDestiny.domain.barriers;

import org.LanceOfDestiny.domain.abilities.SpellType;
import org.LanceOfDestiny.domain.managers.ManagerHub;
import org.LanceOfDestiny.domain.physics.Vector;

import java.util.Random;

public class BarrierFactory {
    private static BarrierFactory instance;

    private BarrierFactory() {
    }

    public static BarrierFactory getInstance() {
        if (instance == null) {
            instance = new BarrierFactory();
        }
        return instance;
    }

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
        ManagerHub.getInstance().getBarrierManager().addBarrier(createdBarrier);
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


}

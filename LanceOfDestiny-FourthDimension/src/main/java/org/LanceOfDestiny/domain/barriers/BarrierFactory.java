package org.LanceOfDestiny.domain.barriers;

import org.LanceOfDestiny.domain.abilities.SpellType;
import org.LanceOfDestiny.domain.managers.ManagerHub;

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

    public Barrier createBarrier(int x, int y, BarrierTypes type) {
        Barrier createdBarrier = switch (type) {
            case SIMPLE -> new SimpleBarrier(x, y, type);
            case REINFORCED -> new ReinforcedBarrier(x, y, type, calculateHitsRequired());
            case EXPLOSIVE -> new ExplosiveBarrier(x, y, type);
            case REWARDING -> new RewardingBarrier(x, y, type, getRandomSpellType());
            default -> null;
        };
        if (createdBarrier == null) {
            return null;
        }
        ManagerHub.getInstance().getBarrierManager().addBarrier(createdBarrier);
        return createdBarrier;
    }

    public SpellType getRandomSpellType(){
        return SpellType.values()[new Random().nextInt(SpellType.values().length)];
    }

    private int calculateHitsRequired() {
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

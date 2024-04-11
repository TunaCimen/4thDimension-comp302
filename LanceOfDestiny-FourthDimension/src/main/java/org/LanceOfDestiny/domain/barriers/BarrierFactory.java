package org.LanceOfDestiny.domain.barriers;

import org.LanceOfDestiny.domain.abilities.SpellType;
import org.LanceOfDestiny.domain.managers.BarrierManager;
import org.LanceOfDestiny.domain.managers.ManagerHub;

import java.util.Random;

public class BarrierFactory {
    private static BarrierFactory instance;
    private SpellType spellType;

    private BarrierFactory() {
    }

    public static BarrierFactory getInstance() {
        if (instance == null) {
            instance = new BarrierFactory();
        }
        return instance;
    }

    public Barrier createBarrier(int x, int y, BarrierTypes type) {

        Random random = new Random();
        Barrier createdBarrier;
        switch (type) {
            case SIMPLE:
                createdBarrier = new SimpleBarrier(x, y, type);
                break;
            case REINFORCED:
                createdBarrier = new ReinforcedBarrier(x, y, type, 3);
                break;
            case EXPLOSIVE:
                createdBarrier = new ExplosiveBarrier(x, y, type);
                break;
            case REWARDING:
                // Used Random instance to select a random SpellType
                SpellType randomSpellType = SpellType.values()[random.nextInt(SpellType.values().length)];
                createdBarrier = new RewardingBarrier(x, y, type, randomSpellType);
                break;
            default:
                createdBarrier = null;
                break;
        }
        if (createdBarrier == null) {
            return null;
        }
        ManagerHub.getInstance().getBarrierManager().addBarrier(createdBarrier);
        return createdBarrier;
    }

}

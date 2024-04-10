package org.LanceOfDestiny.domain.barriers;

import org.LanceOfDestiny.domain.abilities.SpellType;

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

        switch (type) {
            case SIMPLE:
                return new SimpleBarrier(x, y, type);
            case REINFORCED:
                return new ReinforcedBarrier(x, y, type, 3);
            case EXPLOSIVE:
                return new ExplosiveBarrier(x, y, type);
            case REWARDING:
                // Used Random instance to select a random SpellType
                SpellType randomSpellType = SpellType.values()[random.nextInt(SpellType.values().length)];
                return new RewardingBarrier(x, y, type, randomSpellType);
            default:
                return null;
        }
    }

}

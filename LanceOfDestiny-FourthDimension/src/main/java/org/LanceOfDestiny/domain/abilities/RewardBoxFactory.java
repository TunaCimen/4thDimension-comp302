package org.LanceOfDestiny.domain.abilities;

import org.LanceOfDestiny.domain.barriers.*;
import org.LanceOfDestiny.domain.managers.ManagerHub;

import java.util.Random;

public class RewardBoxFactory {

    private static RewardBoxFactory instance;

    private RewardBoxFactory() {
    }

    public static RewardBoxFactory getInstance() {
        if (instance == null) {
            instance = new RewardBoxFactory();
        }
        return instance;
    }

    public RewardBox generateRandomRewardBox(int x, int y) {
        return createRewardBox(x, y, getRandomSpellType());
    }

    public RewardBox createRewardBox(int x, int y, SpellType spellType) {
        return new RewardBox(x, y, spellType);
    }

    public SpellType getRandomSpellType(){
        return SpellType.values()[new Random().nextInt(SpellType.values().length)];
    }




}

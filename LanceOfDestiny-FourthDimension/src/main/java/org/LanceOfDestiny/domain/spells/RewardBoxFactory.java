package org.LanceOfDestiny.domain.spells;

import org.LanceOfDestiny.domain.events.Events;
import org.LanceOfDestiny.domain.physics.Vector;

import java.util.ArrayList;
import java.util.Random;

public class RewardBoxFactory {

    private static RewardBoxFactory instance;
    private static ArrayList<RewardBox> rewardBoxes = new ArrayList<>();


    private RewardBoxFactory() {
        Events.LoseGame.addRunnableListener(this::removeRewardBoxes);
    }

    public static RewardBoxFactory getInstance() {
        if (instance == null) {
            instance = new RewardBoxFactory();
        }
        return instance;
    }

    public static RewardBox generateRandomRewardBox(Vector position) {
        return createRewardBox(position, getRandomSpellType());
    }

    public static RewardBox createRewardBox(Vector position, SpellType spellType) {
        var rewardBox = new RewardBox(position, spellType);
        rewardBoxes.add(rewardBox);
        return rewardBox;
    }

    public static SpellType getRandomSpellType(){
        return SpellType.values()[new Random().nextInt(SpellType.values().length)];
    }

    public ArrayList<RewardBox> getRewardBoxes() {
        return rewardBoxes;
    }

    public void removeRewardBox(RewardBox rewardBox) {
        rewardBoxes.remove(rewardBox);
    }

    public void removeRewardBoxes() {
        int initSize = rewardBoxes.size();
        for (int i = initSize - 1; i >= 0; i--) {
            rewardBoxes.get(i).destroy();
        }
    }
}

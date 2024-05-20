package org.LanceOfDestiny.domain.spells;

import org.LanceOfDestiny.domain.events.Events;
import org.LanceOfDestiny.domain.managers.SessionManager;
import org.LanceOfDestiny.domain.physics.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RewardBoxFactory {

    private static RewardBoxFactory instance;
    private static ArrayList<RewardBox> rewardBoxes = new ArrayList<>();
    private static final Random RANDOM = new Random();

    private RewardBoxFactory() {
        Events.Reset.addRunnableListener(this::removeRewardBoxes);
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

    public static SpellType getRandomSpellType() {
        if (SessionManager.getInstance().getGameMode().equals(SessionManager.GameMode.MULTIPLAYER)) {
            // Any type of spell in multiplayer mode
            return SpellType.values()[RANDOM.nextInt(SpellType.values().length)];
        } else {
            // Only good spells in single-player mode
            List<SpellType> goodSpells = Arrays.stream(SpellType.values())
                    .filter(SpellType::isGood)
                    .toList();
            return goodSpells.get(RANDOM.nextInt(goodSpells.size()));
        }
    }

    public void removeRewardBox(RewardBox rewardBox) {
        rewardBoxes.remove(rewardBox);
    }

    public void removeRewardBoxes() {
        int initSize = rewardBoxes.size();
        for (int i = initSize - 1; i >= 0; i--) {
            rewardBoxes.get(i).destroy();
        }
        rewardBoxes.clear();
    }
}

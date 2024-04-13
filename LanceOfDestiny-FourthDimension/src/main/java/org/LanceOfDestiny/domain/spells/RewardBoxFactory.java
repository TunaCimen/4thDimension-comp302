package org.LanceOfDestiny.domain.abilities;

import org.LanceOfDestiny.domain.barriers.*;
import org.LanceOfDestiny.domain.managers.ManagerHub;
import org.LanceOfDestiny.domain.physics.Vector;

import java.util.ArrayList;
import java.util.Random;

public class RewardBoxFactory {

    private static RewardBoxFactory instance;
    private static ArrayList<RewardBox> rewardBoxes = new ArrayList<>();


    private RewardBoxFactory() {
    }

    public static RewardBoxFactory getInstance() {
        if (instance == null) {
            instance = new RewardBoxFactory();
        }
        return instance;
    }

    public RewardBox generateRandomRewardBox(Vector position) {
        return createRewardBox(position, getRandomSpellType());
    }

    public RewardBox createRewardBox(Vector position, SpellType spellType) {
        var rewardBox = new RewardBox(position, spellType);
        rewardBoxes.add(rewardBox);
        return rewardBox;
    }

    public SpellType getRandomSpellType(){
        return SpellType.values()[new Random().nextInt(SpellType.values().length)];
    }

    public ArrayList<RewardBox> getRewardBoxes() {
        return rewardBoxes;
    }




}

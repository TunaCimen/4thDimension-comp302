package org.LanceOfDestiny.domain.ymir;

import org.LanceOfDestiny.domain.behaviours.MonoBehaviour;
import org.LanceOfDestiny.domain.spells.SpellType;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Ymir extends MonoBehaviour {

    private static final int COIN_FLIP_INTERVAL = 30;
    private static final double COIN_FLIP_PROBABILITY = 0.5;
    private final Random RANDOM = new Random();
    private SpellType[] lastTwoAbilities = new SpellType[2];

     public Ymir() {
        super();
         lastTwoAbilities[0] = getRandomCurse();
         lastTwoAbilities[1] = getRandomCurse();
    }

    @Override
    public void update() {
        super.update();
    }

    private SpellType getRandomCurse() {
        List<SpellType> badSpells = Arrays.stream(SpellType.values())
                .filter(spellType -> !spellType.isGood())
                .toList();
        return badSpells.get(RANDOM.nextInt(badSpells.size()));
    }

    private void updateLastTwoAbilities(SpellType newAbility) {
        lastTwoAbilities[0] = lastTwoAbilities[1];
        lastTwoAbilities[1] = newAbility;
    }


}

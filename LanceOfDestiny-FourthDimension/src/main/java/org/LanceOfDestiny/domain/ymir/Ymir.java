package org.LanceOfDestiny.domain.ymir;

import org.LanceOfDestiny.domain.behaviours.MonoBehaviour;
import org.LanceOfDestiny.domain.looper.LoopExecutor;
import org.LanceOfDestiny.domain.managers.SessionManager;
import org.LanceOfDestiny.domain.spells.CurseManager;
import org.LanceOfDestiny.domain.spells.SpellType;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Ymir extends MonoBehaviour {

    private CurseManager curseManager;
    private static final int COIN_FLIP_INTERVAL = 30;
    private static final double COIN_FLIP_PROBABILITY = 0.5;
    private int nextCoinFlipSeconds;
    private final Random RANDOM = new Random();
    private SpellType[] lastTwoAbilities = new SpellType[2];
    private LoopExecutor loopExecutor;

     public Ymir() {
        super();
        lastTwoAbilities[0] = getRandomCurse();
        lastTwoAbilities[1] = getRandomCurse();
        this.curseManager = CurseManager.getInstance();
        this.loopExecutor = SessionManager.getInstance().getLoopExecutor();
    }

    @Override
    public void update() {
        super.update();
        if (loopExecutor.getSecondsPassed() > nextCoinFlipSeconds) {
            coinFlip();
        }
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

    private void coinFlip() {
         nextCoinFlipSeconds = COIN_FLIP_INTERVAL + loopExecutor.getSecondsPassed();
         var coinFlip = RANDOM.nextDouble() < COIN_FLIP_PROBABILITY;
         if (coinFlip) activateRandomCurse();
    }

    private void activateRandomCurse() {
         var randomCurse = getRandomCurse();
         while (!validateCurse(randomCurse)) {
             randomCurse = getRandomCurse();
         }
         randomCurse.activate();
         updateLastTwoAbilities(randomCurse);
    }

    private boolean validateCurse(SpellType curse) {
        return !curse.equals(lastTwoAbilities[0]) || !curse.equals(lastTwoAbilities[1]);
    }


}

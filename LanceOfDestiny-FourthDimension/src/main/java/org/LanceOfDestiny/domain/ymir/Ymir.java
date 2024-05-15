package org.LanceOfDestiny.domain.ymir;

import org.LanceOfDestiny.domain.behaviours.MonoBehaviour;
import org.LanceOfDestiny.domain.looper.LoopExecutor;
import org.LanceOfDestiny.domain.managers.SessionManager;
import org.LanceOfDestiny.domain.spells.SpellType;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Ymir extends MonoBehaviour {

    private final LoopExecutor loopExecutor;
    private static final int COIN_FLIP_INTERVAL = 15;
    private static final double COIN_FLIP_PROBABILITY = 1;
    private static final int SPELL_DURATION = 5;
    private int curseEndSecond;
    private boolean isCurseActive;
    private SpellType activeCurse;
    private int nextCoinFlipSeconds = 0;
    private final Random RANDOM = new Random();
    private SpellType[] lastTwoAbilities = new SpellType[2];

     public Ymir() {
        super();
        lastTwoAbilities[0] = getRandomCurse();
        lastTwoAbilities[1] = getRandomCurse();
        this.loopExecutor = SessionManager.getInstance().getLoopExecutor();
    }

    @Override
    public void update() {
        super.update();
        if (loopExecutor.getSecondsPassed() >= nextCoinFlipSeconds) {
            coinFlip();
        }

        if (isCurseActive && loopExecutor.getSecondsPassed() >= curseEndSecond) {
            deactivateCurse();
            System.out.println("Deactivated Curse");
        }
    }

    private void deactivateCurse() {
         System.out.println("Deactivating curse");
         activeCurse.deactivate();
         isCurseActive = false;
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
         System.out.println("Coin flipped: " + coinFlip);
         if (coinFlip) activateRandomCurse();
    }

    private void activateRandomCurse() {
         var randomCurse = getRandomCurse();
         while (!validateCurse(randomCurse)) {
             randomCurse = getRandomCurse();
         }
         System.out.println("Random Curse Activating: " + randomCurse);
         randomCurse.activate();
         updateLastTwoAbilities(randomCurse);

         if(SpellType.HOLLOW_PURPLE.equals(randomCurse))
             return;

         isCurseActive = true;
         activeCurse = randomCurse;
         curseEndSecond = loopExecutor.getSecondsPassed() + SPELL_DURATION;
         System.out.println("Curse end Second: " + activeCurse);
    }

    private boolean validateCurse(SpellType curse) {
        return !curse.equals(lastTwoAbilities[0]) || !curse.equals(lastTwoAbilities[1]);
    }


}

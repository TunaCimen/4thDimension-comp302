package org.LanceOfDestiny.domain.ymir;

import org.LanceOfDestiny.domain.behaviours.MonoBehaviour;
import org.LanceOfDestiny.domain.events.Events;
import org.LanceOfDestiny.domain.looper.LoopExecutor;
import org.LanceOfDestiny.domain.managers.SessionManager;
import org.LanceOfDestiny.domain.spells.SpellType;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Ymir extends MonoBehaviour {

    private final LoopExecutor loopExecutor;
    private static final int COIN_FLIP_INTERVAL = 20;
    private static final double COIN_FLIP_PROBABILITY = 1;
    private int nextCoinFlipSeconds = 0;
    private final Random RANDOM = new Random();
    private SpellType[] lastTwoAbilities = new SpellType[2];

     public Ymir() {
        super();
        lastTwoAbilities[0] = getRandomCurse();
        lastTwoAbilities[1] = getRandomCurse();
        this.loopExecutor = SessionManager.getInstance().getLoopExecutor();
        Events.Reset.addRunnableListener(() -> nextCoinFlipSeconds = 0);
    }

    @Override
    public void update() {
        super.update();
        if (loopExecutor.getSecondsPassed() >= nextCoinFlipSeconds) {
            coinFlip();
        }
    }

    private void activateRandomCurse() {
         var randomCurse = getRandomCurse();
         while (!validateCurse(randomCurse)) {
             randomCurse = getRandomCurse();
         }

         Events.TryUsingCurse.invoke(randomCurse);
         updateLastTwoAbilities(randomCurse);
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

    private boolean validateCurse(SpellType curse) {
        return !curse.equals(lastTwoAbilities[0]) || !curse.equals(lastTwoAbilities[1]);
    }


}

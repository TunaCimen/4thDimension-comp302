package org.LanceOfDestiny.domain.ymir;

import org.LanceOfDestiny.domain.behaviours.MonoBehaviour;
import org.LanceOfDestiny.domain.events.Event;
import org.LanceOfDestiny.domain.looper.LoopExecutor;
import org.LanceOfDestiny.domain.managers.AudioManager;
import org.LanceOfDestiny.domain.managers.SessionManager;
import org.LanceOfDestiny.domain.spells.SpellType;

import java.util.List;
import java.util.Random;

public class Ymir extends MonoBehaviour {

    private final LoopExecutor loopExecutor;
    private static final int COIN_FLIP_INTERVAL = 30;
    private static final double COIN_FLIP_PROBABILITY = 0.5;
    private int nextCoinFlipSeconds = 0;
    private final Random RANDOM = new Random();
    private SpellType[] lastTwoAbilities = new SpellType[2];

     public Ymir() {
        super();
        lastTwoAbilities[0] = SpellType.DOUBLE_ACCEL;
        lastTwoAbilities[1] = SpellType.HOLLOW_PURPLE;
        this.loopExecutor = SessionManager.getInstance().getLoopExecutor();
        Event.Reset.addRunnableListener(() -> nextCoinFlipSeconds = 0);
        Event.LoadGame.addRunnableListener(() -> nextCoinFlipSeconds = 0);
    }

    @Override
    public void update() {
        super.update();
        if (loopExecutor.getSecondsPassed() >= nextCoinFlipSeconds && SessionManager.getInstance().isSinglePlayer()) {
            coinFlip();
        }
    }

    private void activateRandomCurse() {
         var randomCurse = getRandomCurse();
         while (!validateCurse(randomCurse)) {
             randomCurse = getRandomCurse();
         }
         Event.ActivateCurse.invoke(randomCurse);
         updateLastTwoAbilities(randomCurse);
        AudioManager.getInstance().playYmirEffect();
    }

    private SpellType getRandomCurse() {
        List<SpellType> badSpells = SpellType.getBadSpells();
        return badSpells.get(RANDOM.nextInt(badSpells.size()));
    }

    private void updateLastTwoAbilities(SpellType newAbility) {
        lastTwoAbilities[0] = lastTwoAbilities[1];
        lastTwoAbilities[1] = newAbility;
    }
    public void updateLastTwoAbilitiesFromLoad(String s1, String s2){
         System.out.println(SpellType.valueOf(s1));
         lastTwoAbilities[0] = SpellType.valueOf(s1);
         lastTwoAbilities[1] = SpellType.valueOf(s2);
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
    public String retTwoSpellNames(){
         return lastTwoAbilities[0].toString() + " ," + lastTwoAbilities[1].toString();
    }


}

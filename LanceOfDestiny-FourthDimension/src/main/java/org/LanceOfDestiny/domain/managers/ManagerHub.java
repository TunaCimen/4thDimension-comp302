package org.LanceOfDestiny.domain.managers;

public class ManagerHub {

    private  InputManager inputManager;
    private  SpellManager spellManager;
    private  BarrierManager barrierManager;
    private  ScoreManager scoreManager;

    private static ManagerHub instance;
    public static ManagerHub getInstance() {
            if (instance == null) {
                instance = new ManagerHub();
                instance.inputManager = new InputManager();
                instance.spellManager = new SpellManager();
                instance.barrierManager = new BarrierManager();
                instance.scoreManager = new ScoreManager();
            }
        return instance;
    }

    public InputManager getInputManager() {
        return inputManager;
    }

    public  SpellManager getSpellManager() {
        return spellManager;
    }

    public BarrierManager getBarrierManager() {
        return barrierManager;
    }

    public  ScoreManager getScoreManager() {
        return scoreManager;
    }
}

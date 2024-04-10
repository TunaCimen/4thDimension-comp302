package org.LanceOfDestiny.domain.managers;

public class ManagerHub {

    private static InputManager inputManager;
    private static SpellManager spellManager;
    private static BarrierManager barrierManager;

    private static ScoreManager scoreManager;

    public static void initialize() {
        // Initialize all managers here
        var inputManager = new InputManager();
        var abilityManager = new SpellManager();
        var barrierManager = new BarrierManager();
        var scoreManager = new ScoreManager();
    }

    public static InputManager getInputManager() {
        return inputManager;
    }

    public static SpellManager getAbilityManager() {
        return spellManager;
    }

    public static BarrierManager getBarrierManager() {
        return barrierManager;
    }

    public static ScoreManager getScoreManager() {
        return scoreManager;
    }
}

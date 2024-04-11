package org.LanceOfDestiny.domain.managers;

import org.LanceOfDestiny.domain.player.FireBall;
import org.LanceOfDestiny.domain.player.MagicalStaff;
import org.LanceOfDestiny.domain.player.Player;

public class ManagerHub {
    MagicalStaff magicalStaff;
    Player player;
    FireBall fireBall;

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
    /**
     * Initializes the dependencies required for managers.
     * Needs to be invoked in the beginning of play mode.*/
    public void initDependencies(MagicalStaff magicalStaff, Player player, FireBall fireBall){
        this.magicalStaff = magicalStaff;
        this.player = player;
        this.fireBall = fireBall;
    }

    public InputManager getInputManager() {
        return inputManager;
    }

    public  SpellManager getSpellManager() {
        if(spellManager.fireBall == null){
            spellManager.fireBall = fireBall;
            spellManager.magicalStaff = magicalStaff;
        }
        return spellManager;
    }

    public BarrierManager getBarrierManager() {
        return barrierManager;
    }

    public  ScoreManager getScoreManager() {
        return scoreManager;
    }
}

package org.LanceOfDestiny.domain.player;

import org.LanceOfDestiny.domain.EventSystem.Events;
import org.LanceOfDestiny.domain.GameObject;
import org.LanceOfDestiny.domain.spells.SpellContainer;
import org.LanceOfDestiny.domain.managers.InputManager;
import org.LanceOfDestiny.domain.managers.ManagerHub;
import org.LanceOfDestiny.domain.managers.ScoreManager;

public class Player extends GameObject {

    private MagicalStaff magicalStaff;
    private SpellContainer spellContainer;

    private InputManager inputManager = ManagerHub.getInstance().getInputManager();
    private ScoreManager scoreManager = ManagerHub.getInstance().getScoreManager();


    private final int DEFAULT_CHANCES = 3;
    private final int MIN_CHANCES = 0;
    private int chancesLeft;


    public Player() {
        super();
        this.spellContainer = new SpellContainer();
        this.chancesLeft = DEFAULT_CHANCES;
        Events.UpdateChance.addListener(this::updateChances);

    }

    @Override
    public void Start() {
        super.Start();
    }

    @Override
    public void Update() {
        super.Update();
    }

    public void loseChance(){
        setChances(chancesLeft - 1);
    }

    public void gainChance() {
        setChances(chancesLeft + 1);
    }

    private void setChances(int chance) {
        this.chancesLeft = chance;
        this.chancesLeft = Math.max(this.chancesLeft, MIN_CHANCES);
        if(this.chancesLeft == MIN_CHANCES) Events.LoseGame.invoke();
    }

    public void setMagicalStaff(MagicalStaff magicalStaff){
        this.magicalStaff = magicalStaff;
    }

    public void updateChances(Object change){
        setChances(chancesLeft + (Integer) change);
    }

}

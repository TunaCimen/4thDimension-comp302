package org.LanceOfDestiny.domain.player;

import org.LanceOfDestiny.domain.GameObject;
import org.LanceOfDestiny.domain.abilities.SpellContainer;
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


    public Player(MagicalStaff magicalStaff) {
        super();
        this.magicalStaff = magicalStaff;
        this.spellContainer = new SpellContainer();
        this.chancesLeft = DEFAULT_CHANCES;

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
    }
}

package org.LanceOfDestiny.domain.player;

import org.LanceOfDestiny.domain.Behaviour;
import org.LanceOfDestiny.domain.EventSystem.Events;
import org.LanceOfDestiny.domain.GameObject;
import org.LanceOfDestiny.domain.spells.SpellContainer;
import org.LanceOfDestiny.domain.spells.SpellType;

public class Player extends Behaviour {

    private MagicalStaff magicalStaff;
    private final SpellContainer spellContainer;

    private final int DEFAULT_CHANCES = 3;
    private final int MIN_CHANCES = 0;
    private int chancesLeft;


    public Player() {
        super();
        this.spellContainer = new SpellContainer();
        this.chancesLeft = DEFAULT_CHANCES;
        Events.UpdateChance.addListener(this::updateChances);
        Events.TryUsingSpell.addListener(this::tryUsingSpell);
    }

    private void tryUsingSpell(Object objectSpellType) {
        SpellType spellType = (SpellType) objectSpellType;
        System.out.println(spellType + " tryUsingSpell()");
        spellContainer.activateSpell(spellType);
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void update() {
        super.update();
    }

    public void loseChance(){
        setChances(chancesLeft - 1);
    }

    public void gainChance() {
        setChances(chancesLeft + 1);
    }

    public int getChances() {
        return chancesLeft;
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

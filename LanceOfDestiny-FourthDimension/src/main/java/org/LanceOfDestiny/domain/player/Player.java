package org.LanceOfDestiny.domain.player;

import org.LanceOfDestiny.domain.events.Events;
import org.LanceOfDestiny.domain.behaviours.MonoBehaviour;
import org.LanceOfDestiny.domain.spells.SpellContainer;
import org.LanceOfDestiny.domain.spells.SpellType;

public class Player extends MonoBehaviour {

    private static final int DEFAULT_CHANCES = 1;
    private static final int MIN_CHANCES = 0;

    private SpellContainer spellContainer;
    private int chancesLeft;

    public Player() {
        super();
        this.spellContainer = new SpellContainer();
        this.chancesLeft = DEFAULT_CHANCES;
        //Events.UpdateChance.addListener(this::updateChances);
        Events.TryUsingSpell.addListener(this::tryUsingSpell);
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void update() {
        super.update();
    }

    private void tryUsingSpell(Object objectSpellType) {
        SpellType spellType = (SpellType) objectSpellType;
        spellContainer.activateSpell(spellType);
    }

    public void updateChances(Object change){
        setChancesLeft(chancesLeft + (Integer) change);
    }

    public void setChancesLeft(int chance) {
        this.chancesLeft = chance;
        this.chancesLeft = Math.max(chancesLeft, MIN_CHANCES);
        if(this.chancesLeft == MIN_CHANCES) Events.LoseGame.invoke();
    }

    public int getChancesLeft() {
        return chancesLeft;
    }
    public void resetSpells(){
        this.spellContainer = new SpellContainer();
    }

    public SpellContainer getSpellContainer() {
        return spellContainer;
    }
}

package org.LanceOfDestiny.domain.player;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.events.Event;
import org.LanceOfDestiny.domain.behaviours.MonoBehaviour;
import org.LanceOfDestiny.domain.spells.SpellContainer;
import org.LanceOfDestiny.domain.spells.SpellType;

public class Player extends MonoBehaviour {
    private SpellContainer spellContainer;
    private int chancesLeft;

    public Player() {
        super();
        this.spellContainer = new SpellContainer();
        this.chancesLeft = Constants.DEFAULT_CHANCES;
        Event.UpdateChance.addListener(this::updateChances);
        Event.ActivateSpell.addListener(this::activateSpell);
        Event.Reset.addRunnableListener(this::resetSpells);
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void update() {
        super.update();
    }

    private void activateSpell(Object objectSpellType) {
        SpellType spellType = (SpellType) objectSpellType;
        spellContainer.activateSpell(spellType);
    }

    public void updateChances(Object change){
        setChancesLeft(chancesLeft + (Integer) change);
    }

    public void setChancesLeft(int chance) {
        var MIN_CHANCES = 0;
        this.chancesLeft = chance;
        this.chancesLeft = Math.max(chancesLeft, MIN_CHANCES);
        if(this.chancesLeft == MIN_CHANCES) {
            Event.EndGame.invoke("You Lost");
        }
    }

    public int getChancesLeft() {
        return chancesLeft;
    }

    public void resetSpells(){
        this.spellContainer.getSpellMap().forEach((a,b)-> b = false);
        for(SpellType spellType : SpellType.values()){
            Event.ResetSpells.invoke(spellType);
        }
    }

    public SpellContainer getSpellContainer() {
        return spellContainer;
    }
}

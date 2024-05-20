package org.LanceOfDestiny.domain.player;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.events.Events;
import org.LanceOfDestiny.domain.behaviours.MonoBehaviour;
import org.LanceOfDestiny.domain.spells.CurseManager;
import org.LanceOfDestiny.domain.spells.SpellContainer;
import org.LanceOfDestiny.domain.spells.SpellType;

public class Player extends MonoBehaviour {
    private SpellContainer spellContainer;
    private int chancesLeft;

    public Player() {
        super();
        this.spellContainer = new SpellContainer();
        this.chancesLeft = Constants.DEFAULT_CHANCES;
        Events.UpdateChance.addListener(this::updateChances);
        Events.TryUsingSpell.addListener(this::tryUsingSpell);
        Events.Reset.addRunnableListener(this::resetSpells);
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
        if(spellType.isGood()) spellContainer.activateSpell(spellType);
    }

    public void updateChances(Object change){
        setChancesLeft(chancesLeft + (Integer) change);
    }

    public void setChancesLeft(int chance) {
        var MIN_CHANCES = 0;
        this.chancesLeft = chance;
        this.chancesLeft = Math.max(chancesLeft, MIN_CHANCES);
        if(this.chancesLeft == MIN_CHANCES) {
            Events.EndGame.invoke("You Lost");
        }
    }

    public int getChancesLeft() {
        return chancesLeft;
    }

    public void resetSpells(){
        this.spellContainer.getSpellMap().forEach((a,b)-> b = false);
        for(SpellType spellType : SpellType.values()){
            Events.ResetSpells.invoke(spellType);
        }
    }

    public SpellContainer getSpellContainer() {
        return spellContainer;
    }
}

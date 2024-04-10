package org.LanceOfDestiny.domain.abilities;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.GameObject;

public abstract class Spell extends GameObject {

    private int spellDuration = Constants.SPELL_DURATION; // in seconds, might need to change later
    private SpellType spellType;

    public Spell(){
        super();
    }

    public SpellType getSpellType() {
        return spellType;
    }

    public void setSpellType(SpellType spellType) {
        this.spellType = spellType;
    }

    public void activateSpell() {
        // TODO:
    }

    public void deactivateSpell() {
        // TODO:
    }
}

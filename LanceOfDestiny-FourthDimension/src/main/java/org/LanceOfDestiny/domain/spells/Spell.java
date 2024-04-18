package org.LanceOfDestiny.domain.spells;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.behaviours.Behaviour;

public abstract class Spell extends Behaviour {

    private final int spellDuration = Constants.SPELL_DURATION;
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
    }

    public void deactivateSpell() {
    }
}

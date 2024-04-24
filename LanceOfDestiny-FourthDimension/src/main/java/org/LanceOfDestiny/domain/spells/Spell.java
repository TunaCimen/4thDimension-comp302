package org.LanceOfDestiny.domain.spells;

public abstract class Spell {

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

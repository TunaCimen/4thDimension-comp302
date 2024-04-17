package org.LanceOfDestiny.domain.spells;

public class ChanceSpell extends Spell{

    public ChanceSpell() {
        super();
        setSpellType(SpellType.CHANCE);
    }

    @Override
    public void activateSpell() {
        super.activateSpell();
        // TODO: invoke event to increase chance
    }

    @Override
    public void deactivateSpell() {
        return;
    }
}

package org.LanceOfDestiny.domain.abilities;

import org.LanceOfDestiny.domain.player.MagicalStaff;

public class ExpansionSpell extends Spell{
    MagicalStaff magicalStaff;

    public ExpansionSpell(MagicalStaff magicalStaff) {
        super();
        setSpellType(SpellType.EXPANSION);
        this.magicalStaff = magicalStaff;
    }

    @Override
    public void activateSpell() {
        super.activateSpell();
        magicalStaff.enableExpansion();
    }

    @Override
    public void deactivateSpell() {
        super.deactivateSpell();
        magicalStaff.disableExpansion();
    }
}

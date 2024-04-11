package org.LanceOfDestiny.domain.abilities;

import org.LanceOfDestiny.domain.player.MagicalStaff;

public class CanonSpell extends Spell{

    MagicalStaff magicalStaff;
    public CanonSpell(MagicalStaff magicalStaff) {
        super();
        setSpellType(SpellType.CANON);
        this.magicalStaff = magicalStaff;
    }

    @Override
    public void activateSpell() {
        super.activateSpell();
        magicalStaff.enableCanons();
    }

    @Override
    public void deactivateSpell() {
        super.deactivateSpell();
        magicalStaff.disableCanons();
    }
}

package org.LanceOfDestiny.domain.spells;

import org.LanceOfDestiny.domain.managers.ManagerHub;
import org.LanceOfDestiny.domain.player.MagicalStaff;

public class CanonSpell extends Spell{

    MagicalStaff magicalStaff;
    public CanonSpell() {
        super();
        setSpellType(SpellType.CANON);
        this.magicalStaff = ManagerHub.getInstance().getMagicalStaff();
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

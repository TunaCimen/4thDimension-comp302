package org.LanceOfDestiny.domain.spells;

import org.LanceOfDestiny.domain.managers.SessionManager;
import org.LanceOfDestiny.domain.player.MagicalStaff;

public class ExpansionSpell extends Spell{
    MagicalStaff magicalStaff;

    public ExpansionSpell() {
        super();
        setSpellType(SpellType.EXPANSION);
        this.magicalStaff = SessionManager.getInstance().getMagicalStaff();
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

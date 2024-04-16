package org.LanceOfDestiny.domain.spells;

import org.LanceOfDestiny.domain.events.Events;

public class ExpansionSpell extends Spell{
//    MagicalStaff magicalStaff;

    public ExpansionSpell() {
        super();
        setSpellType(SpellType.EXPANSION);
//        this.magicalStaff = SessionManager.getInstance().getMagicalStaff();
    }

    @Override
    public void activateSpell() {
        super.activateSpell();
        Events.ActivateExpansion.invoke(true);
//        magicalStaff.enableExpansion();
    }

    @Override
    public void deactivateSpell() {
        super.deactivateSpell();
        Events.ActivateExpansion.invoke(false);
//        magicalStaff.disableExpansion();
    }
}

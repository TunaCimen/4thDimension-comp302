package org.LanceOfDestiny.domain.spells;

import org.LanceOfDestiny.domain.EventSystem.Events;
import org.LanceOfDestiny.domain.managers.SessionManager;
import org.LanceOfDestiny.domain.player.MagicalStaff;

public class CanonSpell extends Spell{

    public CanonSpell() {
        super();
        setSpellType(SpellType.CANON);
    }

    @Override
    public void activateSpell() {
        super.activateSpell();
        Events.ActivateCanons.invoke(true);
    }

    @Override
    public void deactivateSpell() {
        super.deactivateSpell();
//        magicalStaff.disableCanons();
        Events.ActivateCanons.invoke(false);
    }
}

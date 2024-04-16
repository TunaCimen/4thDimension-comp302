package org.LanceOfDestiny.domain.spells;

import org.LanceOfDestiny.domain.events.Events;

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
        Events.ActivateCanons.invoke(false);
    }
}

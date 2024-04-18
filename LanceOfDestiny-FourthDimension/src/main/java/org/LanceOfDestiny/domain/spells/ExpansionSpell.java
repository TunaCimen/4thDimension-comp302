package org.LanceOfDestiny.domain.spells;

import org.LanceOfDestiny.domain.events.Events;

public class ExpansionSpell extends Spell{

    public ExpansionSpell() {
        super();
        setSpellType(SpellType.EXPANSION);
    }

    @Override
    public void activateSpell() {
        super.activateSpell();
        Events.ActivateExpansion.invoke(true);
    }

    @Override
    public void deactivateSpell() {
        super.deactivateSpell();
        Events.ActivateExpansion.invoke(false);
    }
}

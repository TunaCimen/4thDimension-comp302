package org.LanceOfDestiny.domain.spells;

import org.LanceOfDestiny.domain.EventSystem.Events;

public class OverwhelmingFireBallSpell extends Spell {

    public OverwhelmingFireBallSpell() {
        super();
        setSpellType(SpellType.OVERWHELMING);
    }

    @Override
    public void activateSpell() {
        super.activateSpell();
        Events.ActivateOverwhelming.invoke(true);
    }

    @Override
    public void deactivateSpell() {
        super.deactivateSpell();
        Events.ActivateOverwhelming.invoke(false);
    }
}

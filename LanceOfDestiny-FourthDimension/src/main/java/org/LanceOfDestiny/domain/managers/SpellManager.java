package org.LanceOfDestiny.domain.managers;

import org.LanceOfDestiny.domain.abilities.*;
import org.LanceOfDestiny.domain.player.FireBall;
import org.LanceOfDestiny.domain.player.MagicalStaff;

public class SpellManager {

    MagicalStaff magicalStaff;
    FireBall fireBall;

    public SpellManager() {
    }

    public Spell createSpell(SpellType spellType) {
        Spell spell = switch (spellType) {
            case CHANCE -> new ChanceSpell();
            case EXPANSION -> new ExpansionSpell(magicalStaff);
            case OVERWHELMING -> new OverwhelmingFireBallSpell(fireBall);
            case CANON -> new CanonSpell(magicalStaff);
        };
        return spell;
    }


}

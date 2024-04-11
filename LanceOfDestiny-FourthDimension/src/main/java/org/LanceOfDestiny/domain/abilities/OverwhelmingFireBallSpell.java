package org.LanceOfDestiny.domain.abilities;

import org.LanceOfDestiny.domain.player.FireBall;

public class OverwhelmingFireBallSpell extends Spell{

    FireBall fireBall;

    public OverwhelmingFireBallSpell(FireBall fireBall) {
        super();
        setSpellType(SpellType.OVERWHELMING);
        this.fireBall = fireBall;
    }

    @Override
    public void activateSpell() {
        super.activateSpell();
        fireBall.enableOverwhelming();
    }

    @Override
    public void deactivateSpell() {
        super.deactivateSpell();
        fireBall.disableOverwhelming();
    }
}

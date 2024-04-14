package org.LanceOfDestiny.domain.spells;

import org.LanceOfDestiny.domain.managers.SessionManager;
import org.LanceOfDestiny.domain.player.FireBall;

public class OverwhelmingFireBallSpell extends Spell{
    FireBall fireBall;

    public OverwhelmingFireBallSpell() {
        super();
        setSpellType(SpellType.OVERWHELMING);
        this.fireBall = SessionManager.getInstance().getFireBall();
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

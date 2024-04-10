package org.LanceOfDestiny.domain.barriers;

import org.LanceOfDestiny.domain.abilities.SpellType;

public class RewardingBarrier extends Barrier{

    private SpellType spellType;

    public RewardingBarrier(int x, int y, BarrierTypes type, SpellType spellType) {
        super(x, y, type);
        this.spellType = spellType;
    }
}

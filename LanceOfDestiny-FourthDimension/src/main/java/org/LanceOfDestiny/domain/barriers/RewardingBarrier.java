package org.LanceOfDestiny.domain.barriers;

import org.LanceOfDestiny.domain.abilities.SpellTypes;

public class RewardingBarrier extends Barrier{

    private SpellTypes spellType;

    public RewardingBarrier(int x, int y, BarrierTypes type, SpellTypes spellType) {
        super(x, y, type);
        this.spellType = spellType;
    }
}

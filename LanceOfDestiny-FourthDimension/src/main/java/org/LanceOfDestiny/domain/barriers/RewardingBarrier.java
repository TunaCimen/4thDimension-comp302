package org.LanceOfDestiny.domain.barriers;

import org.LanceOfDestiny.domain.abilities.RewardBox;
import org.LanceOfDestiny.domain.abilities.RewardBoxFactory;
import org.LanceOfDestiny.domain.abilities.SpellType;

public class RewardingBarrier extends Barrier{

    RewardBox rewardBox;
    private SpellType spellType;

    public RewardingBarrier(int x, int y, BarrierTypes type) {
        super(x, y, type);
        this.rewardBox = RewardBoxFactory.getInstance().generateRandomRewardBox(x, y);
        this.spellType = rewardBox.getSpellType();
    }


}

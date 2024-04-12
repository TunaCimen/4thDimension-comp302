package org.LanceOfDestiny.domain.barriers;

import org.LanceOfDestiny.domain.abilities.RewardBox;
import org.LanceOfDestiny.domain.abilities.RewardBoxFactory;
import org.LanceOfDestiny.domain.abilities.SpellType;
import org.LanceOfDestiny.domain.physics.Vector;

public class RewardingBarrier extends Barrier{

    RewardBox rewardBox;
    private SpellType spellType;

    public RewardingBarrier(Vector position) {
        super(position, BarrierTypes.REWARDING);
        this.rewardBox = RewardBoxFactory.getInstance().generateRandomRewardBox(position);
        this.spellType = rewardBox.getSpellType();
    }


}

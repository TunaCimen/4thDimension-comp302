package org.LanceOfDestiny.domain.barriers;

import org.LanceOfDestiny.domain.physics.Collision;
import org.LanceOfDestiny.domain.physics.Vector;
import org.LanceOfDestiny.domain.player.FireBall;
import org.LanceOfDestiny.domain.spells.RewardBox;
import org.LanceOfDestiny.domain.spells.RewardBoxFactory;
import org.LanceOfDestiny.domain.spells.SpellType;

import java.awt.*;

public class RewardingBarrier extends Barrier {

    RewardBox rewardBox;
    private final SpellType spellType;

    public RewardingBarrier(Vector position) {
        super(position, BarrierTypes.REWARDING);
        this.rewardBox = RewardBoxFactory.generateRandomRewardBox(position);
        this.spellType = rewardBox.getSpellType();
        this.getSprite().color = Color.GREEN;
    }

    @Override
    public void onCollisionEnter(Collision collision) {
        super.onCollisionEnter(collision);
        var other = collision.getOther(this);

        if (!(other instanceof FireBall)) return;
        rewardBox.setFalling(true);
    }

    public SpellType getSpellType() {
        return spellType;
    }

    public RewardBox getRewardBox() {
        return rewardBox;
    }

}

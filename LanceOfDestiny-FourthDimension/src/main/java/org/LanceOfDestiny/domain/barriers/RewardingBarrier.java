package org.LanceOfDestiny.domain.barriers;

import org.LanceOfDestiny.domain.physics.Collision;
import org.LanceOfDestiny.domain.player.FireBall;
import org.LanceOfDestiny.domain.spells.RewardBox;
import org.LanceOfDestiny.domain.spells.RewardBoxFactory;
import org.LanceOfDestiny.domain.physics.Vector;

import java.awt.*;

public class RewardingBarrier extends Barrier{

    RewardBox rewardBox;

    public RewardingBarrier(Vector position) {
        super(position, BarrierTypes.REWARDING);
        this.rewardBox = RewardBoxFactory.generateRandomRewardBox(position);
        this.getSprite().color = Color.GREEN;
    }

    @Override
    public void onCollisionEnter(Collision collision) {
        super.onCollisionEnter(collision);
        var other = collision.getOther(this);

        if(!(other instanceof FireBall)) return;
        rewardBox.setFalling(true);
        rewardBox.getCollider().setEnabled(true);
        reduceLife();
    }

}

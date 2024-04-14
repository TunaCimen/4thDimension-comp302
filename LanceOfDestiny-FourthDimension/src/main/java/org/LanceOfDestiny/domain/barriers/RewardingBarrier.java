package org.LanceOfDestiny.domain.barriers;

import org.LanceOfDestiny.domain.physics.Collision;
import org.LanceOfDestiny.domain.player.FireBall;
import org.LanceOfDestiny.domain.spells.RewardBox;
import org.LanceOfDestiny.domain.spells.RewardBoxFactory;
import org.LanceOfDestiny.domain.spells.SpellType;
import org.LanceOfDestiny.domain.physics.Vector;

import java.awt.*;

public class RewardingBarrier extends Barrier{

    RewardBox rewardBox;
    private SpellType spellType;

    public RewardingBarrier(Vector position) {
        super(position, BarrierTypes.REWARDING);
        this.rewardBox = RewardBoxFactory.getInstance().generateRandomRewardBox(position);
        this.spellType = rewardBox.getSpellType();
        this.getSprite().color = Color.GREEN;
    }

    @Override
    public void onCollisionEnter(Collision collision) {
        super.onCollisionEnter(collision);
        var other = collision.getOther(this);

        if(!(other instanceof FireBall)) return;




    }
}

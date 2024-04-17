package org.LanceOfDestiny.domain.barriers;

import org.LanceOfDestiny.domain.physics.Collision;
import org.LanceOfDestiny.domain.player.FireBall;
import org.LanceOfDestiny.domain.spells.Hex;
import org.LanceOfDestiny.domain.spells.RewardBox;
import org.LanceOfDestiny.domain.spells.RewardBoxFactory;
import org.LanceOfDestiny.domain.physics.Vector;
import org.LanceOfDestiny.domain.sprite.ImageLibrary;
import org.LanceOfDestiny.domain.sprite.ImageOperations;

import java.awt.*;

public class RewardingBarrier extends Barrier{

    RewardBox rewardBox;

    public RewardingBarrier(Vector position) {
        super(position, BarrierTypes.REWARDING);
        this.rewardBox = RewardBoxFactory.generateRandomRewardBox(position);
        this.getSprite().color = new Color(0,0,0,0);
        this.getSprite().addImage(ImageOperations.resizeImage(ImageLibrary.RewardingBarrier.getImage()
                ,this.getSprite().width(),this.getSprite().height()));
    }

    @Override
    public void onCollisionEnter(Collision collision) {
        super.onCollisionEnter(collision);
        var other = collision.getOther(this);

        if(!(other instanceof FireBall  || other instanceof Hex)) return;
        rewardBox.setFalling(true);
        rewardBox.getCollider().setEnabled(true);
        reduceLife();
    }

    @Override
    public void onTriggerEnter(Collision collision) {
        super.onCollisionEnter(collision);
        var other = collision.getOther(this);

        if(!(other instanceof FireBall)) return;
        rewardBox.setFalling(true);
        rewardBox.getCollider().setEnabled(true);
        reduceLife();
    }

}

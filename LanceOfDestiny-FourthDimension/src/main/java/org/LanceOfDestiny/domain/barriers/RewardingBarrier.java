package org.LanceOfDestiny.domain.barriers;

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
        this.getSprite().setImage(ImageOperations.resizeImage(ImageLibrary.RewardingBarrier.getImage()
                ,this.getSprite().width(),this.getSprite().height()));
        this.defaultSprite = sprite;
        adjustFrozenSprite();
    }

    @Override
    public void kill() {
        rewardBox.setFalling(true);
        rewardBox.getCollider().setEnabled(true);
        super.kill();
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    public void setPosition(Vector position) {
        super.setPosition(position);
        rewardBox.setPosition(position);
    }



}

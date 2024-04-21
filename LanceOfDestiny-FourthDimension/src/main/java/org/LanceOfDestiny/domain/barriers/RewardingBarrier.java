package org.LanceOfDestiny.domain.barriers;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.spells.RewardBox;
import org.LanceOfDestiny.domain.spells.RewardBoxFactory;
import org.LanceOfDestiny.domain.physics.Vector;
import org.LanceOfDestiny.domain.sprite.ImageLibrary;
import org.LanceOfDestiny.domain.sprite.ImageOperations;

import java.awt.*;

public class RewardingBarrier extends Barrier{

    RewardBox rewardBox;
    private Vector offset = new Vector(0,- 0.3 * Constants.BARRIER_HEIGHT);

    public RewardingBarrier(Vector position) {
        super(position, BarrierTypes.REWARDING);
        this.rewardBox = RewardBoxFactory.generateRandomRewardBox(position);
        this.getSprite().color = new Color(0,0,0,0);
        this.getSprite().setImage(ImageOperations.resizeImage(ImageLibrary.RewardingBarrier.getImage()
                ,this.getSprite().width(), (int) (this.getSprite().height()*1.5)));
    }

    @Override
    public void kill() {
        rewardBox.setFalling(true);
        rewardBox.getCollider().setEnabled(true);
        super.kill();
    }

    @Override
    public void setPosition(Vector position) {
        super.setPosition(position);
        this.position = position.add(offset);
        rewardBox.setPosition(position);
    }



}

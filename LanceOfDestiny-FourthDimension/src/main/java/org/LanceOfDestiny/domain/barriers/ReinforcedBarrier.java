package org.LanceOfDestiny.domain.barriers;

import org.LanceOfDestiny.domain.physics.Collision;
import org.LanceOfDestiny.domain.physics.Vector;
import org.LanceOfDestiny.domain.sprite.ImageLibrary;
import org.LanceOfDestiny.domain.sprite.ImageOperations;

import java.awt.*;
import java.util.Random;

public class ReinforcedBarrier extends Barrier {
    public static final double MOVE_PROBABILITY = 0.2;

    public ReinforcedBarrier(Vector position, int hitsRequired) {
        super(position, BarrierTypes.REINFORCED, hitsRequired);
        if ((new Random()).nextDouble() <= MOVE_PROBABILITY) isMoving = true;
        initDirection();
        getSprite().color = new Color(0,0,0,0);
        this.getSprite().setImage(ImageOperations.resizeImageToSprite(ImageLibrary.ReinforcedBarrier.getImage(),getSprite()));
        getSprite().number = String.valueOf(hitsLeft);
        this.defaultSprite = sprite;
        adjustFrozenSprite();
    }

    @Override
    public void update() {
        super.update();
        getSprite().number = String.valueOf(hitsLeft);
    }

    @Override
    public void onCollisionEnter(Collision collision) {
        super.onCollisionEnter(collision);
        var other = collision.getOther(this);

        if ((other instanceof Barrier && isMoving)) {
            getCollider().setVelocity(getCollider().getVelocity().scale(-1));
        }
    }

}

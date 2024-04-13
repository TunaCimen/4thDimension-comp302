package org.LanceOfDestiny.domain.barriers;

import org.LanceOfDestiny.domain.GameObject;
import org.LanceOfDestiny.domain.physics.Collision;
import org.LanceOfDestiny.domain.physics.Vector;
import org.LanceOfDestiny.domain.player.FireBall;
import org.LanceOfDestiny.ui.Sprite;

import java.util.Random;

public class SimpleBarrier extends Barrier{
    public static final double MOVE_PROBABILITY = 0.2;

    public SimpleBarrier(Vector position) {
        super(position, BarrierTypes.SIMPLE);
        if((new Random()).nextDouble() <= MOVE_PROBABILITY) isMoving = true;
    }

    @Override
    public void onCollisionEnter(Collision collision) {
        super.onCollisionEnter(collision);
        GameObject other = collision.getOther(this);
        if (other instanceof FireBall) {
            this.Destroy();  // Assuming a method to handle destruction or deactivation
        }
    }
}

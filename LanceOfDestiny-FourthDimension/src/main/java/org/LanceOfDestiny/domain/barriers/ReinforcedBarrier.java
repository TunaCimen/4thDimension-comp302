package org.LanceOfDestiny.domain.barriers;

import org.LanceOfDestiny.domain.physics.Collision;
import org.LanceOfDestiny.domain.physics.Vector;
import org.LanceOfDestiny.domain.player.FireBall;

import java.awt.*;
import java.util.Random;

public class ReinforcedBarrier extends Barrier{
    public static final double MOVE_PROBABILITY = 0.2;

    public ReinforcedBarrier(Vector position, int hitsRequired) {
        super(position, BarrierTypes.REINFORCED, hitsRequired);
        if((new Random()).nextDouble() <= MOVE_PROBABILITY) isMoving = true;
        getSprite().color = Color.CYAN;
        getSprite().number = hitsLeft;
    }

    @Override
    public void Update() {
        super.Update();
        getSprite().number = hitsLeft;
    }

    @Override
    public void onCollisionEnter(Collision collision) {
        super.onCollisionEnter(collision);
        var other = collision.getOther(this);

        if(!(other instanceof FireBall)) return;

        hitsLeft --;

        if(hitsLeft <= 0) this.Destroy();



    }
}

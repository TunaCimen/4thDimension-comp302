package org.LanceOfDestiny.domain.barriers;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.EventSystem.Events;
import org.LanceOfDestiny.domain.physics.Collision;
import org.LanceOfDestiny.domain.physics.Vector;
import org.LanceOfDestiny.domain.player.FireBall;
import org.LanceOfDestiny.domain.player.MagicalStaff;

import java.awt.*;
import java.util.Random;

public class ExplosiveBarrier extends Barrier{
    public static final double RADIUS = Constants.EXPLOSIVE_RADIUS;
    public static final double MOVE_PROBABILITY = 0.2;
    private boolean isFalling = false;

    public ExplosiveBarrier(Vector position) {
        super(position, BarrierTypes.EXPLOSIVE);
        if((new Random()).nextDouble() <= MOVE_PROBABILITY) isMoving = true;
        getSprite().color = Color.RED;
    }

    @Override
    public void Update() {
        super.Update();
        if(isFalling) setPosition(this.getCollider().getVelocity());
    }

    @Override
    public void onCollisionEnter(Collision collision) {
        super.onCollisionEnter(collision);
        var other = collision.getOther(this);

        if(other instanceof MagicalStaff){
            Events.UpdateChance.invoke(-1);
            System.out.println("EXPLOSSIVEEEE!!!");
            Destroy();
        }

        if(other instanceof FireBall){
            isFalling = true;
        }


    }
}

package org.LanceOfDestiny.domain.barriers;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.events.Events;
import org.LanceOfDestiny.domain.physics.Collision;
import org.LanceOfDestiny.domain.physics.Vector;
import org.LanceOfDestiny.domain.player.FireBall;
import org.LanceOfDestiny.domain.player.MagicalStaff;

import java.awt.*;
import java.util.Random;

public class ExplosiveBarrier extends Barrier {
    public static final double MOVE_PROBABILITY = 0.2;

    Vector initPos;
    private boolean isFalling = false;
    private double angleInDegrees = 0;

    public ExplosiveBarrier(Vector position) {
        super(position, BarrierTypes.EXPLOSIVE);
        if ((new Random()).nextDouble() <= MOVE_PROBABILITY) isMoving = true;
        getSprite().color = Color.RED;
        this.getCollider().setTrigger(false);
        initPos = getPosition();
    }

    @Override
    public void update() {
        if (isFalling) {
            setPosition(getPosition().add(this.getCollider().getVelocity()));
            return;
        }
        if (isMoving) {
            setPosition(getCircularMotionVector());
        }
    }

    public Vector getCircularMotionVector() {
        angleInDegrees += 360 * Constants.UPDATE_RATE; //Speed 360 deg per second.
        double angleInRadians = Math.toRadians(angleInDegrees);
        double x = initPos.getX() + Constants.CIRCULAR_MOTION_RADIUS * Math.cos(angleInRadians);
        double y = initPos.getY() + Constants.CIRCULAR_MOTION_RADIUS * Math.sin(angleInRadians);
        return new Vector(x, y);
    }

    @Override
    public void onCollisionEnter(Collision collision) {
        super.onCollisionEnter(collision);
        var other = collision.getOther(this);

        if (other instanceof FireBall) {
            isFalling = true;
            getCollider().setTrigger(true);
            this.addScore();
        }
    }

    @Override
    public void onTriggerEnter(Collision collision) {
        var other = collision.getOther(this);

        if (other instanceof MagicalStaff) {
            Events.UpdateChance.invoke(-1);
            destroy();
        }

        if (other == null) {
            destroy();
        }
    }

    @Override
    public void kill() {
        addScore();
    }

    private void addScore() {
        Events.UpdateScore.invoke();
    }
}

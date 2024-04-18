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
            // allows the barrier to actually fall
            getCollider().setVelocity(new Vector(0, 2));
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

    @Override
    public Vector getDirection() {
        if (!isMoving) {
            return Vector.getZeroVector();
        }
            // Increment to get the next angle in radians for the small change
            double nextAngleInDegrees = angleInDegrees + 1; // Increase by 1 degree for derivative approximation
            double nextAngleInRadians = Math.toRadians(nextAngleInDegrees);

            // Compute the next position based on the next angle
            double nextX = initPos.getX() + Constants.CIRCULAR_MOTION_RADIUS * Math.cos(nextAngleInRadians);
            double nextY = initPos.getY() + Constants.CIRCULAR_MOTION_RADIUS * Math.sin(nextAngleInRadians);

            // Current position at current angle
            double currentX = initPos.getX() + Constants.CIRCULAR_MOTION_RADIUS * Math.cos(Math.toRadians(angleInDegrees));
            double currentY = initPos.getY() + Constants.CIRCULAR_MOTION_RADIUS * Math.sin(Math.toRadians(angleInDegrees));

            // Calculate the tangent vector (direction) by subtracting current position from next position
            Vector direction = new Vector(nextX - currentX, nextY - currentY);

            // Normalize the direction vector to get a unit vector (length = 1)
            return direction.normalize();


    }
}

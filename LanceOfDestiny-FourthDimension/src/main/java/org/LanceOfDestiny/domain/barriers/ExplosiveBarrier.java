package org.LanceOfDestiny.domain.barriers;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.events.Events;
import org.LanceOfDestiny.domain.physics.Collision;
import org.LanceOfDestiny.domain.physics.Vector;
import org.LanceOfDestiny.domain.player.MagicalStaff;
import org.LanceOfDestiny.domain.sprite.ImageLibrary;
import org.LanceOfDestiny.domain.sprite.ImageOperations;

import java.awt.*;
import java.util.Random;

public class ExplosiveBarrier extends Barrier {
    public static final double MOVE_PROBABILITY = 0.2;

    Vector initPos;
    private boolean isFalling = false;
    private double angleInDegrees = 0;
    private final Vector SHIFT= new Vector(14,9);

    public ExplosiveBarrier(Vector position) {
        super(position, BarrierTypes.EXPLOSIVE);
        this.position = new Vector(position.getX(), position.getY());
        if ((new Random()).nextDouble() <= MOVE_PROBABILITY) isMoving = true;
        adjustSprite();
        initPos = getPosition();
    }

    private void adjustSprite() {
        this.getSprite().color = new Color(0,0,0,0);
        this.getSprite().setImage(ImageOperations.resizeImage(ImageLibrary.ExplosiveBarrier.getImage(), sprite.width()*2,sprite.height()*2));
        getSprite().setImage(ImageOperations.resizeImage(ImageLibrary.ExplosiveBarrier.getImage(),Constants.EXPLOSIVE_RADIUS*2,Constants.EXPLOSIVE_RADIUS*2));
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
        isFalling = true;
        // allows the barrier to actually fall
        getCollider().setVelocity(new Vector(0, 2));
        getCollider().setTrigger(true);
        this.addScore();
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

    @Override
    public void setPosition(Vector position) {
        this.position = position;
        this.initPos = position;
    }

    @Override
    public void shiftPosition(Vector position) {
        this.position = position.add(SHIFT);
        this.initPos = position.add(SHIFT);
    }

    public boolean isFalling() {
        return isFalling;
    }
}

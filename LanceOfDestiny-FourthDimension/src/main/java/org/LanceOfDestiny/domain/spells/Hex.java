package org.LanceOfDestiny.domain.spells;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.barriers.Barrier;
import org.LanceOfDestiny.domain.barriers.ExplosiveBarrier;
import org.LanceOfDestiny.domain.behaviours.GameObject;
import org.LanceOfDestiny.domain.events.Events;
import org.LanceOfDestiny.domain.managers.SessionManager;
import org.LanceOfDestiny.domain.physics.ColliderFactory;
import org.LanceOfDestiny.domain.physics.ColliderType;
import org.LanceOfDestiny.domain.physics.Collision;
import org.LanceOfDestiny.domain.physics.Vector;
import org.LanceOfDestiny.domain.player.FireBall;
import org.LanceOfDestiny.domain.sprite.BallSprite;

import java.awt.*;

public class Hex extends GameObject {
    public static final int HEX_RADIUS = Constants.HEX_RADIUS;
    public static final double HEX_SPEED = Constants.HEX_SPEED;
    public final Vector velocity = new Vector(0, -5);
    private final Canon canon;
    private boolean isVisible = false;
    private boolean isLeft;

    public Hex(Canon canon) {
        super();
        this.canon = canon;
        this.position = canon.getPosition().add(new Vector(Constants.CANON_WIDTH, -HEX_RADIUS * 2));
        this.isLeft = canon.isLeft;
        createColliderAndSprite();
        Events.Reset.addRunnableListener(this::disable);
        Events.LoadGame.addRunnableListener(this::disable);
    }

    private void createColliderAndSprite() {
        this.sprite = new BallSprite(this, Color.WHITE, HEX_RADIUS);
        this.collider = ColliderFactory.createBallCollider(this, velocity, ColliderType.STATIC, HEX_RADIUS);
        collider.setEnabled(false);
        collider.setTrigger(true);
        sprite.setVisible(false);
    }

    @Override
    public void update() {
        super.update();
        if (isVisible) {
            setPosition(getPosition().add(collider.getVelocity()));
        }
    }

    @Override
    public void onTriggerEnter(Collision collision) {
        super.onTriggerEnter(collision);
        var other = collision.getOther(this);

        if( other == null) {
            disable();
        }

        if(other instanceof FireBall) return;
        if(other instanceof ExplosiveBarrier explosiveBarrier && explosiveBarrier.isFalling()) return;

        if (other instanceof Barrier) {
            disable();
            if (!((Barrier) other).isFrozen())
                ((Barrier) other).reduceLife();
        }
    }

    public void shoot() {
        isVisible = true;
        sprite.setVisible(true);
        collider.setEnabled(true);

        var magicalStaff = SessionManager.getInstance().getMagicalStaff();
        var angle = magicalStaff.getAngle();

        this.position = getShootingPosition();
        var velocity = Vector.getVelocityByAngleAndMagnitude((int) -HEX_SPEED, angle);
        collider.setVelocity(velocity);

    }

    public Vector getShootingPosition() {
        var magicalStaff = SessionManager.getInstance().getMagicalStaff();
        var staffPositionLeft = magicalStaff.getPosition();
        var staffPositionRight = staffPositionLeft.add(new Vector(Constants.STAFF_WIDTH, 0));
        var angle = canon.getAngle();
        var canonPosition = canon.getPosition();
        var hexPosition = canonPosition.add(new Vector((double) Constants.CANON_WIDTH / 2, 0));
        var center = (isLeft ?
                staffPositionLeft.add(new Vector((double) Constants.STAFF_WIDTH / 2, 0)) :
                staffPositionRight.subtract(new Vector((double) Constants.STAFF_WIDTH / 2, 0)));
        return calculateRotatedPosition(center, hexPosition, angle);
    }

    public Vector calculateRotatedPosition(Vector center, Vector oldPosition, double angle) {
        Vector translatedPosition = oldPosition.subtract(center);
        double cosAngle = Math.cos(angle);
        double sinAngle = Math.sin(angle);
        double rotatedX = translatedPosition.getX() * cosAngle - translatedPosition.getY() * sinAngle;
        double rotatedY = translatedPosition.getX() * sinAngle + translatedPosition.getY() * cosAngle;
        return new Vector(rotatedX, rotatedY).add(center);
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public void disable(){
        sprite.setVisible(false);
        collider.setEnabled(false);
        setVisible(false);
    }
}

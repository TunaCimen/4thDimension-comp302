package org.LanceOfDestiny.domain.player;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.EventSystem.Events;
import org.LanceOfDestiny.domain.GameObject;
import org.LanceOfDestiny.domain.managers.SessionManager;
import org.LanceOfDestiny.domain.physics.Collider;
import org.LanceOfDestiny.domain.physics.ColliderFactory;
import org.LanceOfDestiny.domain.physics.ColliderType;
import org.LanceOfDestiny.domain.physics.Vector;
import org.LanceOfDestiny.domain.sprite.BallSprite;
import org.LanceOfDestiny.domain.sprite.Sprite;

import java.awt.*;

public class FireBall extends GameObject {
    private final int radius = Constants.FIREBALL_RADIUS;
    BallSprite ballSprite;
    private Collider collider;
    private boolean isOverwhelming = false;

    private boolean isAttached = true;
    private int defaultSpeed = Constants.FIREBALL_SPEED;
    private double currentSpeed;
    private MagicalStaff magicalStaff;


    public FireBall() {
        super();
        this.position = Constants.FIREBALL_POSITION;
        this.currentSpeed = defaultSpeed;
        this.collider = ColliderFactory.createBallCollider(this, Vector.getZeroVector(), ColliderType.DYNAMIC, radius);
        this.ballSprite = new BallSprite(this, Color.black, Constants.FIREBALL_RADIUS);
        Events.ActivateOverwhelming.addListener(this::handleOverwhelming);
        Events.ShootBall.addRunnableListener(this::shootBall);
    }

    private void shootBall() {
        isAttached = false;
        Vector velocity = Vector.getVelocityByAngleAndMagnitude(defaultSpeed, magicalStaff.getAngle());
        collider.setVelocity(velocity);
    }


    @Override
    public void start() {
        super.start();
        magicalStaff = SessionManager.getInstance().getMagicalStaff();


    }

    @Override
    public void update() {
        if (getPosition().getY() >= Constants.SCREEN_HEIGHT - 40) fireBallDropped();
        if (isAttached) {
            var attachedPosition = new Vector(
                    magicalStaff.getPosition().getX() + Constants.STAFF_WIDTH / 2f + (Constants.STAFF_WIDTH / 4f) * Math.sin(magicalStaff.getAngle()),
                    magicalStaff.getPosition().getY() + Constants.FIREBALL_RADIUS + (Constants.STAFF_WIDTH / 4f) * Math.cos(magicalStaff.getAngle() + Math.PI)
            );
            collider.setPosition(attachedPosition);
        }
        else setPosition(getPosition().add(collider.getVelocity()));
    }

    private void handleOverwhelming(Object object) {
        isOverwhelming = (Boolean) object;
    }

    @Override
    public Sprite getSprite() {
        return ballSprite;
    }

    public void fireBallDropped() {
        Events.UpdateChance.invoke(-1);
        isAttached = true;
    }


    public void enableOverwhelming() {
        isOverwhelming = true;
    }

    public void disableOverwhelming() {
        isOverwhelming = false;
    }


}

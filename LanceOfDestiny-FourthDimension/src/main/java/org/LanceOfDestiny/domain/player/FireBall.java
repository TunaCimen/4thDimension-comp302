package org.LanceOfDestiny.domain.player;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.barriers.Barrier;
import org.LanceOfDestiny.domain.behaviours.GameObject;
import org.LanceOfDestiny.domain.events.Event;
import org.LanceOfDestiny.domain.managers.SessionManager;
import org.LanceOfDestiny.domain.physics.*;
import org.LanceOfDestiny.domain.sprite.BallSprite;
import org.LanceOfDestiny.domain.sprite.ImageLibrary;
import org.LanceOfDestiny.domain.sprite.ImageOperations;

import java.awt.*;

public class FireBall extends GameObject {
    private boolean isOverwhelming = false;
    private boolean isAttached = true;
    private int speed = Constants.FIREBALL_SPEED;
    private MagicalStaff magicalStaff;
    private Image defaultImage;
    private Image overwelmingImage;

    public FireBall() {
        super();
        this.position = Constants.FIREBALL_POSITION;
        createColliderAndSprite();
        Event.ActivateOverwhelming.addListener(this::handleOverwhelming);
        Event.ActivateDoubleAccel.addListener(this::handleDoubleAccel);
        Event.ShootBall.addRunnableListener(this::shootBall);
        Event.LoadGame.addRunnableListener(() -> isAttached = true);
        Event.EndGame.addRunnableListener(() -> isAttached = true);
        Event.Reset.addRunnableListener(this::resetFireballPosition);
        Event.Reset.addRunnableListener(() -> speed = Constants.FIREBALL_SPEED);
        Event.LoadGame.addRunnableListener(this::resetFireballPosition);
    }


    private void createColliderAndSprite() {
        int radius = Constants.FIREBALL_RADIUS;
        this.collider = ColliderFactory.createBallCollider(this, Vector.getZeroVector(), ColliderType.DYNAMIC, radius);
        this.sprite = new BallSprite(this, Color.black, Constants.FIREBALL_RADIUS);
        defaultImage = ImageOperations.resizeImage(ImageLibrary.FireBall.getImage(), sprite.width() * 2, sprite.height() * 2);
        overwelmingImage = ImageOperations.resizeImage(ImageLibrary.FireBallOverwhelmed.getImage(), sprite.width() * 2, sprite.height() * 2);
        this.getSprite().setImage(defaultImage);
    }

    @Override
    public void start() {
        super.start();
        magicalStaff = SessionManager.getInstance().getMagicalStaff();
    }

    @Override
    public void update() {
        if (isAttached) {
            var staffWidth = (magicalStaff.isExpanded ? Constants.STAFF_WIDTH * 2 : Constants.STAFF_WIDTH);
            var attachedPosition = getAttachedPosition(staffWidth);
            collider.setPosition(attachedPosition);
        } else setPosition(getPosition().add(collider.getVelocity()));
    }

    private Vector getAttachedPosition(int staffWidth) {
        return new Vector(magicalStaff.getPosition().getX() + staffWidth / 2f + (Constants.STAFF_WIDTH / 4f) * Math.sin(magicalStaff.getAngle()), magicalStaff.getPosition().getY() + Constants.FIREBALL_RADIUS * 3.5 + (staffWidth / 4f) * Math.cos(magicalStaff.getAngle() + Math.PI));
    }

    private void shootBall() {
        if (!isAttached) return;
        isAttached = false;
        Vector velocity = Vector.getVelocityByAngleAndMagnitude(speed, magicalStaff.getAngle());
        collider.setVelocity(velocity);
    }

    private void handleOverwhelming(Object object) {
        if ((Boolean) object) enableOverwhelming();
        else disableOverwhelming();
    }

    public void enableOverwhelming() {
        isOverwhelming = true;
        ((BallSprite)this.sprite).setComet(true);
    }

    public void disableOverwhelming() {
        isOverwhelming = false;
        ((BallSprite)this.sprite).setComet(false);
    }

    private void handleDoubleAccel(Object object) {
        if ((boolean) object) activateDoubleAccel();
        else deactivateDoubleAccel();
    }

    private void activateDoubleAccel() {
        System.out.println("Activating Double Accel");
        speed = Constants.FIREBALL_SPEED / 2;
        if (!isAttached) this.collider.setVelocity(getCollider().getVelocity().scale(0.5));
    }

    private void deactivateDoubleAccel() {
        System.out.println("Deactivating Double Accel");
        speed = Constants.FIREBALL_SPEED;
        if (!isAttached) this.collider.setVelocity(getCollider().getVelocity().scale(2));
    }

    public void fireBallDropped() {
        Event.UpdateChance.invoke(-1);
        isAttached = true;
    }

    @Override
    public void onCollisionEnter(Collision collision) {
        super.onCollisionEnter(collision);
        GameObject other = collision.getOther(this);
        if (other instanceof MagicalStaff) return;
        if (other == null && getPosition().getY() >= Constants.SCREEN_HEIGHT - 70) {
            fireBallDropped();
            return;
        }

        if (!(other instanceof Barrier barrier)) return;

        if (barrier.isFrozen()) {
            if (!isOverwhelming) return;
            barrier.reduceLife();
            PhysicsManager.getInstance().handleFireballBounce(collision);
        } else {
            if (isOverwhelming) barrier.kill();
            else barrier.reduceLife();
        }

    }

    public boolean isOverwhelming() {
        return isOverwhelming;
    }

    public void resetFireballPosition() {
        this.position = Constants.FIREBALL_POSITION;
    }
}

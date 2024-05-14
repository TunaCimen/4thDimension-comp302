package org.LanceOfDestiny.domain.player;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.barriers.Barrier;
import org.LanceOfDestiny.domain.behaviours.GameObject;
import org.LanceOfDestiny.domain.events.Events;
import org.LanceOfDestiny.domain.managers.SessionManager;
import org.LanceOfDestiny.domain.physics.*;
import org.LanceOfDestiny.domain.sprite.BallSprite;
import org.LanceOfDestiny.domain.sprite.ImageLibrary;
import org.LanceOfDestiny.domain.sprite.ImageOperations;

import java.awt.*;

public class FireBall extends GameObject {
    private boolean isOverwhelming = false;
    private boolean isAttached = true;
    private final int defaultSpeed = Constants.FIREBALL_SPEED;
    private MagicalStaff magicalStaff;
    private Image defaultImage;
    private Image overwelmingImage;

    public FireBall() {
        super();
        this.position = Constants.FIREBALL_POSITION;
        createColliderAndSprite();
        Events.ActivateOverwhelming.addListener(this::handleOverwhelming);
        Events.ShootBall.addRunnableListener(this::shootBall);
        Events.LoadGame.addRunnableListener(() -> isAttached = true);
        Events.EndGame.addRunnableListener(()->isAttached=true);
        Events.Reset.addRunnableListener(this::resetFireballPosition);
        Events.LoadGame.addRunnableListener(this::resetFireballPosition);
        Events.ActivateDoubleAccel.addListener(this::handleDoubleAccel);
    }


    private void createColliderAndSprite() {
        int radius = Constants.FIREBALL_RADIUS;
        this.collider = ColliderFactory.createBallCollider(this, Vector.getZeroVector(), ColliderType.DYNAMIC, radius);
        this.sprite = new BallSprite(this, Color.black, Constants.FIREBALL_RADIUS);
        defaultImage = ImageOperations.resizeImage(ImageLibrary.FireBall.getImage()
                , sprite.width()*2
                , sprite.height()*2);
        overwelmingImage = ImageOperations.resizeImage(ImageLibrary.FireBallOverwhelmed.getImage(),
                sprite.width()*2,
                sprite.height()*2);
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
            var staffWidth =  (magicalStaff.isExpanded ? Constants.STAFF_WIDTH * 2 : Constants.STAFF_WIDTH);
            var attachedPosition = getAttachedPosition(staffWidth);
            collider.setPosition(attachedPosition);
        } else setPosition(getPosition().add(collider.getVelocity()));
    }

    private Vector getAttachedPosition(int staffWidth) {
        return new Vector(
                magicalStaff.getPosition().getX() + staffWidth / 2f + (Constants.STAFF_WIDTH / 4f) * Math.sin(magicalStaff.getAngle()),
                magicalStaff.getPosition().getY() + Constants.FIREBALL_RADIUS * 3.5 + (staffWidth / 4f) * Math.cos(magicalStaff.getAngle() + Math.PI)
        );
    }

    private void shootBall() {
        if(!isAttached) return;
        isAttached = false;
        Vector velocity = Vector.getVelocityByAngleAndMagnitude(defaultSpeed, magicalStaff.getAngle());
        collider.setVelocity(velocity);
    }

    private void handleOverwhelming(Object object) {
        if ((Boolean) object) enableOverwhelming();
        else disableOverwhelming();
    }

    public void enableOverwhelming() {
        isOverwhelming = true;
        sprite.color = Color.ORANGE;
        sprite.setImage(overwelmingImage);
    }

    public void disableOverwhelming() {
        isOverwhelming = false;
        sprite.color = Color.BLACK;
        sprite.setImage(defaultImage);
    }

    private void handleDoubleAccel(Object object) {
        if((boolean) object) enableDoubleAccel();
        else disableDoubleAccel();
    }

    private void enableDoubleAccel() {
        //TODO: set fireball speed to half
    }
    private void disableDoubleAccel() {
        //TODO: set fireball speed to default
    }

    public void fireBallDropped() {
        Events.UpdateChance.invoke(-1);
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
        if (other instanceof Barrier) {
            if(isOverwhelming) ((Barrier) other).kill();
            else ((Barrier) other).reduceLife();
        }
    }

    public boolean isOverwhelming() {
        return isOverwhelming;
    }

    public void resetFireballPosition() {
        this.position = Constants.FIREBALL_POSITION;
    }
}

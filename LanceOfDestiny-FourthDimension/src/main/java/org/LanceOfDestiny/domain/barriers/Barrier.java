package org.LanceOfDestiny.domain.barriers;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.events.Event;
import org.LanceOfDestiny.domain.behaviours.GameObject;
import org.LanceOfDestiny.domain.managers.AudioManager;
import org.LanceOfDestiny.domain.managers.BarrierManager;
import org.LanceOfDestiny.domain.physics.ColliderFactory;
import org.LanceOfDestiny.domain.physics.ColliderType;
import org.LanceOfDestiny.domain.physics.Vector;
import org.LanceOfDestiny.domain.sprite.*;

import java.awt.*;
import java.util.Random;

public abstract class Barrier extends GameObject {

    public static final int WIDTH = Constants.BARRIER_WIDTH;
    public static final int HEIGHT = Constants.BARRIER_HEIGHT;
    private final BarrierTypes barrierType;
    protected int direction;
    protected boolean isMoving;
    protected int hitsLeft;
    protected boolean isFalling;
    protected boolean isFrozen;
    protected Sprite defaultSprite;
    protected Sprite frozenSprite;


    public Barrier(Vector position, BarrierTypes type, int hitsRequired) {
        super();
        this.position = position;
        this.barrierType = type;
        this.hitsLeft = hitsRequired;
        this.isFrozen = false;
        createColliderAndSprite();
    }

    public Barrier(Vector position, BarrierTypes type) {
        this(position, type, 1);
    }

    public void createColliderAndSprite() {
        if (this instanceof ExplosiveBarrier) {
            this.collider = ColliderFactory.createBallCollider(this, new Vector(0, 1), ColliderType.STATIC, Constants.EXPLOSIVE_RADIUS);
            this.sprite = new BallSprite(this, Color.RED, (int) Constants.EXPLOSIVE_RADIUS);
            return;
        }
        this.collider = ColliderFactory.createRectangleCollider(this, Vector.getZeroVector(), ColliderType.STATIC, WIDTH, HEIGHT);
        this.sprite = new RectangleSprite(this, Color.DARK_GRAY, WIDTH, HEIGHT);
    }

    public void adjustFrozenSprite() {
        this.frozenSprite = new RectangleSprite(this, Color.DARK_GRAY, WIDTH, HEIGHT);
        this.frozenSprite.setImage(ImageOperations.resizeImageToSprite(ImageLibrary.FrozenBarrierRectangle.getImage(), this.sprite));
    }

    @Override
    public void start() {
        super.start();
        if (isMoving) {
            getCollider().setVelocity(new Vector(direction, 0));
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        BarrierManager.getInstance().removeBarrier(this);
        Event.UpdateBarrierCount.invoke();
        Event.SendBarrierCountUpdate.invoke();
    }

    public void kill() {
        destroy();
        Event.UpdateScore.invoke();
    }

    public void reduceLife() {
        hitsLeft--;
        AudioManager.getInstance().playBarrierHitEffect();
        if (isDestroyed()) {
            kill();
        }
    }

    public void initDirection() {
        if (isMoving) {
            Random rand = new Random(31);
            int directionRand = rand.nextInt(0, 11);
            if (directionRand % 2 == 0) direction = 1;
            if (directionRand % 2 != 0) direction = -1;
        }
    }

    public boolean isDestroyed() {
        return hitsLeft <= 0;
    }

    public BarrierTypes getBarrierType() {
        return barrierType;
    }

    public int getHitsLeft() {
        return hitsLeft;
    }

    public BarrierTypes getType() {
        return barrierType;
    }

    public boolean isFalling() {
        return isFalling;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    public void activateFrozen() {
        isFrozen = true;
        setSprite(frozenSprite);
    }

    public void deactivateFrozen() {
        isFrozen = false;
        setSprite(defaultSprite);
    }

    public boolean isFrozen() {
        return isFrozen;
    }

    @Override
    public String toString() {
        // return a string that represents the object
        return "Barrier Name{" +
                "name=" + barrierType + ", position =" + position +
                '}';
    }

    public String toSerializedString() {
        return barrierType + "," +
                hitsLeft + "," +
                position.getX() + "," + position.getY() + "," +
                isMoving;
    }

}


package org.LanceOfDestiny.domain.barriers;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.events.Events;
import org.LanceOfDestiny.domain.behaviours.GameObject;
import org.LanceOfDestiny.domain.managers.BarrierManager;
import org.LanceOfDestiny.domain.physics.ColliderFactory;
import org.LanceOfDestiny.domain.physics.ColliderType;
import org.LanceOfDestiny.domain.physics.Vector;
import org.LanceOfDestiny.domain.sprite.BallSprite;
import org.LanceOfDestiny.domain.sprite.RectangleSprite;

import java.awt.*;
import java.util.Random;

public abstract class Barrier extends GameObject {

    public static final int WIDTH = Constants.BARRIER_WIDTH;
    public static final int HEIGHT = Constants.BARRIER_HEIGHT;
    private final BarrierTypes barrierType;
    protected int direction;
    protected boolean isMoving;
    protected int hitsLeft;

    public Barrier(Vector position, BarrierTypes type, int hitsRequired) {
        super();
        this.position = position;
        this.barrierType = type;
        this.hitsLeft = hitsRequired;
        createColliderAndSprite();
    }

    public Barrier(Vector position, BarrierTypes type) {
        this(position, type, 1);
    }

    @Override
    public void update() {
        if (isMoving) {
            setPosition(getPosition().add(new Vector(direction, 0)));
        }
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

    @Override
    public void destroy() {
        super.destroy();
        BarrierManager.getInstance().removeBarrier(this);
    }

    public void kill() {
        destroy();
        Events.UpdateScore.invoke();
    }

    public void reduceLife() {
        hitsLeft--;
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

    public int getDirection() {
        return direction;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public int getHitsLeft() {
        return hitsLeft;
    }
}

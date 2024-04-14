package org.LanceOfDestiny.domain.barriers;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.GameObject;
import org.LanceOfDestiny.domain.managers.ManagerHub;
import org.LanceOfDestiny.domain.physics.*;
import org.LanceOfDestiny.ui.BallSprite;
import org.LanceOfDestiny.ui.RectangleSprite;
import org.LanceOfDestiny.ui.Sprite;

import java.awt.*;

public abstract class Barrier extends GameObject {

    public static final int WIDTH = Constants.BARRIER_WIDTH;
    public static final int HEIGHT = Constants.BARRIER_HEIGHT;  // All barriers except explosive barriers are rectangles with dimensions L/5 and 20px.

    private Collider collider;
    protected boolean isMoving;
    protected int hitsLeft;
    private Vector coordinate;
    public BarrierTypes barrierType;

    private Sprite sprite;


    public Barrier(Vector position, BarrierTypes type, int hitsRequired){
        super();
        this.position = position;
        this.barrierType = type;
        this.hitsLeft = hitsRequired;
        createColliderAndSprite();
    }

    public Barrier(Vector position, BarrierTypes type){
        this(position, type, 1);
    }

    public void createColliderAndSprite(){
        if(this instanceof ExplosiveBarrier) {
            this.collider = ColliderFactory.createBallCollider(this, Vector.getZeroVector(), ColliderType.STATIC, Constants.EXPLOSIVE_RADIUS);
            this.sprite = new BallSprite(this, Color.RED, (int) Constants.EXPLOSIVE_RADIUS);
        }
        this.collider = ColliderFactory.createRectangleCollider(this, Vector.getZeroVector(), ColliderType.STATIC, WIDTH, HEIGHT);
        this.sprite = new RectangleSprite(this, Color.DARK_GRAY, WIDTH, HEIGHT);
    }
    @Override
    public void Destroy() {
        super.Destroy();
        PhysicsManager.getInstance().removeCollider(getCollider());
        ManagerHub.getInstance().getBarrierManager().removeBarrier(this);
    }

    public boolean isDestroyed() {
        return hitsLeft <= 0;
    }

    public void ReduceLife() {
        hitsLeft--;
        if (isDestroyed()) {
            kill();
        }
    }

    public void kill() {
        Destroy();
        // method call for adding score should be added after event system I think
    }

    public void setCoordinate(Vector coordinate) {
        this.coordinate = coordinate;
    }

    @Override
    public Sprite getSprite() {
        return sprite;
    }


    public Collider getCollider() {
        return collider;
    }

    public void setCollider(Collider collider) {
        this.collider = collider;
    }

    public Vector getCoordinate() {
        return coordinate;
    }
}

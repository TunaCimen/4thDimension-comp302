package org.LanceOfDestiny.domain.barriers;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.GameObject;
import org.LanceOfDestiny.domain.managers.ManagerHub;
import org.LanceOfDestiny.domain.physics.Collider;

public abstract class Barrier extends GameObject {

    public static final int WIDTH = Constants.L / 5;
    public static final int HEIGHT = Constants.L / 5;

    private Collider collider;
    protected boolean isMoving;
    protected int hitsLeft;

    public int x;
    public int y;

    public BarrierTypes barrierType;


    public Barrier(int x, int y, BarrierTypes type, int hitsRequired){
        super();
        this.x = x;
        this.y = y;
        this.barrierType = type;
        this.hitsLeft = hitsRequired;
    }

    public Barrier(int x, int y, BarrierTypes type){
        super();
        this.x = x;
        this.y = y;
        this.barrierType = type;
        this.hitsLeft = 1;
    }
    @Override
    public void Destroy() {
        super.Destroy();
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



}

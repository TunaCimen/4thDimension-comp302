package org.LanceOfDestiny.domain.player;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.GameObject;
import org.LanceOfDestiny.domain.managers.ManagerHub;
import org.LanceOfDestiny.domain.physics.ColliderFactory;
import org.LanceOfDestiny.domain.physics.Vector;

public class MagicalStaff extends GameObject {

    private FireBall fireBall;
    private int width = Constants.STAFF_WIDTH;
    private final int height = Constants.STAFF_HEIGHT;
    private Vector position;



    private boolean isCanonActivated = false;
    private boolean isExpanded = false;

    public MagicalStaff(Vector position) {
        super();
        this.position = position;
        ColliderFactory.createRectangleCollider(this, )
    }

    public void enableExpansion(){
        // TODO
    }

    public void disableExpansion(){
        // TODO
    }


    public void enableCanons() {
        // TODO
    }

    public void disableCanons() {
        // TODO
    }

    public void setFireBall(FireBall fireBall) {
        this.fireBall = fireBall;
    }
}

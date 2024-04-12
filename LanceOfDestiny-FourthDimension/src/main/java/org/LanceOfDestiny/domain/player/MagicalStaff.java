package org.LanceOfDestiny.domain.player;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.GameObject;
import org.LanceOfDestiny.domain.physics.Vector;
import org.LanceOfDestiny.ui.RectangleSprite;
import org.LanceOfDestiny.ui.Sprite;

import java.awt.*;

public class MagicalStaff extends GameObject {

    private FireBall fireBall;
    private int width = Constants.STAFF_WIDTH;
    private final int height = Constants.STAFF_HEIGHT;
    private Vector position;



    private boolean isCanonActivated = false;
    private boolean isExpanded = false;

    RectangleSprite rectangleSprite;

    public MagicalStaff(Vector position) {
        super();
        this.position = position;
        rectangleSprite = new RectangleSprite(this, Color.DARK_GRAY,Constants.STAFF_WIDTH,Constants.STAFF_HEIGHT);
    }

    @Override
    public Sprite sprite() {
        return rectangleSprite;
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

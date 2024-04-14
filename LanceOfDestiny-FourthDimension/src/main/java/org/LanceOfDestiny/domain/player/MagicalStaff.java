package org.LanceOfDestiny.domain.player;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.EventSystem.Events;
import org.LanceOfDestiny.domain.GameObject;
import org.LanceOfDestiny.domain.Looper.Waiter;
import org.LanceOfDestiny.domain.physics.*;
import org.LanceOfDestiny.ui.RectangleSprite;
import org.LanceOfDestiny.ui.Sprite;

import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;

public class MagicalStaff extends GameObject {

    private FireBall fireBall;
    private int width = Constants.STAFF_WIDTH;
    private final int height = Constants.STAFF_HEIGHT;
    private boolean isCanonActivated = false;
    private boolean isExpanded = false;

    private RectangleCollider collider;
    RectangleSprite rectangleSprite;

    public MagicalStaff(Vector position) {
        super();
        Events.MoveStaff.addListener(this::moveRight);
        Events.RotateStaff.addListener(this::rotate);
        Events.ResetStaff.addRunnableListener(this::resetStaff);
        Events.TimedTestEvent.addListener(this::changeColor);
        Events.ResetColorEvent.addRunnableListener(this::resetColor);
        this.position = position;
        this.rectangleSprite = new RectangleSprite(this, Color.orange,Constants.STAFF_WIDTH,Constants.STAFF_HEIGHT);
        this.collider = ColliderFactory.createRectangleCollider(this, new Vector(0,0), ColliderType.STATIC, Constants.STAFF_WIDTH, Constants.STAFF_HEIGHT);
    }

    private void resetColor() {
        rectangleSprite.color = Color.orange;
    }


    @Override
    public Sprite getSprite() {
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

    public void changeColor(Object color){
        rectangleSprite.color = (Color) color;
    }

    public void moveRight(Object integer) {
        int sign = ((Integer) integer) > 0 ? 1 : -1;
        setPosition(position.add(new Vector(sign*Constants.STAFF_SPEED , 0)));
    }

    public void rotate(Object angle){
        int sign = ((Double) angle) > 0 ? 1 : -1;
        double newAngle = Math.min(Math.max(-0.78,getAngle()
                + Constants.STAFF_ANGULAR_SPEED*sign), 0.78);

        setAngle(newAngle);
    }

    public void hitExplosiveBarrier(){
        Events.UpdateChance.invoke(-1);
    }

    public void resetStaff(){
        setAngle(0);
    }

    public void setFireBall(FireBall fireBall) {
        this.fireBall = fireBall;
    }


}

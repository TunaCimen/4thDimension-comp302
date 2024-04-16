package org.LanceOfDestiny.domain.player;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.events.Events;
import org.LanceOfDestiny.domain.behaviours.GameObject;
import org.LanceOfDestiny.domain.physics.*;
import org.LanceOfDestiny.domain.sprite.RectangleSprite;
import org.LanceOfDestiny.domain.sprite.Sprite;

import java.awt.*;

public class MagicalStaff extends GameObject {

    private FireBall fireBall;
    private int width = Constants.STAFF_WIDTH;
    private final int height = Constants.STAFF_HEIGHT;
    private boolean isCanonActivated = false;
    private boolean isExpanded = false;
    RectangleSprite rectangleSprite;

    public MagicalStaff() {
        super();
        Events.MoveStaff.addListener(this::moveRight);
        Events.RotateStaff.addListener(this::rotate);
        Events.ResetStaff.addRunnableListener(this::resetStaff);
        Events.TimedTestEvent.addListener(this::changeColor);
        Events.ResetColorEvent.addRunnableListener(this::resetColor);
        Events.ActivateCanons.addListener(this::handleCanons);
        Events.ActivateExpansion.addListener(this::handleExpansion);
        this.position = Constants.STAFF_POSITION;
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

    private void handleCanons(Object object) {
        isCanonActivated = (boolean) object;
        if (isCanonActivated) enableCanons();
        else disableCanons();
    }

    private void handleExpansion(Object object) {
        isExpanded = (boolean) object;
        if (isExpanded) enableExpansion();
        else disableExpansion();
    }

}

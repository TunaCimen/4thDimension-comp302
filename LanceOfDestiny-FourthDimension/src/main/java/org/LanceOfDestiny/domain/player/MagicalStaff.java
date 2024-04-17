package org.LanceOfDestiny.domain.player;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.behaviours.GameObject;
import org.LanceOfDestiny.domain.events.Events;
import org.LanceOfDestiny.domain.physics.ColliderFactory;
import org.LanceOfDestiny.domain.physics.ColliderType;
import org.LanceOfDestiny.domain.physics.RectangleCollider;
import org.LanceOfDestiny.domain.physics.Vector;
import org.LanceOfDestiny.domain.sprite.RectangleSprite;
import org.LanceOfDestiny.domain.sprite.Sprite;

import java.awt.*;

public class MagicalStaff extends GameObject {

    private final int height = Constants.STAFF_HEIGHT;
    private FireBall fireBall;
    private int width = Constants.STAFF_WIDTH;
    private boolean isCanonActivated = false;
    protected boolean isExpanded = false;
    private RectangleSprite defaultSprite;
    private RectangleSprite expandedSprite;
    private RectangleCollider expandedCollider;
    private RectangleCollider defaultCollider;

    public MagicalStaff() {
        super();
        this.position = Constants.STAFF_POSITION;
        initializeCollidersAndSprites();

        Events.MoveStaff.addListener(this::moveRight);
        Events.RotateStaff.addListener(this::rotate);
        Events.ResetStaff.addRunnableListener(this::resetStaff);
        Events.TimedTestEvent.addListener(this::changeColor);
        Events.ResetColorEvent.addRunnableListener(this::resetColor);
        Events.ActivateCanons.addListener(this::handleCanons);
        Events.ActivateExpansion.addListener(this::handleExpansion);
    }

    public void initializeCollidersAndSprites(){
        this.defaultSprite = new RectangleSprite(this, Color.orange, width, height);
        this.sprite = defaultSprite;
        this.defaultCollider = ColliderFactory.createRectangleCollider(this, new Vector(0, 0), ColliderType.STATIC, width, height);
        this.collider = defaultCollider;

        this.expandedSprite = new RectangleSprite(this, Color.red, width * 2, height);
        this.expandedCollider = ColliderFactory.createRectangleCollider(this, new Vector(0, 0), ColliderType.STATIC, width * 2, height);
        expandedCollider.setEnabled(false);
    }

    private void resetColor() {
        sprite.color = Color.orange;
    }


    public void setSprite(Sprite sprite) {
        this.sprite = (RectangleSprite) sprite;
    }

    public void enableExpansion() {
        isExpanded = true;

        setSprite(expandedSprite);
        setCollider(expandedCollider);

        defaultCollider.setEnabled(false);
        expandedCollider.setEnabled(true);
    }

    public void disableExpansion() {
        isExpanded = false;

        setSprite(defaultSprite);
        setCollider(defaultCollider);

        expandedCollider.setEnabled(false);
        defaultCollider.setEnabled(true);
    }


    public void enableCanons() {
        // TODO
    }

    public void disableCanons() {
        // TODO
    }

    public void changeColor(Object color) {
        defaultSprite.color = (Color) color;
    }

    public void moveRight(Object integer) {
        int sign = ((Integer) integer) > 0 ? 1 : -1;
        setPosition(position.add(new Vector(sign * Constants.STAFF_SPEED, 0)));
    }

    public void rotate(Object angle) {
        int sign = ((Double) angle) > 0 ? 1 : -1;
        double newAngle = Math.min(Math.max(-0.78, getAngle()
                + Constants.STAFF_ANGULAR_SPEED * sign), 0.78);

        setAngle(newAngle);
    }

    public void hitExplosiveBarrier() {
        Events.UpdateChance.invoke(-1);
    }

    public void resetStaff() {
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

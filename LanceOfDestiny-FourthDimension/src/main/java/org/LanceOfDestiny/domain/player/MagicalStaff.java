package org.LanceOfDestiny.domain.player;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.behaviours.GameObject;
import org.LanceOfDestiny.domain.events.Events;
import org.LanceOfDestiny.domain.physics.ColliderFactory;
import org.LanceOfDestiny.domain.physics.ColliderType;
import org.LanceOfDestiny.domain.physics.RectangleCollider;
import org.LanceOfDestiny.domain.physics.Vector;
import org.LanceOfDestiny.domain.spells.Canon;
import org.LanceOfDestiny.domain.sprite.ImageLibrary;
import org.LanceOfDestiny.domain.sprite.ImageOperations;
import org.LanceOfDestiny.domain.sprite.RectangleSprite;

import java.awt.*;

public class MagicalStaff extends GameObject {

    private final int HEIGHT = Constants.STAFF_HEIGHT;
    private final int WIDTH = Constants.STAFF_WIDTH;
    private final Canon canonLeft;
    private final Canon canonRight;
    protected boolean isExpanded = false;
    private RectangleSprite defaultSprite;
    private RectangleSprite expandedSprite;
    private RectangleCollider expandedCollider;
    private RectangleCollider defaultCollider;

    public MagicalStaff() {
        super();
        this.position = Constants.STAFF_POSITION;
        initializeCollidersAndSprites();

        this.canonLeft = new Canon(this.position.add(new Vector(0, -HEIGHT)), true);
        this.canonRight = new Canon(this.position.add(new Vector(WIDTH, -HEIGHT)), false);
        ((RectangleSprite)canonLeft.getSprite()).parentObject = this;
        ((RectangleSprite)canonRight.getSprite()).parentObject = this;
        Events.MoveStaff.addListener(this::moveRight);
        Events.RotateStaff.addListener(this::rotate);
        Events.ResetStaff.addRunnableListener(this::resetStaff);
        Events.ActivateCanons.addListener(this::handleCanons);
        Events.ActivateExpansion.addListener(this::handleExpansion);
    }

    public void initializeCollidersAndSprites() {
        this.defaultSprite = new RectangleSprite(this, new Color(0,0,0,0), WIDTH, HEIGHT);
        this.sprite = defaultSprite;
        this.sprite.setImage(ImageOperations.resizeImageToSprite(ImageLibrary.MagicalStaff.getImage(),this.sprite));
        this.defaultCollider = ColliderFactory.createRectangleCollider(this, new Vector(0, 0), ColliderType.STATIC, WIDTH, HEIGHT);
        this.collider = defaultCollider;
        this.expandedSprite = new RectangleSprite(this, new Color(0,0,0,0), WIDTH * 2, HEIGHT);
        this.expandedSprite.setImage(ImageOperations.resizeImageToSprite(ImageLibrary.MagicalStaff.getImage(), expandedSprite));
        this.expandedCollider = ColliderFactory.createRectangleCollider(this, new Vector(0, 0), ColliderType.STATIC, WIDTH * 2, HEIGHT);
        expandedCollider.setEnabled(false);
    }

    @Override
    public void setPosition(Vector position) {


        //var x = (position.getX() <= (double) Constants.SCREEN_WIDTH / 2) ? Math.max(minX, position.getX()) : Math.min(position.getX(), maxX);
        this.position = position;

    }

    public void moveRight(Object integer) {
        int sign = ((Integer) integer) > 0 ? 1 : -1;
        var minX = 0;
        var staffWidth = (isExpanded ? WIDTH * 2 : WIDTH);
        var maxX = Constants.SCREEN_WIDTH - staffWidth;

        var x = (position.getX() <= (double) Constants.SCREEN_WIDTH / 2) ? Math.max(minX, position.getX()) : Math.min(position.getX(), maxX);
        if(x == minX && sign==-1)return;
        if(x == maxX && sign==1)return;
        setPosition(position.add(new Vector(sign * Constants.STAFF_SPEED, 0)));
        canonLeft.setPosition(canonLeft.getPosition().add(new Vector(sign * Constants.STAFF_SPEED, 0)));
        canonRight.setPosition(canonRight.getPosition().add(new Vector(sign * Constants.STAFF_SPEED, 0)));
    }

    public void rotate(Object angle) {
        int sign = ((Double) angle) > 0 ? 1 : -1;
        double newAngle = Math.min(Math.max(-0.78, getAngle() + Constants.STAFF_ANGULAR_SPEED * sign), 0.78);
        setAngle(newAngle);
        canonLeft.setAngle(newAngle);
        canonRight.setAngle(newAngle);
    }

    public void resetStaff() {
        setAngle(0);
        canonLeft.setAngle(0);
        canonRight.setAngle(0);
    }

    private void handleExpansion(Object object) {
        isExpanded = (boolean) object;
        if (isExpanded) enableExpansion();
        else disableExpansion();
    }

    public void enableExpansion() {
        setSprite(expandedSprite);
        setCollider(expandedCollider);
        defaultCollider.setEnabled(false);
        expandedCollider.setEnabled(true);
    }

    public void disableExpansion() {
        setSprite(defaultSprite);
        setCollider(defaultCollider);
        expandedCollider.setEnabled(false);
        defaultCollider.setEnabled(true);
    }

    private void handleCanons(Object object) {
        boolean isCanonActivated = (boolean) object;
        if (isCanonActivated) enableCanons();
        else disableCanons();
    }

    public void enableCanons() {
        canonLeft.activateCanon();
        canonRight.activateCanon();
    }

    public void disableCanons() {
        canonLeft.deactivateCanon();
        canonRight.deactivateCanon();
    }
}

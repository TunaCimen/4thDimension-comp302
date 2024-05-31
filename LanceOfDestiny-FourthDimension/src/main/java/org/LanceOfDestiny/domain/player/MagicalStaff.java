package org.LanceOfDestiny.domain.player;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.behaviours.GameObject;
import org.LanceOfDestiny.domain.events.Event;
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
    protected boolean isCanonEnabled = false;
    protected boolean isCanonRightShifted = false;
    private RectangleSprite defaultSprite;
    private RectangleSprite expandedSprite;
    private RectangleCollider expandedCollider;
    private RectangleCollider defaultCollider;

    public MagicalStaff() {
        super();
        this.position = Constants.STAFF_POSITION;
        initializeCollidersAndSprites();

        this.canonLeft = new Canon(this.position.add(new Vector(0, -HEIGHT)), true);
        this.canonRight = new Canon(this.position.add(new Vector(WIDTH-Constants.CANON_WIDTH, -HEIGHT)), false);
        ((RectangleSprite)canonLeft.getSprite()).parentObject = this;
        ((RectangleSprite)canonRight.getSprite()).parentObject = this;
        Event.MoveStaff.addListener(this::moveRight);
        Event.RotateStaff.addListener(this::rotate);
        Event.ResetStaff.addRunnableListener(this::resetStaffAngle);
        Event.ActivateCanons.addListener(this::handleCanons);
        Event.ActivateExpansion.addListener(this::handleExpansion);
        Event.Reset.addRunnableListener(this::resetStaff);
        Event.LoadGame.addRunnableListener(this::resetStaff);
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
    /**
     * Moves the magical staff horizontally within the game boundaries.
     * @param integer an Object that should be an Integer representing the direction and magnitude of movement. <p>
     *                A positive value moves the staff to the right, and a negative value moves it to the left. <p>
     * Requires: <p>
     *                - 'integer' must be an Integer instance <p>
     *                - 'position' of the staff must be initialized within the game boundaries. <p>
     *                - 'Constants.SCREEN_WIDTH', 'Constants.STAFF_SPEED', 'WIDTH', and, 'HEIGHT' must be defined beforehand. <p>
     * Modifies: <p>
     *                - 'position' of the MagicalStaff <p>
     *                - 'position' of the 'canonRight' and 'canonLeft' <p>
     * Effects: <p>
     *                - Moves the staff to the right if `integer` is positive, and to the left if `integer` is negative. <p>
     *                - Ensures the staff does not move outside the horizontal boundaries of the game screen. <p>
     *                - If the staff is expanded, it adjusts the maximum boundary accordingly. <p>
     *                - Updates the positions of `canonLeft` and `canonRight` according to staff's new position. <p>
     */
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

    public void resetStaffAngle() {
        setAngle(0);
        canonLeft.setAngle(0);
        canonRight.setAngle(0);
    }

    public void resetStaff() {
        resetStaffAngle();
        resetStaffPosition();
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
        if(isCanonEnabled && !isCanonRightShifted) {
            canonRight.setPosition(canonRight.getPosition().add(new Vector(Constants.STAFF_WIDTH,0)));
            isCanonRightShifted = true;
        }
    }

    public void disableExpansion() {
        setSprite(defaultSprite);
        setCollider(defaultCollider);
        expandedCollider.setEnabled(false);
        defaultCollider.setEnabled(true);
        if(isCanonEnabled && isCanonRightShifted) {
            canonRight.setPosition(canonRight.getPosition().subtract(new Vector(Constants.STAFF_WIDTH, 0)));
            isCanonRightShifted = false;
        }
    }

    private void handleCanons(Object object) {
        boolean isCanonActivated = (boolean) object;
        if (isCanonActivated) enableCanons();
        else disableCanons();
    }

    public void enableCanons() {
        isCanonEnabled = true;
        canonLeft.activateCanon();
        canonRight.activateCanon();
        if(isExpanded && !isCanonRightShifted) {
            canonRight.setPosition(canonRight.getPosition().add(new Vector(Constants.STAFF_WIDTH, 0)));
            isCanonRightShifted = true;
        }
    }

    public void disableCanons() {
        isCanonEnabled = false;
        canonLeft.deactivateCanon();
        canonRight.deactivateCanon();
        if(isExpanded && isCanonRightShifted) {
            canonRight.setPosition(canonRight.getPosition().subtract(new Vector(Constants.STAFF_WIDTH, 0)));
            isCanonRightShifted = false;
        }
    }

    public void resetStaffPosition() {
        setPosition(Constants.STAFF_POSITION);
        canonLeft.setPosition(this.position.add(new Vector(0, -HEIGHT)));
        canonRight.setPosition(this.position.add(new Vector(WIDTH-Constants.CANON_WIDTH, -HEIGHT)));
    }

    public Canon getCanonLeft() {
        return canonLeft;
    }

    public Canon getCanonRight() {
        return canonRight;
    }
}

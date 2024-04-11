package org.LanceOfDestiny.domain.player;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.GameObject;
import org.LanceOfDestiny.ui.BallSprite;

import javax.swing.*;

import java.awt.*;

public class FireBall extends GameObject {
    private MagicalStaff magicalStaff;

    private boolean isAttached = true;
    private boolean isOverwhelming = false;

    private double defaultSpeed;
    private double currentSpeed;

    private double x;
    private double y;
    private final int radius = Constants.FIREBALL_RADIUS;
    BallSprite bs;

    public FireBall(MagicalStaff magicalStaff) {
        super();
        this.magicalStaff = magicalStaff;
        this.isAttached = true;
        currentSpeed = defaultSpeed;
        bs = new BallSprite(40,40,40, Color.red);

    }

    @Override
    public JPanel sprite() {
        return bs;
    }

    @Override
    public void Awake() {
        super.Awake();
    }

    @Override
    public void Update() {
        super.Update();
    }

    public void enableOverwhelming() {
        isOverwhelming = true;
    }

    public void disableOverwhelming() {
        isOverwhelming = false;
    }

    public boolean isAttached() {
        return isAttached;
    }

    public void reattach() {
        isAttached = true;
    }

    public double getDefaultSpeed() {
        return defaultSpeed;
    }

    public void setDefaultSpeed(double defaultSpeed) {
        this.defaultSpeed = defaultSpeed;
    }

    public double getCurrentSpeed() {
        return currentSpeed;
    }

    public void setCurrentSpeed(double currentSpeed) {
        this.currentSpeed = currentSpeed;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

}

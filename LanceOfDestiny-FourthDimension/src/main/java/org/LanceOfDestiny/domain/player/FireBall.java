package org.LanceOfDestiny.domain.player;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.EventSystem.Events;
import org.LanceOfDestiny.domain.GameObject;
import org.LanceOfDestiny.domain.physics.BallCollider;
import org.LanceOfDestiny.domain.physics.Collider;
import org.LanceOfDestiny.domain.physics.ColliderType;
import org.LanceOfDestiny.domain.physics.Vector;
import org.LanceOfDestiny.ui.BallSprite;

import javax.swing.*;

import java.awt.*;

public class FireBall extends GameObject {
    private Collider collider;
    private boolean isOverwhelming = false;
    private double defaultSpeed;
    private double currentSpeed;
    private final int radius = Constants.FIREBALL_RADIUS;
    BallSprite bs;
    public FireBall(Vector position) {
        super();
        this.position = position;
        this.currentSpeed = defaultSpeed;
        this.collider = new BallCollider(new Vector(0, 0), ColliderType.DYNAMIC, radius, this);
        this.bs = new BallSprite(40,40,40, Color.red);
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
        System.out.println("Fireball Updated");
        bs.x+=5;
    }

    public void enableOverwhelming() {
        isOverwhelming = true;
    }

    public void disableOverwhelming() {
        isOverwhelming = false;
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

}

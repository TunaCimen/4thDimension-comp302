package org.LanceOfDestiny.domain.player;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.EventSystem.Events;
import org.LanceOfDestiny.domain.GameObject;
import org.LanceOfDestiny.domain.physics.*;
import org.LanceOfDestiny.ui.BallSprite;
import org.LanceOfDestiny.ui.Sprite;

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

        this.collider = ColliderFactory.createBallCollider(this,new Vector(0,0), ColliderType.DYNAMIC, radius);
        //this.bs = new BallSprite(this,Constants.FIREBALL_RADIUS, Color.red);
        //this.collider = ColliderFactory.createBallCollider(this,new Vector(5,-10),ColliderType.DYNAMIC,radius);
       // this.collider = new BallCollider(new Vector(5, 0), ColliderType.DYNAMIC, radius, this);
        this.bs = new BallSprite(this, Color.red,Constants.FIREBALL_RADIUS);
        Events.ActivateOverwhelming.addListener(this::handleOverwhelming);
    }

    private void handleOverwhelming(Object object) {
        isOverwhelming = (Boolean) object;
    }

    public FireBall(Vector position, Vector velocity) {
        super();
        this.position = position;
        this.currentSpeed = defaultSpeed;
        this.collider = ColliderFactory.createBallCollider(this,velocity, ColliderType.DYNAMIC, radius);
        this.bs = new BallSprite(this, Color.black,Constants.FIREBALL_RADIUS);
        Events.ActivateOverwhelming.addListener(this::handleOverwhelming);
    }
    public FireBall(Vector position, ColliderType colliderType) {
        super();
        this.position = position;
        this.currentSpeed = defaultSpeed;
        this.collider = ColliderFactory.createBallCollider(this,new Vector(0,0),colliderType, radius);
        this.bs = new BallSprite(this, Color.cyan,Constants.FIREBALL_RADIUS);
        Events.ActivateOverwhelming.addListener(this::handleOverwhelming);
    }


    @Override
    public Sprite getSprite() {
        return bs;
    }

    @Override
    public void awake() {
        super.awake();
    }

    @Override
    public void update() {
        setPosition(getPosition().add(collider.getVelocity()));
        if(getPosition().getY() >= Constants.SCREEN_HEIGHT) fireBallDropped();
    }

    public void fireBallDropped(){
        Events.UpdateChance.invoke(-1);
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

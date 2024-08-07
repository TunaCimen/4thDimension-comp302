package org.LanceOfDestiny.domain.physics;

import org.LanceOfDestiny.domain.behaviours.GameObject;

public abstract class Collider {

    protected final GameObject gameObject; // the gameObject that it is attached to
    protected Vector velocity;
    protected ColliderType colliderType;
    private boolean isEnabled;
    private boolean isTrigger;

    private boolean isMovable;

    protected Collider(Vector velocity, ColliderType colliderType, GameObject gameObject) {
        this.velocity = velocity;
        this.colliderType = colliderType;
        this.gameObject = gameObject;
        this.isEnabled = true;
        this.isTrigger = false;
        isMovable = true;
    }

    protected Collider(Vector velocity, ColliderType colliderType, GameObject gameObject, boolean isEnabled) {
        this(velocity, colliderType, gameObject);
        this.isEnabled = isEnabled;
    }

    public GameObject getGameObject() {
        return gameObject;
    }

    public Vector getPosition() {
        return gameObject.getPosition();
    }

    public void setPosition(Vector position) {
        gameObject.setPosition(position);
    }

    public Vector getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector velocity) {
        this.velocity = velocity;
    }

    public Vector getPosition(double framesAhead) {
        return gameObject.getPosition().add(getVelocity().scale(framesAhead));
    }

    public ColliderType getColliderType() {
        return colliderType;
    }

    public void setColliderType(ColliderType colliderType) {
        this.colliderType = colliderType;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public boolean isTrigger() {
        return isTrigger;
    }

    public void setTrigger(boolean trigger) {
        isTrigger = trigger;
    }

    public void setMovable(boolean isMovable){
        this.isMovable = isMovable;
    }
    public boolean isMovable(){
        return isMovable;
    }





}

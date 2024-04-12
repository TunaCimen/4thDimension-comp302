package org.LanceOfDestiny.domain.physics;

import org.LanceOfDestiny.domain.EventSystem.Events;
import org.LanceOfDestiny.domain.GameObject;

public abstract class Collider {

    protected Vector velocity;
    protected ColliderType colliderType;
    private final GameObject gameObject; // the gameObject that it is attached to

    protected Collider(Vector velocity, ColliderType colliderType, GameObject gameObject) {
        this.velocity = velocity;
        this.colliderType = colliderType;
        this.gameObject = gameObject;
        Events.CollisionEvent.addListener(gameObject::OnCollisionEnter);
    }

    // Getters and Setters
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

    public ColliderType getColliderType() {
        return colliderType;
    }

    public void setColliderType(ColliderType colliderType) {
        this.colliderType = colliderType;
    }
}

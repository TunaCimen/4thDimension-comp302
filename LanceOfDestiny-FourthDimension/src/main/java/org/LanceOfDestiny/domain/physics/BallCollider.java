package org.LanceOfDestiny.domain.physics;

import org.LanceOfDestiny.domain.GameObject;

public class BallCollider extends Collider {
    private float radius;

    public BallCollider(Vector velocity, ColliderType colliderType, float radius, GameObject gameObject) {
        super(velocity, colliderType, gameObject);
        this.radius = radius;
    }

    // Additional functionality specific to BallCollider can go here

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }
}

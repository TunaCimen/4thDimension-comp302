package org.LanceOfDestiny.domain.physics;

public class BallCollider extends Collider {
    private float radius;

    public BallCollider(Vector position, Vector velocity, ColliderType colliderType, float radius) {
        super(position, velocity, colliderType);
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

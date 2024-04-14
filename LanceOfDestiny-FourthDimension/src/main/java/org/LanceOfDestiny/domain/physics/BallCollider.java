package org.LanceOfDestiny.domain.physics;

import org.LanceOfDestiny.domain.GameObject;

public class BallCollider extends Collider {
    private double radius;

    public BallCollider(Vector velocity, ColliderType colliderType, double radius, GameObject gameObject) {
        super(velocity, colliderType, gameObject);
        this.radius = radius;
    }

    // Additional functionality specific to BallCollider can go here

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }
}

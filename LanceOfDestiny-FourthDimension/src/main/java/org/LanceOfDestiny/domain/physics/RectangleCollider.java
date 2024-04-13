package org.LanceOfDestiny.domain.physics;

import org.LanceOfDestiny.domain.GameObject;

public class RectangleCollider extends Collider {
    private double width;
    private double height;
    public RectangleCollider(GameObject gameObject, Vector velocity, ColliderType colliderType, double width, double height) {
        super(velocity, colliderType,gameObject);
        this.width = width;
        this.height = height;
    }

    // Additional functionality specific to RectangleCollider can go here

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getAngle() {
        return gameObject.getAngle();
    }

    public void setAngle(double angle) {
        gameObject.setAngle(angle);
    }
}

package org.LanceOfDestiny.domain.physics;

import org.LanceOfDestiny.domain.GameObject;

public class RectangleCollider extends Collider {
    private float width;
    private float height;
    private float rotation;
    public RectangleCollider(GameObject gameObject, Vector velocity, ColliderType colliderType, float width, float height) {
        super(velocity, colliderType,gameObject);
        this.width = width;
        this.height = height;
    }

    // Additional functionality specific to RectangleCollider can go here

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }
}

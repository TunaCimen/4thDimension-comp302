package org.LanceOfDestiny.domain.physics;

public class Collider {
    private ColliderShape colliderShape;
    private Vector position;
    private Vector velocity;
    private ColliderType colliderType;

    private float width;
    private float height;

    private float rotation;
    private float radius;

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

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public Collider(ColliderType colliderType, Vector position, Vector velocity) {
        this.colliderShape = ColliderShape.BALL;
        this.position = position;
        this.velocity = velocity;
        this.colliderType = colliderType;
    }

    public Collider(ColliderType colliderType, Vector position, Vector velocity, float height, float width, float rotation) {
        this.colliderShape = ColliderShape.RECTANGLE;
        this.position = position;
        this.velocity = velocity;
        this.height = height;
        this.width = width;
        this.colliderType = colliderType;
    }
    // Method to update the collider's position based on its velocity
    public void updatePosition(float deltaTime) {
        // Assuming deltaTime is the time passed since the last update, in seconds
        // and that velocity is in units per second
        this.position.setX(this.position.getX() + this.velocity.getX() * deltaTime);
        this.position.setY(this.position.getY() + this.velocity.getY() * deltaTime);
    }

    // Getters and Setters
    public ColliderShape getColliderShape() {
        return colliderShape;
    }

    public void setColliderShape(ColliderShape colliderShape) {
        this.colliderShape = colliderShape;
    }

    public Vector getPosition() {
        return position;
    }

    public void setPosition(Vector position) {
        this.position = position;
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

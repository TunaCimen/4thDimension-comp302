package org.LanceOfDestiny.domain.physics;

public abstract class Collider {
    protected Vector position;
    protected Vector velocity;
    protected ColliderType colliderType;

    protected Collider(Vector position, Vector velocity, ColliderType colliderType) {
        this.position = position;
        this.velocity = velocity;
        this.colliderType = colliderType;
    }

    public void updatePosition(float deltaTime) {
        this.position.setX(this.position.getX() + this.velocity.getX() * deltaTime);
        this.position.setY(this.position.getY() + this.velocity.getY() * deltaTime);
    }

    // Getters and Setters
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

package org.LanceOfDestiny.domain.physics;

public class Collision {
    private Collider collider1;
    private Collider collider2;
    private Vector collisionPoint; // The exact point of collision, may be null or need calculation based on the game's physics

    // Constructor
    public Collision(Collider collider1, Collider collider2, Vector collisionPoint) {
        this.collider1 = collider1;
        this.collider2 = collider2;
        this.collisionPoint = collisionPoint;
    }

    // Getters
    public Collider getCollider1() {
        return collider1;
    }

    public Collider getCollider2() {
        return collider2;
    }

    public Vector getCollisionPoint() {
        return collisionPoint;
    }

    // You may want to add setters or other methods depending on how you decide to resolve collisions or update game state post-collision
}

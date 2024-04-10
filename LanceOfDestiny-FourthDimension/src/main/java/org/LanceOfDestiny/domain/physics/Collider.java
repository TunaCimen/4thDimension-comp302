package org.LanceOfDestiny.domain.physics;

public class Collider {
    private ColliderShape colliderShape;
    private Vector position;
    private Vector velocity;

    private ColliderType colliderType;
    // Static (nothing changes its pos. or state (non-moving barriers)),
    // Dynamic (its pos. and speed can be changed (i.e. fireball)),
    // or Kinematic (its pos. is dependent on its own speed only (moving barriers))

    public Collider(ColliderShape colliderShape, ColliderType colliderType, Vector position, Vector velocity) {
        this.colliderShape = colliderShape;
        this.position = position;
        this.velocity = velocity;
    }






}

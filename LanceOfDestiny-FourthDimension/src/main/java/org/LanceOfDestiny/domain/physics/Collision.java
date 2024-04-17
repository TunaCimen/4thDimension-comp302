package org.LanceOfDestiny.domain.physics;

import org.LanceOfDestiny.domain.behaviours.GameObject;

public class Collision {
    private Collider collider1;
    private Collider collider2; // Note: collider2 can be null for boundary collisions
    private Vector normal; // Normal vector at the point of collision

    // Constructor
    public Collision(Collider collider1, Collider collider2, Vector normal) {
        this.collider1 = collider1;
        this.collider2 = collider2;
        this.normal = normal;
    }

    // Getters
    public Collider getCollider1() {
        return collider1;
    }

    public Collider getCollider2() {
        return collider2;
    }

    public Vector getNormal() {
        return normal;
    }

    /**
     * Returns the other GameObject involved in the collision, or null if not applicable.
     * @param self The GameObject calling this method.
     * @return The other GameObject involved in the collision, or null if there is no other or the input is invalid.
     */
    public GameObject getOther(GameObject self) {
        if (self == null) {
            return null; // Early return if the calling object itself is null
        }

        if (collider1 != null && collider1.getGameObject() != null) {
            if (collider1.getGameObject().equals(self)) {
                return (collider2 != null) ? collider2.getGameObject() : null; // Return collider2's GameObject if it exists
            }
        }

        if (collider2 != null && collider2.getGameObject() != null) {
            if (collider2.getGameObject().equals(self)) {
                return collider1.getGameObject(); // Safe to return collider1's GameObject as it must be non-null if used correctly
            }
        }

        return null; // Return null if no match was found or if both colliders are null
    }
}

package org.LanceOfDestiny.domain.physics;

import org.LanceOfDestiny.domain.GameObject;

public class Collision {
    private Collider collider1;
    private Collider collider2;
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

    public GameObject getOther(GameObject self) {
        if (collider1.getGameObject().equals(self)) {
            return collider2.getGameObject();
        } else if (collider2.getGameObject().equals(self)) {
            return collider1.getGameObject();
        }
        return null;
    }
}

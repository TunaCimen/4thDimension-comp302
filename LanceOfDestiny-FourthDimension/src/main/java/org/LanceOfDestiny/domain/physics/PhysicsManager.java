package org.LanceOfDestiny.domain.physics;

import java.util.List;

public class PhysicsManager {
    private static PhysicsManager instance;
    private List<Collider> colliders;

    private PhysicsManager() {
        // Private constructor to prevent instantiation
    }

    public static PhysicsManager getInstance() {
        if (instance == null) {
            instance = new PhysicsManager();
        }
        return instance;
    }

    public void checkCollisions() {
        // Here, every single two collider comb. will be checked to see if they will collide on the next loop
        // If they do collide, a Collision object is created
    }

    // This will be the place with the crazy math UwU
    // Maybe this can also create the collision if it exists idk (it might not abide by the observer mutator principle)
    private void checkCollision(Collider collider1, Collider collider2) {

    }

}

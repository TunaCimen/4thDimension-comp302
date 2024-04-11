package org.LanceOfDestiny.domain.physics;

import org.LanceOfDestiny.domain.GameObject;

public class ColliderFactory {

    public static BallCollider createBallCollider(GameObject gameObject, Vector velocity, ColliderType colliderType, float radius) {
        BallCollider ballCollider = new BallCollider( velocity, colliderType, radius,gameObject);
        PhysicsManager.getInstance().addCollider(ballCollider);
        return ballCollider;
    }

    public static RectangleCollider createRectangleCollider(GameObject gameObject, Vector velocity, ColliderType colliderType, float width, float height) {
        RectangleCollider rectangleCollider = new RectangleCollider(gameObject, velocity, colliderType, width, height);
        PhysicsManager.getInstance().addCollider(rectangleCollider);
        return rectangleCollider;
    }
}

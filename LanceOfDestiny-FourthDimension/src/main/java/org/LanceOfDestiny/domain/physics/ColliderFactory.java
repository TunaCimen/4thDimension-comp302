package org.LanceOfDestiny.domain.physics;

public class ColliderFactory {

    public static BallCollider createBallCollider(Vector position, Vector velocity, ColliderType colliderType, float radius) {
        BallCollider ballCollider = new BallCollider(position, velocity, colliderType, radius);
        PhysicsManager.getInstance().addCollider(ballCollider);
        return ballCollider;
    }

    public static RectangleCollider createRectangleCollider(Vector position, Vector velocity, ColliderType colliderType, float width, float height) {
        RectangleCollider rectangleCollider = new RectangleCollider(position, velocity, colliderType, width, height);
        PhysicsManager.getInstance().addCollider(rectangleCollider);
        return rectangleCollider;
    }
}

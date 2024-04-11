package org.LanceOfDestiny.domain.physics;

import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;

public class PhysicsManager {
    private static PhysicsManager instance;
    private List<Collider> colliders;

    private PhysicsManager() {
        colliders = new ArrayList<>();
    }

    public static PhysicsManager getInstance() {
        if (instance == null) {
            instance = new PhysicsManager();
        }
        return instance;
    }

    public void addCollider(Collider collider) {
        colliders.add(collider);
    }

    public void removeCollider(Collider collider) {
        colliders.remove(collider);
    }

    public Collision checkCollision(Collider collider1, Collider collider2) {
        // Check for ball-rectangle collision; if not, return null
        if (!isBallRectCollision(collider1, collider2)) {
            return null; // Return null if it's not a ball-rectangle collision
        }

        Collider ball = (collider1.getColliderShape() == ColliderShape.BALL) ? collider1 : collider2;
        Collider rectangle = (collider1.getColliderShape() == ColliderShape.BALL) ? collider2 : collider1;

        Vector ballCenter = ball.getPosition();
        float ballRadius = ball.getRadius();

        // Calculate the closest point on the rectangle to the center of the ball
        float closestX = Math.max(rectangle.getPosition().getX(), Math.min(ballCenter.getX(), rectangle.getPosition().getX() + rectangle.getWidth()));
        float closestY = Math.max(rectangle.getPosition().getY(), Math.min(ballCenter.getY(), rectangle.getPosition().getY() + rectangle.getHeight()));

        // Calculate the distance between the ball's center and this closest point
        float distanceX = ballCenter.getX() - closestX;
        float distanceY = ballCenter.getY() - closestY;

        // If the distance is less than the ball's radius, we have a collision
        if (areColliding(distanceX, distanceY, ballRadius)) {
            // Collision detected, generate Collision object with the closest point as the collision point
            Vector collisionPoint = new Vector(closestX, closestY);
            return new Collision(ball, rectangle, collisionPoint);
        }

        return null; // No collision detected
    }

    private static boolean areColliding(float distanceX, float distanceY, float ballRadius) {
        return Math.sqrt((distanceX * distanceX) + (distanceY * distanceY)) < ballRadius;
    }

    private static boolean isBallRectCollision(Collider collider1, Collider collider2) {
        return (collider1.getColliderShape() == ColliderShape.BALL && collider2.getColliderShape() == ColliderShape.RECTANGLE) ||
                (collider2.getColliderShape() == ColliderShape.BALL && collider1.getColliderShape() == ColliderShape.RECTANGLE);
    }

    public List<Collision> checkCollisions() {
        List<Collision> detectedCollisions = new LinkedList<>();
        for (int i = 0; i < colliders.size(); i++) {
            for (int j = i + 1; j < colliders.size(); j++) {
                Collision collision = checkCollision(colliders.get(i), colliders.get(j));
                if (collision != null) {
                    detectedCollisions.add(collision);
                }
            }
        }
        return detectedCollisions;
    }

    // Placeholder for velocity change method
    public void updateVelocitiesAfterCollision(Collision collision) {
        // Implement based on your physics model
    }

    // Other methods...
}

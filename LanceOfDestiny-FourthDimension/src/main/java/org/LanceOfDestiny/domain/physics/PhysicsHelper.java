package org.LanceOfDestiny.domain.physics;

import org.LanceOfDestiny.domain.barriers.Barrier;
import org.LanceOfDestiny.domain.player.FireBall;

import java.util.List;

public class PhysicsHelper {

    static Vector getReflection(Collision collision, Collider collider) {
        Vector incoming = collider.getVelocity();
        Vector normal = collision.getNormal();
        Vector reflection = incoming.subtract(normal.scale(2).scale(incoming.dotProduct(normal)));
        return reflection;
    }

    static boolean isBallRectCollision(Collider collider1, Collider collider2) {
        return (collider1 instanceof RectangleCollider && collider2 instanceof BallCollider) || (collider1 instanceof BallCollider && collider2 instanceof RectangleCollider);
    }

    static boolean isBallBallCollision(Collider collider1, Collider collider2) {
        return collider1 instanceof BallCollider && collider2 instanceof BallCollider;
    }

    static boolean isOverwhelmingFireballBarrierCollision(Collision collision) {
        return ((collision.getCollider1().getGameObject() instanceof FireBall && ((FireBall) collision.getCollider1().getGameObject()).isOverwhelming()) &&
                (collision.getCollider2() != null && collision.getCollider2().getGameObject() instanceof Barrier)) ||
                ((collision.getCollider2() != null && collision.getCollider2().getGameObject() instanceof FireBall && ((FireBall) collision.getCollider2().getGameObject()).isOverwhelming()) &&
                        (collision.getCollider1().getGameObject() instanceof Barrier));
    }

    static double[] projectRectangle(Vector[] corners, Vector axis) {
        double min = axis.dotProduct(corners[0]);
        double max = min;
        for (int i = 1; i < corners.length; i++) {
            double projection = axis.dotProduct(corners[i]);
            if (projection < min) min = projection;
            if (projection > max) max = projection;
        }
        return new double[]{min, max};
    }

    static Vector getRectRectCollisionNormal(int framesAhead, RectangleCollider rect1, RectangleCollider rect2) {
        // Extract the corners of the first rectangle
        Vector[] rect1Corners = rect1.getCorners(framesAhead);
        // Extract the corners of the second rectangle
        Vector[] rect2Corners = rect2.getCorners(framesAhead);

        Vector minimumTranslationVector = null;
        double minimumOverlap = Double.POSITIVE_INFINITY;

        // Test the normal axes of the first rectangle
        Vector[] axes = new Vector[]{rect1Corners[1].subtract(rect1Corners[0]).perpendicular().normalize(), rect1Corners[3].subtract(rect1Corners[0]).perpendicular().normalize(), rect2Corners[1].subtract(rect2Corners[0]).perpendicular().normalize(), rect2Corners[3].subtract(rect2Corners[0]).perpendicular().normalize()};

        for (Vector axis : axes) {
            // Project both rectangles onto the axis
            double[] rect1Projection = PhysicsHelper.projectRectangle(rect1Corners, axis);
            double[] rect2Projection = PhysicsHelper.projectRectangle(rect2Corners, axis);

            // Check for overlap
            double overlap = Math.min(rect1Projection[1], rect2Projection[1]) - Math.max(rect1Projection[0], rect2Projection[0]);
            if (overlap < 0) {
                return null;  // Separating axis found, no collision
            }

            // Track the axis with the minimum overlap
            if (overlap < minimumOverlap) {
                minimumOverlap = overlap;
                minimumTranslationVector = axis.scale(overlap);
            }
        }

        return minimumTranslationVector;  // Return the collision normal based on the minimum translation vector
    }

    static Vector getRectangleToCircleCollisionNormal(int framesAhead,Collider collider1, Collider collider2) {
        RectangleCollider rectangle = collider1 instanceof RectangleCollider ? (RectangleCollider) collider1 : (RectangleCollider) collider2;
        BallCollider ball = collider1 instanceof BallCollider ? (BallCollider) collider1 : (BallCollider) collider2;
        // Calculate the center of the rectangle
        double centerX = rectangle.getPosition(framesAhead).getX() + rectangle.getWidth() / 2.0f;
        double centerY = rectangle.getPosition(framesAhead).getY() + rectangle.getHeight() / 2.0f;

        // Translate and rotate the circle's center to the rectangle's local coordinate system
        double translatedX = ball.getPosition(framesAhead).getX() - centerX;
        double translatedY = ball.getPosition(framesAhead).getY() - centerY;

        // Apply reverse rotation to align to the rectangle's axis
        double angle = -rectangle.getAngle();
        double rotatedX = (translatedX * Math.cos(angle) - translatedY * Math.sin(angle));
        double rotatedY = (translatedX * Math.sin(angle) + translatedY * Math.cos(angle));

        // Determine the closest point on the axis-aligned rectangle in local space
        double rectHalfWidth = rectangle.getWidth() / 2.0f;
        double rectHalfHeight = rectangle.getHeight() / 2.0f;
        double closestX = Math.max(-rectHalfWidth, Math.min(rotatedX, rectHalfWidth));
        double closestY = Math.max(-rectHalfHeight, Math.min(rotatedY, rectHalfHeight));

        // Calculate the distance from the closest point to the rotated circle's center
        double distanceX = rotatedX - closestX;
        double distanceY = rotatedY - closestY;
        double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);

        if (distance < ball.getRadius()) {
            // Normal vector calculation from the closest point back to the circle center
            double magnitude = Math.sqrt(distanceX * distanceX + distanceY * distanceY);
            if (magnitude == 0) {
                // If the center of the circle is exactly on an edge or corner, treat this as a special case
                // You might decide to reflect the circle directly backwards or use some other logic depending on your game's physics
                return new Vector(-translatedX, -translatedY).normalize();  // Reflect directly back
            }
            Vector normal = new Vector(distanceX / magnitude, distanceY / magnitude);

            // Rotate the normal vector back to align with the world coordinates
            double normalWorldX = (normal.getX() * Math.cos(-angle) - normal.getY() * Math.sin(-angle));
            double normalWorldY = (normal.getX() * Math.sin(-angle) + normal.getY() * Math.cos(-angle));

            return new Vector(normalWorldX, normalWorldY);  // Return the transformed normal vector
        }

        return null;  // No collision detected
    }

    static boolean isRectRectCollision(Collider collider1, Collider collider2) {
        return collider1 instanceof RectangleCollider && collider2 instanceof RectangleCollider;
    }

    static Vector getBallBallCollisionNormal(BallCollider ball1, BallCollider ball2) {
        double dx = ball1.getPosition().getX() - ball2.getPosition().getX();
        double dy = ball1.getPosition().getY() - ball2.getPosition().getY();
        double distanceSquared = dx * dx + dy * dy;
        double radiusSum = ball1.getRadius() + ball2.getRadius();

        if (distanceSquared < radiusSum * radiusSum) {
            double distance = (double) Math.sqrt(distanceSquared);
            return new Vector(dx / distance, dy / distance); // Normalized vector
        }
        return null;
    }


    static boolean isFireballCollision(Collision collision) {
        return (collision.getCollider1().getGameObject() instanceof FireBall ||
                (collision.getCollider2() != null && collision.getCollider2().getGameObject() instanceof FireBall));
    }


    static FireBall getFireballFromCollision(Collision collision) {
        if (collision.getCollider1().getGameObject() instanceof FireBall) {
            return (FireBall) collision.getCollider1().getGameObject();
        } else if (collision.getCollider2() != null && collision.getCollider2().getGameObject() instanceof FireBall) {
            return (FireBall) collision.getCollider2().getGameObject();
        }
        return null; // No FireBall involved in the collision
    }
    static void handleBallBoundaryCollision(int framesAhead, BallCollider ballCollider, Vector normal, double boundary, boolean isMaxBoundary, List<Collision> detectedCollisions) {
        double position = (normal.getX() != 0) ? ballCollider.getPosition(framesAhead).getX() : ballCollider.getPosition(framesAhead).getY();
        double radiusAdjust = (normal.getX() != 0) ? ballCollider.getRadius() : 2 * ballCollider.getRadius();
        if ((isMaxBoundary && position + radiusAdjust >= boundary) || (!isMaxBoundary && position - radiusAdjust <= boundary)) {
            detectedCollisions.add(new Collision(ballCollider, null, normal));
        }
    }
}

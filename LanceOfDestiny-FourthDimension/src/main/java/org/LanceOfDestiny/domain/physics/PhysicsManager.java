package org.LanceOfDestiny.domain.physics;

import org.LanceOfDestiny.domain.EventSystem.Events;

import java.util.ArrayList;
import java.util.List;

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

    public List<Collision> checkCollisions() {
        List<Collision> detectedCollisions = new ArrayList<>();
        for (Collider collider1 : colliders) {
            for (Collider collider2 : colliders) {
                if (collider1 == collider2) {
                    continue; // Skip checking collider with itself
                }

                if (isBallBallCollision(collider1, collider2)) {
                    Vector normal = checkBallBallCollision((BallCollider) collider1, (BallCollider) collider2);
                    if (normal == null) {
                        continue;
                    }
                        detectedCollisions.add(new Collision(collider1, collider2, normal));
                } else if (isBallRectCollision(collider1, collider2)) {
                    Vector normal = checkRectangleToCircleCollision(collider1, collider2);
                    if (normal == null) {
                        continue;
                    }
                    detectedCollisions.add(new Collision(collider1, collider2, normal));
                }
            }
        }
        return detectedCollisions;
    }

    public void handleCollisionEvents(List<Collision> collisions) {
        for (Collision collision : collisions) {

            Events.CollisionEvent.invoke(collision); // Trigger the event
        }
    }


    private static boolean isBallRectCollision(Collider collider1, Collider collider2) {
        return (collider1 instanceof RectangleCollider && collider2 instanceof BallCollider) ||
                (collider1 instanceof BallCollider && collider2 instanceof RectangleCollider);
    }

    private static boolean isBallBallCollision(Collider collider1, Collider collider2) {
        return collider1 instanceof BallCollider && collider2 instanceof BallCollider;
    }

    private Vector checkBallBallCollision(BallCollider ball1, BallCollider ball2) {
        float dx = ball1.getPosition().getX() - ball2.getPosition().getX();
        float dy = ball1.getPosition().getY() - ball2.getPosition().getY();
        float distanceSquared = dx * dx + dy * dy;
        float radiusSum = ball1.getRadius() + ball2.getRadius();

        if (distanceSquared < radiusSum * radiusSum) {
            float distance = (float) Math.sqrt(distanceSquared);
            return new Vector(dx / distance, dy / distance); // Normalized vector
        }
        return null;
    }

    private Vector checkRectangleToCircleCollision(Collider collider1, Collider collider2) {
        RectangleCollider rectangle = collider1 instanceof RectangleCollider ? (RectangleCollider) collider1 : (RectangleCollider) collider2;
        BallCollider ball = collider1 instanceof BallCollider ? (BallCollider) collider1 : (BallCollider) collider2;

        if (testRectangleToCircle(rectangle.getWidth(), rectangle.getHeight(), rectangle.getRotation(),
                rectangle.getPosition().getX(), rectangle.getPosition().getY(),
                ball.getPosition().getX(), ball.getPosition().getY(), ball.getRadius())) {
            // Assuming a simplistic approach for the normal calculation - this should be refined for accurate physics responses
            float dx = rectangle.getPosition().getX() - ball.getPosition().getX();
            float dy = rectangle.getPosition().getY() - ball.getPosition().getY();
            float magnitude = (float) Math.sqrt(dx * dx + dy * dy);
            return new Vector(dx / magnitude, dy / magnitude);
        }
        return null;
    }

    // Rectangle to Point Collision Check
    private boolean testRectangleToPoint(double rectWidth, double rectHeight, double rectRotation, double rectCenterX, double rectCenterY, double pointX, double pointY) {
        double unrotatedX = Math.cos(-rectRotation) * (pointX - rectCenterX) - Math.sin(-rectRotation) * (pointY - rectCenterY) + rectCenterX;
        double unrotatedY = Math.sin(-rectRotation) * (pointX - rectCenterX) + Math.cos(-rectRotation) * (pointY - rectCenterY) + rectCenterY;

        // Check if the unrotated point is within the rectangle bounds
        boolean withinXBounds = unrotatedX >= rectCenterX - rectWidth / 2 && unrotatedX <= rectCenterX + rectWidth / 2;
        boolean withinYBounds = unrotatedY >= rectCenterY - rectHeight / 2 && unrotatedY <= rectCenterY + rectHeight / 2;
        return withinXBounds && withinYBounds;
    }

    // Circle to Line Segment Collision Check
    private boolean testCircleToSegment(double circleCenterX, double circleCenterY, double circleRadius, double lineAX, double lineAY, double lineBX, double lineBY) {
        double lineLength = Math.sqrt((lineAX - lineBX) * (lineAX - lineBX) + (lineAY - lineBY) * (lineAY - lineBY));
        double dot = (((circleCenterX - lineAX) * (lineBX - lineAX)) + ((circleCenterY - lineAY) * (lineBY - lineAY))) / Math.pow(lineLength, 2);

        double closestX = lineAX + (dot * (lineBX - lineAX));
        double closestY = lineAY + (dot * (lineBY - lineAY));
        double distanceX = circleCenterX - closestX;
        double distanceY = circleCenterY - closestY;

        boolean onSegment = testPointToSegment(closestX, closestY, lineAX, lineAY, lineBX, lineBY);
        return Math.sqrt((distanceX * distanceX) + (distanceY * distanceY)) < circleRadius && onSegment;
    }

    // Check if a point is on a segment
    private boolean testPointToSegment(double pointX, double pointY, double lineAX, double lineAY, double lineBX, double lineBY) {
        double crossProduct = (pointY - lineAY) * (lineBX - lineAX) - (pointX - lineAX) * (lineBY - lineAY);
        if (Math.abs(crossProduct) > 0.0001) return false; // Not on the line

        double dotProduct = (pointX - lineAX) * (lineBX - lineAX) + (pointY - lineAY) * (lineBY - lineAY);
        if (dotProduct < 0) return false; // On the line, but not the segment

        double squaredLengthBA = (lineBX - lineAX) * (lineBX - lineAX) + (lineBY - lineAY) * (lineBY - lineAY);
        if (dotProduct > squaredLengthBA) return false; // On the line, but not the segment

        return true; // The point is on the segment
    }

    private boolean testRectangleToCircle(double rectWidth, double rectHeight, double rectRotation, double rectCenterX, double rectCenterY, double circleCenterX, double circleCenterY, double circleRadius) {
        // Check collision from the rectangle's perspective
        boolean rectToCircle = testRectangleToPoint(rectWidth, rectHeight, rectRotation, rectCenterX, rectCenterY, circleCenterX, circleCenterY);
        if(rectToCircle) return true; // Early return if the circle's center is inside the rectangle

        // Check for collision from the circle's perspective against each of the rectangle's edges
        // Transform rectangle corners to "unrotated" position relative to the circle for easier calculation
        double halfWidth = rectWidth / 2.0;
        double halfHeight = rectHeight / 2.0;

        double[] rectCornersX = {
                rectCenterX - halfWidth, rectCenterX + halfWidth,
                rectCenterX + halfWidth, rectCenterX - halfWidth
        };
        double[] rectCornersY = {
                rectCenterY - halfHeight, rectCenterY - halfHeight,
                rectCenterY + halfHeight, rectCenterY + halfHeight
        };

        for (int i = 0; i < 4; i++) {
            // Rotate rectangle corner points around the center back to axis-aligned for collision check
            double rotatedX = Math.cos(-rectRotation) * (rectCornersX[i] - rectCenterX) - Math.sin(-rectRotation) * (rectCornersY[i] - rectCenterY) + rectCenterX;
            double rotatedY = Math.sin(-rectRotation) * (rectCornersX[i] - rectCenterX) + Math.cos(-rectRotation) * (rectCornersY[i] - rectCenterY) + rectCenterY;

            rectCornersX[i] = rotatedX;
            rectCornersY[i] = rotatedY;
        }

        // Check circle to each rectangle edge (segment)
        for (int i = 0; i < 4; i++) {
            int next = (i + 1) % 4; // Ensures we loop back around to the first corner
            if (testCircleToSegment(circleCenterX, circleCenterY, circleRadius,
                    rectCornersX[i], rectCornersY[i],
                    rectCornersX[next], rectCornersY[next])) {
                return true;
            }
        }

        return false; // No collision detected
    }
}


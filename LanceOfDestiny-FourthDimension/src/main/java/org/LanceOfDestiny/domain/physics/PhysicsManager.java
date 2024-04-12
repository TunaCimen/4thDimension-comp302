package org.LanceOfDestiny.domain.physics;

import org.LanceOfDestiny.domain.Constants;
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
        //Boundaries of the Map.
        for (Collider collider : colliders) {
            if(collider instanceof BallCollider ballCollider){
                if (collider.getPosition().getX() + ballCollider.getRadius() >= Constants.SCREEN_WIDTH) {
                    //System.out.println(collider.getPosition().getX() + " ---"  + collider.getPosition().getY() + " --r-- " + ballCollider.getRadius());
                    Vector normal = new Vector(-1, 0); // Pointing left
                    detectedCollisions.add(new Collision(collider, null, normal));
                    //collider.setPosition(new Vector(Constants.SCREEN_WIDTH - ballCollider.getRadius(), collider.getPosition().getY()));
                }

                    // Left boundary collision
                if (collider.getPosition().getX() - ballCollider.getRadius() <= 0) {
                    //System.out.println(collider.getPosition().getX() + " ---"  + collider.getPosition().getY() + " --r-- " + ballCollider.getRadius());
                    Vector normal = new Vector(1, 0); // Pointing right
                    detectedCollisions.add(new Collision(collider, null, normal));
                    //collider.setPosition(new Vector(ballCollider.getRadius(), collider.getPosition().getY()));
                }

                // Bottom boundary collision
                // TODO: Why do we need 2* bruh idk but we need to
                if (collider.getPosition().getY() + 2*ballCollider.getRadius() >= Constants.SCREEN_HEIGHT) {
                    System.out.println("Bottom " + collider.getPosition().getX() + " ---"  + collider.getPosition().getY());
                    Vector normal = new Vector(0, -1); // Pointing up
                    detectedCollisions.add(new Collision(collider, null, normal));
                    //collider.setPosition(new Vector(collider.getPosition().getX(), Constants.SCREEN_HEIGHT - ballCollider.getRadius()));
                }


                // Top boundary collision
                if (collider.getPosition().getY() - ballCollider.getRadius() <= 0) {
                    System.out.println("Top " + collider.getPosition().getX() + " ---"  + collider.getPosition().getY() );
                    Vector normal = new Vector(0, 1); // Pointing down
                    detectedCollisions.add(new Collision(collider, null, normal));
                    //collider.setPosition(new Vector(collider.getPosition().getX(), ballCollider.getRadius()));
                }
            }
            // Check right boundary

        }


        for (int i = 0; i < colliders.size(); i++) {
            for (int j = i + 1; j < colliders.size(); j++) {
                Collider collider1 = colliders.get(i);
                Collider collider2 = colliders.get(j);
                if (isBallBallCollision(collider1, collider2)) {
                    Vector normal = getBallBallCollisionNormal((BallCollider) collider1, (BallCollider) collider2);
                    if (normal == null) { //There is no collision it means.
                        continue;
                    }
                    // System.out.println("BALL BALL COLLISINNNNNNNNNNN");
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
            handleBounce(collision);
            Events.CollisionEvent.invoke(collision); // Trigger the event
        }
    }

    private void handleBounce(Collision collision) {
        //Screen Boundary
        if(collision.getCollider2() == null){
            Vector reflection = computeReflection(collision, true);
            collision.getCollider1().setVelocity(reflection);
            // System.out.println("Velocity of collider1 = " + collision.getCollider1().getVelocity().getY());
        } else {
            Vector reflection1 = computeReflection(collision, true);
            Vector reflection2 = computeReflection(collision, false);
            // If the objects are objects that are allowed to change their velocity based on collisions, do so
            if (collision.getCollider1().getColliderType() == ColliderType.DYNAMIC) {
                collision.getCollider1().setVelocity(reflection1);
            }
            if (collision.getCollider2().getColliderType() == ColliderType.DYNAMIC) {
                collision.getCollider2().setVelocity(reflection2);
            }
        }

    }

    private static Vector computeReflection(Collision collision, boolean isFirstCollider) {
        Collider collider;
        if (isFirstCollider) {
            collider = collision.getCollider1();
        } else {
            collider = collision.getCollider2();
        }
        Vector incoming = collider.getVelocity();
        Vector normal = collision.getNormal();
        Vector reflection = incoming.subtract(normal.scale(2).scale(incoming.dotProduct(normal)));
        return reflection;
    }


    private static boolean isBallRectCollision(Collider collider1, Collider collider2) {
        return (collider1 instanceof RectangleCollider && collider2 instanceof BallCollider) ||
                (collider1 instanceof BallCollider && collider2 instanceof RectangleCollider);
    }

    private static boolean isBallBallCollision(Collider collider1, Collider collider2) {
        return collider1 instanceof BallCollider && collider2 instanceof BallCollider;
    }

    private Vector getBallBallCollisionNormal(BallCollider ball1, BallCollider ball2) {
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

        // Determine the closest point on the rectangle to the center of the circle
        // Since the rectangle is represented by the top-left corner:
        float rectLeft = rectangle.getPosition().getX();
        float rectTop = rectangle.getPosition().getY();
        float rectRight = rectLeft + rectangle.getWidth();
        float rectBottom = rectTop + rectangle.getHeight();

        // Using clamping to find the closest x and y on the rectangle to the circle's center
        float closestX = Math.max(rectLeft, Math.min(ball.getPosition().getX(), rectRight));
        float closestY = Math.max(rectTop, Math.min(ball.getPosition().getY(), rectBottom));

        // Calculate the distance from the closest point to the circle's center
        float distanceX = ball.getPosition().getX() - closestX;
        float distanceY = ball.getPosition().getY() - closestY;

        // Check if the distance is less than the radius, indicating a collision
        double sqrt = Math.sqrt(distanceX * distanceX + distanceY * distanceY);
        if (sqrt < ball.getRadius()) {
            // Normalize the vector from the closest point to the center of the circle to get the normal
            float magnitude = ((float) sqrt);
            return new Vector(distanceX / magnitude, distanceY / magnitude);
        }

        return null; // No collision
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


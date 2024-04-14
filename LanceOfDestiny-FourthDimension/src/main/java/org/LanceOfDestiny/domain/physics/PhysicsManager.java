package org.LanceOfDestiny.domain.physics;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.EventSystem.Events;
import org.LanceOfDestiny.domain.GameObject;

import java.util.ArrayList;
import java.util.List;

public class PhysicsManager {
    private static PhysicsManager instance;
    private List<Collider> colliders;
    private final double framesAhead = 1;
    // this allows for precalculating where the ball will be so the ball doesn't get stuck hopefully
    // not using these for ball-ball collisions as they seem fine for the most part

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
        processBoundaryCollisions(detectedCollisions);
        processObjectCollisions(detectedCollisions);
        return detectedCollisions;
    }

    private void processObjectCollisions(List<Collision> detectedCollisions) {
        for (int i = 0; i < colliders.size(); i++) {
            Collider collider1 = colliders.get(i);
            if (!collider1.isEnabled()) continue;

            for (int j = i + 1; j < colliders.size(); j++) {
                Collider collider2 = colliders.get(j);
                if (!collider2.isEnabled()) continue;

                if (isRectRectCollision(collider1, collider2)) {
                    Vector normal = getRectRectCollisionNormal((RectangleCollider) collider1, (RectangleCollider) collider2);
                    if (normal != null) {
                        detectedCollisions.add(new Collision(collider1, collider2, normal));
                    }
                } else if (isBallBallCollision(collider1, collider2)) {
                    Vector normal = getBallBallCollisionNormal((BallCollider) collider1, (BallCollider) collider2);
                    if (normal != null) {
                        detectedCollisions.add(new Collision(collider1, collider2, normal));
                    }
                } else if (isBallRectCollision(collider1, collider2)) {
                    Vector normal = getRectangleToCircleCollisionNormal(collider1, collider2);
                    if (normal != null) {
                        detectedCollisions.add(new Collision(collider1, collider2, normal));
                    }
                }
            }
        }
    }

    private void processBoundaryCollisions(List<Collision> detectedCollisions) {
        for (Collider collider : colliders) {
            if (!collider.isEnabled()) continue;

            if (collider instanceof BallCollider ballCollider) {
                checkBoundaryCollision(ballCollider, detectedCollisions);
            }
        }
    }

    private void checkBoundaryCollision(BallCollider ballCollider, List<Collision> detectedCollisions) {
        // Boundary checks for each direction using helper method
        handleBoundary(ballCollider, new Vector(-1, 0), Constants.SCREEN_WIDTH - ballCollider.getRadius(), true, detectedCollisions);
        handleBoundary(ballCollider, new Vector(1, 0), 0 + ballCollider.getRadius(), false, detectedCollisions);
        handleBoundary(ballCollider, new Vector(0, -1), Constants.SCREEN_HEIGHT - ballCollider.getRadius(), true, detectedCollisions);
        handleBoundary(ballCollider, new Vector(0, 1), 0 + ballCollider.getRadius(), false, detectedCollisions);
    }

    private void handleBoundary(BallCollider ballCollider, Vector normal, double boundary, boolean isMaxBoundary, List<Collision> detectedCollisions) {
        double position = (normal.getX() != 0) ? ballCollider.getPosition(framesAhead).getX() : ballCollider.getPosition(framesAhead).getY();
        double radiusAdjust = (normal.getX() != 0) ? ballCollider.getRadius() : 2 * ballCollider.getRadius();
        if ((isMaxBoundary && position + radiusAdjust >= boundary) || (!isMaxBoundary && position - radiusAdjust <= boundary)) {
            detectedCollisions.add(new Collision(ballCollider, null, normal));
        }
    }


    public void handleCollisionEvents(List<Collision> collisions) {
        for (Collision collision : collisions) {
            GameObject gameObject1 = collision.getCollider1().getGameObject();
            GameObject gameObject2 = collision.getCollider2() != null ? collision.getCollider2().getGameObject() : null;

            // Check if either collider is a trigger
            if (collision.getCollider1().isTrigger() || (gameObject2 != null && collision.getCollider2().isTrigger())) {
                // Invoke onTriggerEnter on the first game object
                gameObject1.onTriggerEnter(collision);

                // If there's a second game object, invoke onTriggerEnter on it too
                if (gameObject2 != null) {
                    gameObject2.onTriggerEnter(collision);
                }
            } else {
                // Handle physical collision responses
                handleBounce(collision);

                // Invoke onCollisionEnter on the first game object
                gameObject1.onCollisionEnter(collision);

                // If there's a second game object, invoke onCollisionEnter on it too
                if (gameObject2 != null) {
                    gameObject2.onCollisionEnter(collision);
                }
            }
        }
    }



    private void handleBounce(Collision collision) {
        //Screen Boundary
        if (collision.getCollider2() == null) {
            Vector reflection = getReflection(collision, true);
            collision.getCollider1().setVelocity(reflection);
            // System.out.println("Velocity of collider1 = " + collision.getCollider1().getVelocity().getY());
        } else {
            Vector reflection1 = getReflection(collision, true);
            Vector reflection2 = getReflection(collision, false);
            // If the objects are objects that are allowed to change their velocity based on collisions, do so
            if (collision.getCollider1().getColliderType() == ColliderType.DYNAMIC) {
                collision.getCollider1().setVelocity(reflection1);
            }
            if (collision.getCollider2().getColliderType() == ColliderType.DYNAMIC) {
                collision.getCollider2().setVelocity(reflection2);
            }
        }

    }

    private static Vector getReflection(Collision collision, boolean isFirstCollider) {
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
        return (collider1 instanceof RectangleCollider && collider2 instanceof BallCollider) || (collider1 instanceof BallCollider && collider2 instanceof RectangleCollider);
    }

    private static boolean isBallBallCollision(Collider collider1, Collider collider2) {
        return collider1 instanceof BallCollider && collider2 instanceof BallCollider;
    }

    private boolean isRectRectCollision(Collider collider1, Collider collider2) {
        return collider1 instanceof RectangleCollider && collider2 instanceof RectangleCollider;
    }

    private Vector getBallBallCollisionNormal(BallCollider ball1, BallCollider ball2) {
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

    private Vector getRectangleToCircleCollisionNormal(Collider collider1, Collider collider2) {
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
        double rotatedX = (double) (translatedX * Math.cos(angle) - translatedY * Math.sin(angle));
        double rotatedY = (double) (translatedX * Math.sin(angle) + translatedY * Math.cos(angle));

        // Determine the closest point on the axis-aligned rectangle in local space
        double rectHalfWidth = rectangle.getWidth() / 2.0f;
        double rectHalfHeight = rectangle.getHeight() / 2.0f;
        double closestX = Math.max(-rectHalfWidth, Math.min(rotatedX, rectHalfWidth));
        double closestY = Math.max(-rectHalfHeight, Math.min(rotatedY, rectHalfHeight));

        // Calculate the distance from the closest point to the rotated circle's center
        double distanceX = rotatedX - closestX;
        double distanceY = rotatedY - closestY;
        double distance = (double) Math.sqrt(distanceX * distanceX + distanceY * distanceY);

        if (distance < ball.getRadius()) {
            // Normal vector calculation from the closest point back to the circle center
            double magnitude = (double) Math.sqrt(distanceX * distanceX + distanceY * distanceY);
            Vector normal = new Vector(distanceX / magnitude, distanceY / magnitude);

            // Rotate the normal vector back to align with the world coordinates
            double normalWorldX = (double) (normal.getX() * Math.cos(-angle) - normal.getY() * Math.sin(-angle));
            double normalWorldY = (double) (normal.getX() * Math.sin(-angle) + normal.getY() * Math.cos(-angle));

            return new Vector(normalWorldX, normalWorldY);  // Return the transformed normal vector
        }

        return null;  // No collision detected
    }

    private Vector getRectRectCollisionNormal(RectangleCollider rect1, RectangleCollider rect2) {
        // Extract the corners of the first rectangle
        Vector[] rect1Corners = rect1.getCorners(framesAhead);
        // Extract the corners of the second rectangle
        Vector[] rect2Corners = rect2.getCorners(framesAhead);

        Vector minimumTranslationVector = null;
        double minimumOverlap = Double.POSITIVE_INFINITY;

        // Test the normal axes of the first rectangle
        Vector[] axes = new Vector[]{
                rect1Corners[1].subtract(rect1Corners[0]).perpendicular().normalize(),
                rect1Corners[3].subtract(rect1Corners[0]).perpendicular().normalize(),
                rect2Corners[1].subtract(rect2Corners[0]).perpendicular().normalize(),
                rect2Corners[3].subtract(rect2Corners[0]).perpendicular().normalize()
        };

        for (Vector axis : axes) {
            // Project both rectangles onto the axis
            double[] rect1Projection = projectRectangle(rect1Corners, axis);
            double[] rect2Projection = projectRectangle(rect2Corners, axis);

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

    private double[] projectRectangle(Vector[] corners, Vector axis) {
        double min = axis.dotProduct(corners[0]);
        double max = min;
        for (int i = 1; i < corners.length; i++) {
            double projection = axis.dotProduct(corners[i]);
            if (projection < min) min = projection;
            if (projection > max) max = projection;
        }
        return new double[] { min, max };
    }



}


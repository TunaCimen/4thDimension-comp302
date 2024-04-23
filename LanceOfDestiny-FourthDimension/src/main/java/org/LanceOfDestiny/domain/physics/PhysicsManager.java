package org.LanceOfDestiny.domain.physics;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.barriers.Barrier;
import org.LanceOfDestiny.domain.behaviours.GameObject;
import org.LanceOfDestiny.domain.player.FireBall;
import org.LanceOfDestiny.domain.player.MagicalStaff;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PhysicsManager {
    private static PhysicsManager instance;
    private List<Collider> colliders;
    // this will be made non-zero if we need predictive collision detection code
    private double framesAhead = 2;
    private int currentFrame = 0;
    private int frameTreshold = 0;
    private Map<String, Integer> recentCollisions;  // Stores collision pairs with the frame of their last occurrence

    private PhysicsManager() {
        colliders = new ArrayList<>();
        recentCollisions = new HashMap<>();
    }

    public static PhysicsManager getInstance() {
        if (instance == null) {
            instance = new PhysicsManager();
        }
        return instance;
    }
    public void updateCollisions() {
        handleCollisionEvents(PhysicsManager.getInstance().checkCollisions());
    }
    private static Vector getReflection(Collision collision, Collider collider) {
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

    public void addCollider(Collider collider) {
        colliders.add(collider);
    }

    public void removeCollider(Collider collider) {
        colliders.remove(collider);
    }

    private List<Collision> checkCollisions() {
        currentFrame++; // Assume a method to get the current frame
        List<Collision> detectedCollisions = new ArrayList<>();
        processBoundaryCollisions(detectedCollisions);
        processObjectCollisions(detectedCollisions, currentFrame);
        cleanupRecentCollisions(currentFrame);
        return detectedCollisions;
    }

    private void processObjectCollisions(List<Collision> detectedCollisions, int currentFrame) {
        for (int i = 0; i < colliders.size(); i++) {
            Collider collider1 = colliders.get(i);
            if (!collider1.isEnabled()) continue;

            for (int j = i + 1; j < colliders.size(); j++) {
                Collider collider2 = colliders.get(j);
                if (!collider2.isEnabled()) continue;

                String collisionKey = generateCollisionKey(collider1, collider2);
                if (isSpecialCollision(collider1, collider2) && recentCollisions.containsKey(collisionKey) && (currentFrame - recentCollisions.get(collisionKey) <= frameTreshold)) {
                    continue;  // Skip this collision for the special cases within the timeframe
                }

                if (checkAndHandleCollision(collider1, collider2, detectedCollisions)) {
                    if (isSpecialCollision(collider1, collider2)) {
                        recentCollisions.put(collisionKey, currentFrame); // Update the frame of the last collision
                    }
                }
            }
        }
    }

    private boolean checkAndHandleCollision(Collider c1, Collider c2, List<Collision> collisions) {
        Vector normal;
        if (isRectRectCollision(c1, c2)) {
            normal = getRectRectCollisionNormal((RectangleCollider) c1, (RectangleCollider) c2);
        } else if (isBallBallCollision(c1, c2)) {
            normal = getBallBallCollisionNormal((BallCollider) c1, (BallCollider) c2);
        } else if (isBallRectCollision(c1, c2)) {
            normal = getRectangleToCircleCollisionNormal(c1, c2);
        } else {
            normal = null;
        }
        if (normal != null) {
            collisions.add(new Collision(c1, c2, normal));
            return true;
        }
        return false;
    }

    private boolean isSpecialCollision(Collider a, Collider b) {
        return (a.gameObject instanceof FireBall && b.gameObject instanceof MagicalStaff) ||
                (a.gameObject instanceof MagicalStaff && b.gameObject instanceof FireBall);
    }

    private String generateCollisionKey(Collider a, Collider b) {
        int id1 = System.identityHashCode(a);
        int id2 = System.identityHashCode(b);
        return id1 < id2 ? id1 + "-" + id2 : id2 + "-" + id1;  // Ensure consistent ordering
    }

    private void cleanupRecentCollisions(int currentFrame) {
        recentCollisions.entrySet().removeIf(entry -> currentFrame - entry.getValue() > frameTreshold);
    }

    private void processBoundaryCollisions(List<Collision> detectedCollisions) {
        for (Collider collider : colliders) {
            if (!collider.isEnabled()) continue;
            if (collider instanceof BallCollider ballCollider) {
                checkBoundaryCollision(ballCollider, detectedCollisions);
            } else if (collider instanceof RectangleCollider rectangleCollider) {
                checkBoundaryCollision(rectangleCollider, detectedCollisions);
            }
        }
    }

    private void checkBoundaryCollision(RectangleCollider rectangleCollider, List<Collision> detectedCollisions) {
        Vector pos = rectangleCollider.getPosition(framesAhead); // Assuming the position is the center of the rectangle

        // Calculate boundaries of the rectangle
        double left = pos.getX() ;
        double right = pos.getX() + rectangleCollider.getWidth();
        double top = pos.getY();
        double bottom = pos.getY() + rectangleCollider.getHeight();

        // Check each boundary and add collisions as necessary
        if (left <= 0) { // Left boundary collision
            detectedCollisions.add(new Collision(rectangleCollider, null, new Vector(1, 0))); // Normal pointing right
        }
        if (right >= Constants.SCREEN_WIDTH) { // Right boundary collision
            detectedCollisions.add(new Collision(rectangleCollider, null, new Vector(-1, 0))); // Normal pointing left
        }
        if (top <= 0) { // Top boundary collision
            detectedCollisions.add(new Collision(rectangleCollider, null, new Vector(0, 1))); // Normal pointing down
        }
        if (bottom >= Constants.SCREEN_HEIGHT) { // Bottom boundary collision
            detectedCollisions.add(new Collision(rectangleCollider, null, new Vector(0, -1))); // Normal pointing up
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

    private void handleCollisionEvents(List<Collision> collisions) {
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
        // Screen Boundary Collision
        if (collision.getCollider2() == null) {
            handleScreenBounce(collision);
            return;
        }
        // Normal collision but check for FireBall being overwhelming
        if (isOverwhelmingFireballBarrierCollision(collision)) {
            return; // do not bounce the ball
        }
        if (!isFireballCollision(collision)) {
            handleRegularBounce(collision); // currently this should do nothing since only fireball is dynamic
            return;
        }
        // do the annoying calculations here
        handleFireballBounce(collision);
    }

    private static void handleScreenBounce(Collision collision) {
        Vector reflection = getReflection(collision, collision.getCollider1());
        collision.getCollider1().setVelocity(reflection);
    }

    private void handleFireballBounce(Collision collision) {
        FireBall fireball = getFireballFromCollision(collision);
        GameObject otherGameObject = collision.getOther(fireball);
        Vector fireballVelocity = fireball.getCollider().getVelocity();
        Vector otherVelocity = otherGameObject.getCollider().getVelocity();
        Vector otherObjectDirection = otherGameObject.getDirection();
        final double speedIncrease = 5 * Constants.UPDATE_RATE;
        if (otherVelocity.isZero()) {
            handleRegularBounce(collision);
            return;
        }
        if (!otherObjectDirection.isZero() && otherObjectDirection.isPerpendicular(fireballVelocity)) {
            // Calculate the angle of reflection at 45 degrees relative to the line of movement direction
            double angleOfReflection = Math.PI / 4; // 45 degrees in radians
            Vector reflectionDirection = otherVelocity.normalize();
            // Rotate the reflection direction by 45 degrees to either side
            Vector rotatedVector = reflectionDirection.rotateVector(angleOfReflection);
            // Set the new velocity with the same magnitude as the original velocity
            double fireballSpeed = fireballVelocity.magnitude();
            fireball.getCollider().setVelocity(rotatedVector.scale(fireballSpeed));
            return;
        }
        if (!otherObjectDirection.isSameDirectionX(fireballVelocity) && !otherObjectDirection.isSameDirectionY(fireballVelocity)) {
            // will hopefully cause 180 degree return
            fireball.getCollider().setVelocity(fireballVelocity.scale(-1));
            return;
        }

        // separately checking for x and y directions
        if (otherObjectDirection.isSameDirectionX(fireballVelocity)) {
            fireball.getCollider().setVelocity(fireballVelocity.add(new Vector(fireballVelocity.getDirectionSignVector().getX() + speedIncrease, 0)));
        }
        if (otherObjectDirection.isSameDirectionY(fireballVelocity)) {
            fireball.getCollider().setVelocity(fireballVelocity.add(new Vector(0,fireballVelocity.getDirectionSignVector().getY() + speedIncrease)));
        }
        handleRegularBounce(collision);
    }


    private static void handleRegularBounce(Collision collision) {
        Vector reflection1 = getReflection(collision, collision.getCollider1());
        Vector reflection2 = getReflection(collision, collision.getCollider2());

        // Apply bounce only if the colliders are dynamic and it's not an overwhelming FireBall collision
        if (collision.getCollider1().getColliderType() == ColliderType.DYNAMIC) {
            collision.getCollider1().setVelocity(reflection1);
        }
        if (collision.getCollider2().getColliderType() == ColliderType.DYNAMIC) {
            collision.getCollider2().setVelocity(reflection2);
        }
    }

    private boolean isFireballCollision(Collision collision) {
        return (collision.getCollider1().getGameObject() instanceof FireBall ||
                (collision.getCollider2() != null && collision.getCollider2().getGameObject() instanceof FireBall));
    }


    private FireBall getFireballFromCollision(Collision collision) {
        if (collision.getCollider1().getGameObject() instanceof FireBall) {
            return (FireBall) collision.getCollider1().getGameObject();
        } else if (collision.getCollider2() != null && collision.getCollider2().getGameObject() instanceof FireBall) {
            return (FireBall) collision.getCollider2().getGameObject();
        }
        return null; // No FireBall involved in the collision
    }


    private static boolean isOverwhelmingFireballBarrierCollision(Collision collision) {
        return ((collision.getCollider1().getGameObject() instanceof FireBall && ((FireBall) collision.getCollider1().getGameObject()).isOverwhelming()) &&
                (collision.getCollider2() != null && collision.getCollider2().getGameObject() instanceof Barrier)) ||
                ((collision.getCollider2() != null && collision.getCollider2().getGameObject() instanceof FireBall && ((FireBall) collision.getCollider2().getGameObject()).isOverwhelming()) &&
                        (collision.getCollider1().getGameObject() instanceof Barrier));
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

    private Vector getRectRectCollisionNormal(RectangleCollider rect1, RectangleCollider rect2) {
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
        return new double[]{min, max};
    }


}


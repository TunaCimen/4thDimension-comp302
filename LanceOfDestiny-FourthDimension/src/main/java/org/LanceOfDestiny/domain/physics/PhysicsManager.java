package org.LanceOfDestiny.domain.physics;

import org.LanceOfDestiny.domain.Constants;
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

    private int framesAhead = 2;
    private int currentFrame = 0;
    private int frameTreshold = 0; // currently not effective within the code

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
    private List<Collision> checkCollisions() {
        currentFrame++;
        List<Collision> detectedCollisions = new ArrayList<>();
        processBoundaryCollisions(detectedCollisions);
        processObjectCollisions(detectedCollisions, currentFrame);
        cleanupRecentCollisions(currentFrame);
        return detectedCollisions;
    }

    public void addCollider(Collider collider) {
        colliders.add(collider);
    }

    public void removeCollider(Collider collider) {
        colliders.remove(collider);
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
        if (PhysicsHelper.isRectRectCollision(c1, c2)) {
            normal = PhysicsHelper.getRectRectCollisionNormal(framesAhead, (RectangleCollider) c1, (RectangleCollider) c2);
        } else if (PhysicsHelper.isBallBallCollision(c1, c2)) {
            normal = PhysicsHelper.getBallBallCollisionNormal((BallCollider) c1, (BallCollider) c2);
        } else if (PhysicsHelper.isBallRectCollision(c1, c2)) {
            normal = PhysicsHelper.getRectangleToCircleCollisionNormal(framesAhead, c1, c2);
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
        PhysicsHelper.handleBallBoundaryCollision(framesAhead, ballCollider, new Vector(-1, 0), Constants.SCREEN_WIDTH - ballCollider.getRadius(), true, detectedCollisions);
        PhysicsHelper.handleBallBoundaryCollision(framesAhead, ballCollider, new Vector(1, 0), 0 + ballCollider.getRadius(), false, detectedCollisions);
        PhysicsHelper.handleBallBoundaryCollision(framesAhead, ballCollider, new Vector(0, -1), Constants.SCREEN_HEIGHT - ballCollider.getRadius(), true, detectedCollisions);
        PhysicsHelper.handleBallBoundaryCollision(framesAhead, ballCollider, new Vector(0, 1), 0 + ballCollider.getRadius(), false, detectedCollisions);
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
        if (PhysicsHelper.isOverwhelmingFireballBarrierCollision(collision)) {
            return; // do not bounce the ball
        }
        if (!PhysicsHelper.isFireballCollision(collision)) {
            handleRegularBounce(collision); // currently this should do nothing since only fireball is dynamic
            return;
        }
        // do the annoying calculations here
        handleFireballBounce(collision);
    }

    private static void handleScreenBounce(Collision collision) {
        Vector reflection = PhysicsHelper.getReflection(collision, collision.getCollider1());
        collision.getCollider1().setVelocity(reflection);
    }

    private void handleFireballBounce(Collision collision) {
        FireBall fireball = PhysicsHelper.getFireballFromCollision(collision);
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
        Vector reflection1 = PhysicsHelper.getReflection(collision, collision.getCollider1());
        Vector reflection2 = PhysicsHelper.getReflection(collision, collision.getCollider2());
        // Apply bounce only if the colliders are dynamic and it's not an overwhelming FireBall collision
        if (collision.getCollider1().getColliderType() == ColliderType.DYNAMIC) {
            collision.getCollider1().setVelocity(reflection1);
        }
        if (collision.getCollider2().getColliderType() == ColliderType.DYNAMIC) {
            collision.getCollider2().setVelocity(reflection2);
        }
    }












}


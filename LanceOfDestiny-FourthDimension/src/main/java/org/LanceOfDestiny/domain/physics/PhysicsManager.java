package org.LanceOfDestiny.domain.physics;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.EventSystem.Events;

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
        //Boundaries of the Map.
        for (Collider collider : colliders) {
            if(collider instanceof BallCollider ballCollider){
                if (collider.getPosition(framesAhead).getX() + ballCollider.getRadius() >= Constants.SCREEN_WIDTH) {
                    //System.out.println(collider.getPosition().getX() + " ---"  + collider.getPosition().getY() + " --r-- " + ballCollider.getRadius());
                    Vector normal = new Vector(-1, 0); // Pointing left
                    detectedCollisions.add(new Collision(collider, null, normal));
                    //collider.setPosition(new Vector(Constants.SCREEN_WIDTH - ballCollider.getRadius(), collider.getPosition().getY()));
                }

                    // Left boundary collision
                if (collider.getPosition(framesAhead).getX() - ballCollider.getRadius() <= 0) {
                    //System.out.println(collider.getPosition().getX() + " ---"  + collider.getPosition().getY() + " --r-- " + ballCollider.getRadius());
                    Vector normal = new Vector(1, 0); // Pointing right
                    detectedCollisions.add(new Collision(collider, null, normal));
                    //collider.setPosition(new Vector(ballCollider.getRadius(), collider.getPosition().getY()));
                }

                // Bottom boundary collision
                // TODO: Why do we need 2* bruh idk but we need to
                if (collider.getPosition(framesAhead).getY() + 2*ballCollider.getRadius() >= Constants.SCREEN_HEIGHT) {
                    System.out.println("Bottom " + collider.getPosition().getX() + " ---"  + collider.getPosition().getY());
                    Vector normal = new Vector(0, -1); // Pointing up
                    detectedCollisions.add(new Collision(collider, null, normal));
                    //collider.setPosition(new Vector(collider.getPosition().getX(), Constants.SCREEN_HEIGHT - ballCollider.getRadius()));
                }


                // Top boundary collision
                if (collider.getPosition(framesAhead).getY() - ballCollider.getRadius() <= 0) {
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
                    Vector normal = getRectangleToCircleCollisionNormal(collider1, collider2);
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

    private Vector getRectangleToCircleCollisionNormal(Collider collider1, Collider collider2) {
        RectangleCollider rectangle = collider1 instanceof RectangleCollider ? (RectangleCollider) collider1 : (RectangleCollider) collider2;
        BallCollider ball = collider1 instanceof BallCollider ? (BallCollider) collider1 : (BallCollider) collider2;
        System.out.println(rectangle.getAngle());
        // Calculate the center of the rectangle
        float centerX = rectangle.getPosition(framesAhead).getX() + rectangle.getWidth() / 2.0f;
        float centerY = rectangle.getPosition(framesAhead).getY() + rectangle.getHeight() / 2.0f;

        // Translate and rotate the circle's center to the rectangle's local coordinate system
        float translatedX = ball.getPosition(framesAhead).getX() - centerX;
        float translatedY = ball.getPosition(framesAhead).getY() - centerY;

        // Apply reverse rotation to align to the rectangle's axis
        double angle = -rectangle.getAngle();
        float rotatedX = (float) (translatedX * Math.cos(angle) - translatedY * Math.sin(angle));
        float rotatedY = (float) (translatedX * Math.sin(angle) + translatedY * Math.cos(angle));

        // Determine the closest point on the axis-aligned rectangle in local space
        float rectHalfWidth = rectangle.getWidth() / 2.0f;
        float rectHalfHeight = rectangle.getHeight() / 2.0f;
        float closestX = Math.max(-rectHalfWidth, Math.min(rotatedX, rectHalfWidth));
        float closestY = Math.max(-rectHalfHeight, Math.min(rotatedY, rectHalfHeight));

        // Calculate the distance from the closest point to the rotated circle's center
        float distanceX = rotatedX - closestX;
        float distanceY = rotatedY - closestY;
        float distance = (float) Math.sqrt(distanceX * distanceX + distanceY * distanceY);

        if (distance < ball.getRadius()) {
            // Normal vector calculation from the closest point back to the circle center
            float magnitude = (float) Math.sqrt(distanceX * distanceX + distanceY * distanceY);
            Vector normal = new Vector(distanceX / magnitude, distanceY / magnitude);

            // Rotate the normal vector back to align with the world coordinates
            float normalWorldX = (float) (normal.getX() * Math.cos(-angle) - normal.getY() * Math.sin(-angle));
            float normalWorldY = (float) (normal.getX() * Math.sin(-angle) + normal.getY() * Math.cos(-angle));

            return new Vector(normalWorldX, normalWorldY);  // Return the transformed normal vector
        }

        return null;  // No collision detected
    }





}


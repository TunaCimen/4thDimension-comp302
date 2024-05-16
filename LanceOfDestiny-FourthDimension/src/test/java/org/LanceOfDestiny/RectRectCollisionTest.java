package org.LanceOfDestiny;

import org.LanceOfDestiny.domain.physics.Vector;
import org.LanceOfDestiny.domain.physics.ColliderType;
import org.LanceOfDestiny.domain.physics.RectangleCollider;
import org.LanceOfDestiny.domain.physics.PhysicsHelper;
import org.LanceOfDestiny.domain.barriers.SimpleBarrier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.lang.reflect.Method;

public class RectRectCollisionTest {

    private RectangleCollider rect1;
    private RectangleCollider rect2;

    @BeforeEach
    public void setUp() {
        SimpleBarrier barrier1 = new SimpleBarrier(new Vector(0, 0));
        SimpleBarrier barrier2 = new SimpleBarrier(new Vector(10, 10));

        rect1 = new RectangleCollider(barrier1, new Vector(0, 0), ColliderType.STATIC, 28, 20);
        rect2 = new RectangleCollider(barrier2, new Vector(0, 0), ColliderType.STATIC, 28, 20);
    }


    // checks if the method detects a collision in the next frame (framesAhead = 1)
    @Test
    public void testRectRectCollisionNormalOneFrameAhead() throws Exception {
        SimpleBarrier barrier2 = new SimpleBarrier(new Vector(29, 0));
        rect2 = new RectangleCollider(barrier2, new Vector(-5, 0), ColliderType.STATIC, 28, 20);

        Method method = PhysicsHelper.class.getDeclaredMethod("getRectRectCollisionNormal", int.class, RectangleCollider.class, RectangleCollider.class);
        method.setAccessible(true);

        Vector collisionNormal = (Vector) method.invoke(null, 5, rect1, rect2);
        assertNotNull(collisionNormal);
        assertTrue(collisionNormal.magnitude() > 0);  // The rectangles should collide in the next frame
    }

    // checks if the method does not detect a collision if rect2 moves away in the future (framesAhead = 10)
    @Test
    public void testRectRectCollisionNormalTenFramesAhead() throws Exception {
        SimpleBarrier barrier2 = new SimpleBarrier(new Vector(0, 0));
        rect2 = new RectangleCollider(barrier2, new Vector(50, 50), ColliderType.STATIC, 28, 20);

        Method method = PhysicsHelper.class.getDeclaredMethod("getRectRectCollisionNormal", int.class, RectangleCollider.class, RectangleCollider.class);
        method.setAccessible(true);

        Vector collisionNormal = (Vector) method.invoke(null, 10, rect1, rect2);
        assertNull(collisionNormal);  // The rectangles should not collide in the next 10 frames
    }


    //  checks if method detects collision and returns the correct normal
    @Test
    public void testRectRectCollisionNormalCollisionDetected() throws Exception {
        Method method = PhysicsHelper.class.getDeclaredMethod("getRectRectCollisionNormal", int.class, RectangleCollider.class, RectangleCollider.class);
        method.setAccessible(true);
        Vector collisionNormal = (Vector) method.invoke(null, 0, rect1, rect2);
        assertNotNull(collisionNormal); // Black-Box: got no internal knowledge of method
        // The minimum overlap is at the axis with the smallest overlap
        assertEquals(20 - 10, collisionNormal.magnitude(), 0.1); // Glass-Box: Knows the internal logic to check the calculated minimum overlap
    }

    //  checks if method returns null when there is no collision
    @Test
    public void testRectRectCollisionNormalNoCollision() throws Exception {
        SimpleBarrier barrier3 = new SimpleBarrier(new Vector(100, 100));
        rect2 = new RectangleCollider(barrier3, new Vector(0, 0), ColliderType.STATIC, 28, 20);
        Method method = PhysicsHelper.class.getDeclaredMethod("getRectRectCollisionNormal", int.class, RectangleCollider.class, RectangleCollider.class);
        method.setAccessible(true);
        Vector collisionNormal = (Vector) method.invoke(null, 0, rect1, rect2);
        assertNull(collisionNormal);
    }

    // checks if method detects collision and returns positive collisionNormal when rectangles overlap at the corner
    @Test
    public void testRectRectCollisionNormalCornerOverlap() throws Exception {
        SimpleBarrier barrier4 = new SimpleBarrier(new Vector(27, 19));  // Slightly less than width and height
        rect2 = new RectangleCollider(barrier4, new Vector(0, 0), ColliderType.STATIC, 28, 20);
        Method method = PhysicsHelper.class.getDeclaredMethod("getRectRectCollisionNormal", int.class, RectangleCollider.class, RectangleCollider.class);
        method.setAccessible(true);
        Vector collisionNormal = (Vector) method.invoke(null, 0, rect1, rect2);
        assertNotNull(collisionNormal); // Black-Box
        assertTrue(collisionNormal.magnitude() > 0); // Glass-Box
    }

    // checks if method handles large overlaps correctly.
    @Test
    public void testRectRectCollisionNormalLargeOverlap() throws Exception {
        SimpleBarrier barrier5 = new SimpleBarrier(new Vector(10, 10));
        rect2 = new RectangleCollider(barrier5, new Vector(0, 0), ColliderType.STATIC, 100, 100);  // Large collider
        Method method = PhysicsHelper.class.getDeclaredMethod("getRectRectCollisionNormal", int.class, RectangleCollider.class, RectangleCollider.class);
        method.setAccessible(true);
        Vector collisionNormal = (Vector) method.invoke(null, 0, rect1, rect2);
        assertNotNull(collisionNormal); // Black-Box
        assertTrue(collisionNormal.magnitude() > 0); // Glass-Box
    }

    @Test
    public void testRectRectCollisionNormalBoundaryCondition() throws Exception {
        SimpleBarrier barrier6 = new SimpleBarrier(new Vector(28, 0));  // Exactly the width away
        rect2 = new RectangleCollider(barrier6, new Vector(0, 0), ColliderType.STATIC, 28, 20);
        Method method = PhysicsHelper.class.getDeclaredMethod("getRectRectCollisionNormal", int.class, RectangleCollider.class, RectangleCollider.class);
        method.setAccessible(true);
        Vector collisionNormal = (Vector) method.invoke(null, 0, rect1, rect2);
        assertNotNull(collisionNormal);
        // minimum overlap should be 0 for this case, if it would be negative, it would mean that the rectangles are not colliding
        assertTrue(collisionNormal.magnitude() >= 0);
    }

    // checks if method handles when two rectangles touching at the edge
    @Test
    public void testRectRectCollisionNormalEdgeTouching() throws Exception {
        SimpleBarrier barrier7 = new SimpleBarrier(new Vector(28, 20));  // Exactly touching at one edge
        rect2 = new RectangleCollider(barrier7, new Vector(0, 0), ColliderType.STATIC, 28, 20);
        Method method = PhysicsHelper.class.getDeclaredMethod("getRectRectCollisionNormal", int.class, RectangleCollider.class, RectangleCollider.class);
        method.setAccessible(true);
        Vector collisionNormal = (Vector) method.invoke(null, 0, rect1, rect2);
        assertNotNull(collisionNormal);
    }
}

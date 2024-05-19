package org.LanceOfDestiny;

import static org.junit.jupiter.api.Assertions.*;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.physics.Vector;
import org.LanceOfDestiny.domain.player.MagicalStaff;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MagicalStaffTest {

    private MagicalStaff staff;
    private static final double DELTA = 1e-5; // tolerance for floating-point comparisons

    @BeforeEach
    public void setUp() {
        staff = new MagicalStaff();
        staff.setPosition(new Vector(100, 100)); // Set initial position
    }

    @Test
    public void testMoveRightWithinBoundaries() {
        staff.moveRight(1);
        assertEquals(100 + Constants.STAFF_SPEED, staff.getPosition().getX(), DELTA);
        assertEquals(100, staff.getPosition().getY(), DELTA);
    }

    @Test
    public void testMoveLeftWithinBoundaries() {
        staff.moveRight(-1);
        assertEquals(100 - Constants.STAFF_SPEED, staff.getPosition().getX(), DELTA);
        assertEquals(100, staff.getPosition().getY(), DELTA);
    }

    @Test
    public void testMoveRightAtMaxBoundary() {
        staff.setPosition(new Vector(Constants.SCREEN_WIDTH - Constants.STAFF_WIDTH, 100));
        staff.moveRight(1);
        assertEquals(Constants.SCREEN_WIDTH - Constants.STAFF_WIDTH, staff.getPosition().getX(), DELTA);
        assertEquals(100, staff.getPosition().getY(), DELTA);
    }

    @Test
    public void testMoveLeftAtMinBoundary() {
        staff.setPosition(new Vector(0, 100));
        staff.moveRight(-1);
        assertEquals(0, staff.getPosition().getX(), DELTA);
        assertEquals(100, staff.getPosition().getY(), DELTA);
    }

    @Test
    public void testMoveRightWhenExpanded() {
        staff.enableExpansion();
        staff.moveRight(1);
        assertEquals(100 + Constants.STAFF_SPEED, staff.getPosition().getX(), DELTA);
        assertEquals(100, staff.getPosition().getY(), DELTA);
    }

    @Test
    public void testMoveLeftWhenExpanded() {
        staff.enableExpansion();
        staff.moveRight(-1);
        assertEquals(100 - Constants.STAFF_SPEED, staff.getPosition().getX(), DELTA);
        assertEquals(100, staff.getPosition().getY(), DELTA);
    }

    @Test
    public void testMoveAtBoundaryWhenExpanded() {
        staff.enableExpansion();
        staff.setPosition(new Vector(Constants.SCREEN_WIDTH - Constants.STAFF_WIDTH, 100));
        staff.moveRight(1);
        assertEquals(Constants.SCREEN_WIDTH - Constants.STAFF_WIDTH, staff.getPosition().getX(), DELTA);
        assertEquals(100, staff.getPosition().getY(), DELTA);
    }

    @Test
    public void testMultipleMovementsRight() {
        staff.moveRight(1);
        staff.moveRight(1);
        staff.moveRight(1);
        assertEquals(100 + 3 * Constants.STAFF_SPEED, staff.getPosition().getX(), DELTA);
        assertEquals(100, staff.getPosition().getY(), DELTA);
    }

    @Test
    public void testMultipleMovementsLeft() {
        staff.moveRight(-1);
        staff.moveRight(-1);
        staff.moveRight(-1);
        assertEquals(100 - 3 * Constants.STAFF_SPEED, staff.getPosition().getX(), DELTA);
        assertEquals(100, staff.getPosition().getY(), DELTA);
    }

}

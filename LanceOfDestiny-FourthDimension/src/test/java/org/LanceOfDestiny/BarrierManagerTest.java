package org.LanceOfDestiny;

import org.LanceOfDestiny.domain.barriers.Barrier;
import org.LanceOfDestiny.domain.barriers.HollowBarrier;
import org.LanceOfDestiny.domain.managers.BarrierManager;
import org.LanceOfDestiny.domain.barriers.SimpleBarrier;
import org.LanceOfDestiny.domain.physics.Vector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BarrierManagerTest {
    private BarrierManager barrierManager;

    @BeforeEach
    public void setUp() {
        barrierManager = BarrierManager.getInstance();
        barrierManager.removeAllBarriers(); // Reset the state before each test
    }

    @Test
    public void testAddBarrier() {
        Barrier barrier = new SimpleBarrier(new Vector(100, 100));
        barrierManager.addBarrier(barrier);
        assertEquals(1, barrierManager.getBarriers().size());
    }

    @Test
    public void testRemoveBarrier() {
        Barrier barrier = new SimpleBarrier(new Vector(100, 100));
        barrierManager.addBarrier(barrier);
        barrierManager.removeBarrier(barrier);
        assertEquals(0, barrierManager.getBarriers().size());
    }

    @Test
    public void testGetBarrierByLocation() {
        Barrier barrier = new SimpleBarrier(new Vector(100, 100));
        barrierManager.addBarrier(barrier);
        Barrier foundBarrier = barrierManager.getBarrierByLocation(100, 100);
        assertNotNull(foundBarrier);
        assertEquals(barrier, foundBarrier);
    }

    @Test
    public void testValidateBarrierPlacement() {
        boolean isValid = barrierManager.validateBarrierPlacement(100, 100);
        assertTrue(isValid);
    }

    @Test
    public void testIsBarrierColliding() {
        Barrier barrier1 = new SimpleBarrier(new Vector(100, 100));
        barrierManager.addBarrier(barrier1);
        boolean isColliding = barrierManager.isBarrierColliding(110, 110);
        assertTrue(isColliding);
    }

    @Test
    public void testRepOk() {
        Barrier barrier = new SimpleBarrier(new Vector(100, 100));
        barrierManager.addBarrier(barrier);
        assertTrue(barrierManager.repOk());
    }
}


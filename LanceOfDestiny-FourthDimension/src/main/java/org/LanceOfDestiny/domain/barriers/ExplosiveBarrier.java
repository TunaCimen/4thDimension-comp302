package org.LanceOfDestiny.domain.barriers;

import java.util.Random;

public class ExplosiveBarrier extends Barrier{
    public static final double RADIUS = 15;
    public static final double MOVE_PROBABILITY = 0.2;

    public ExplosiveBarrier(int x, int y, BarrierTypes type) {
        super(x, y, type);
        if((new Random()).nextDouble() <= MOVE_PROBABILITY) isMoving = true;
    }
}

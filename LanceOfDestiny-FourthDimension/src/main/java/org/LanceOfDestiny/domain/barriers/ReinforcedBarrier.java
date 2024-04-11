package org.LanceOfDestiny.domain.barriers;

import java.util.Random;

public class ReinforcedBarrier extends Barrier{
    public static final double MOVE_PROBABILITY = 0.2;

    public ReinforcedBarrier(int x, int y, BarrierTypes type, int hitsRequired) {
        super(x, y, type, hitsRequired);
        if((new Random()).nextDouble() <= MOVE_PROBABILITY) isMoving = true;
    }
}

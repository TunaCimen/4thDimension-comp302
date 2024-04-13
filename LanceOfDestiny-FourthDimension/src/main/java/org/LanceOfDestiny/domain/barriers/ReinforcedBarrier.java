package org.LanceOfDestiny.domain.barriers;

import org.LanceOfDestiny.domain.physics.Vector;

import java.util.Random;

public class ReinforcedBarrier extends Barrier{
    public static final double MOVE_PROBABILITY = 0.2;

    public ReinforcedBarrier(Vector position, int hitsRequired) {
        super(position, BarrierTypes.REINFORCED, hitsRequired);
        if((new Random()).nextDouble() <= MOVE_PROBABILITY) isMoving = true;
    }
}

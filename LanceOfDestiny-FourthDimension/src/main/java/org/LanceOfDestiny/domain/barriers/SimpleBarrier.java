package org.LanceOfDestiny.domain.barriers;

import org.LanceOfDestiny.domain.physics.Vector;

import java.util.Random;

public class SimpleBarrier extends Barrier{
    public static final double MOVE_PROBABILITY = 0.2;

    public SimpleBarrier(Vector position) {
        super(position, BarrierTypes.SIMPLE);
        if((new Random()).nextDouble() <= MOVE_PROBABILITY) isMoving = true;
    }
}

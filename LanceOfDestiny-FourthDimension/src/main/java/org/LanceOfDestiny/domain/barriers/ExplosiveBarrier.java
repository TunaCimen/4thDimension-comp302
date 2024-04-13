package org.LanceOfDestiny.domain.barriers;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.physics.Vector;

import java.util.Random;

public class ExplosiveBarrier extends Barrier{
    public static final double RADIUS = Constants.EXPLOSIVE_RADIUS;
    public static final double MOVE_PROBABILITY = 0.2;

    public ExplosiveBarrier(Vector position) {
        super(position, BarrierTypes.EXPLOSIVE);
        if((new Random()).nextDouble() <= MOVE_PROBABILITY) isMoving = true;
    }
}

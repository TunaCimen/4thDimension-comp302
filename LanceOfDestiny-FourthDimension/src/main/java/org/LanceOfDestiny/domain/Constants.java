package org.LanceOfDestiny.domain;

import org.LanceOfDestiny.domain.physics.Vector;
import org.LanceOfDestiny.domain.player.FireBall;

public class Constants {
    public static final int SCREEN_WIDTH = 750; //can be changed later
    public static final int SCREEN_HEIGHT = 600; //can be changed later
    public static final int L = SCREEN_WIDTH / 10;

    public static final float UPDATE_RATE = 0.016f;

    public static final float STAFF_SPEED = L*UPDATE_RATE*2;


    public static final double STAFF_ANGULAR_SPEED = UPDATE_RATE*50*Math.PI/180; //20 is sooo sloow :(

    public static final int T = 20; // as in 20 px
    public static final int FIREBALL_RADIUS = 10; // as in 8 pixels
    public static final int STAFF_HEIGHT = T;
    public static final int STAFF_WIDTH = L;
    public static final int SPELL_DURATION = 30; // in seconds
    public static final int HEX_LENGTH = 3;
    public static final double HEX_SPEED = 5;  // value should change, 5 is placeholder
    public static final int CANON_HEIGHT = 10; // value should change, 10 is placeholder
    public static final int CANON_WIDTH = 10; // value should change, 10 is placeholder
    public static final Vector FIREBALL_POSITION = new Vector(SCREEN_WIDTH / 2, SCREEN_HEIGHT - 80); // initial position
    public static final Vector STAFF_POSITION = new Vector(SCREEN_WIDTH / 2 - (STAFF_WIDTH / 2),SCREEN_HEIGHT - 50 );
    public static final double EXPLOSIVE_RADIUS = 15;
}

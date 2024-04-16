package org.LanceOfDestiny.domain;

import org.LanceOfDestiny.domain.physics.Vector;

public class Constants {
    public static final float UPDATE_RATE = 0.016f;
    public static final int SCREEN_WIDTH = 750; //can be changed later
    public static final int SCREEN_HEIGHT = 600; //can be changed later
    public static final int L = SCREEN_WIDTH / 10;
    public static final float STAFF_SPEED = L * UPDATE_RATE * 4;
    public static final int STAFF_WIDTH = L;
    public static final Vector STAFF_POSITION = new Vector((double) SCREEN_WIDTH / 2 - ((double) STAFF_WIDTH / 2), SCREEN_HEIGHT-100);
    public static final int BARRIER_HEIGHT = L / 5;
    public static final int REWARD_BOX_HEIGHT = BARRIER_HEIGHT;
    public static final double STAFF_ANGULAR_SPEED = UPDATE_RATE * 50 * Math.PI / 180; //20 is sooo sloow :(
    public static final int T = 20; // as in 20 px
    public static final int FIREBALL_RADIUS = 10; // as in 8 pixels
    public static final int STAFF_HEIGHT = T;
    public static final int CIRCULAR_MOTION_RADIUS = 8;
    public static final int SPELL_DURATION = 30; // in seconds
    public static final int HEX_LENGTH = 3;
    public static final double HEX_SPEED = 5;  // value should change, 5 is placeholder
    public static final int CANON_HEIGHT = 10; // value should change, 10 is placeholder
    public static final int CANON_WIDTH = 10; // value should change, 10 is placeholder
    public static final Vector FIREBALL_POSITION = new Vector((double) SCREEN_WIDTH / 2, SCREEN_HEIGHT - 120); // initial position
    public static final int BARRIER_WIDTH = T;
    public static final int REWARD_BOX_WIDTH = BARRIER_WIDTH;
    public static final double EXPLOSIVE_RADIUS = 7;

    public static final int FIREBALL_SPEED = 5;
}

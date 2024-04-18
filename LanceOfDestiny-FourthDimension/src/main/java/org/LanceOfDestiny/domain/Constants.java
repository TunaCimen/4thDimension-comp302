package org.LanceOfDestiny.domain;

import org.LanceOfDestiny.domain.physics.Vector;

public class Constants {
    public static final float UPDATE_RATE = 0.016f;
    public static final int SCREEN_WIDTH = 1400; //can be changed later
    public static final int SCREEN_HEIGHT = 720; //can be changed later
    public static final int L = SCREEN_WIDTH / 10;
    public static final float STAFF_SPEED = L * UPDATE_RATE * 3;
    public static final int STAFF_WIDTH = L;
    public static final Vector STAFF_POSITION = new Vector((double) SCREEN_WIDTH / 2 - ((double) STAFF_WIDTH / 2), SCREEN_HEIGHT-120);
    public static final int BARRIER_HEIGHT = 20;
    public static final int BARRIER_WIDTH = L/5;
    public static final int REWARD_BOX_HEIGHT = BARRIER_HEIGHT;
    public static final double STAFF_ANGULAR_SPEED = UPDATE_RATE * 50 * Math.PI / 180; //20 is sooo sloow :(
    public static final int T = 20; // as in 20 px
    public static final int FIREBALL_RADIUS = 8; // as in 8 pixels
    public static final int STAFF_HEIGHT = T;
    public static final int CIRCULAR_MOTION_RADIUS = 8;
    public static final int SPELL_DURATION = 30; // in seconds
    public static final int HEX_RADIUS = 3;
    public static final double HEX_SPEED = 5;  // value should change, 5 is placeholder
    public static final double CANON_HEIGHT = STAFF_HEIGHT * 2;
    public static final int CANON_WIDTH = STAFF_WIDTH / 10;
    public static final Vector FIREBALL_POSITION = new Vector((double) SCREEN_WIDTH / 2, SCREEN_HEIGHT - 120); // initial position
    public static final int REWARD_BOX_WIDTH = BARRIER_WIDTH;
    public static final double EXPLOSIVE_RADIUS = 8;
    public static final int FIREBALL_SPEED = -8;
}

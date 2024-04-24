package org.LanceOfDestiny.domain;

import org.LanceOfDestiny.domain.physics.Vector;

public class Constants {
    // GENERAL
    public static final float UPDATE_RATE = 0.016f;
    public static final int SCREEN_WIDTH = 1400;
    public static final int SCREEN_HEIGHT = 720;
    public static final int L = SCREEN_WIDTH / 10;
    public static final int T = 20; // as in 20 px
    // MAGICAL STAFF
    public static final int STAFF_WIDTH = L;
    public static final int STAFF_HEIGHT = T;
    public static final float STAFF_SPEED = L * UPDATE_RATE * 3;
    public static final Vector STAFF_POSITION = new Vector((double) SCREEN_WIDTH / 2 - ((double) STAFF_WIDTH / 2), SCREEN_HEIGHT-120);
    public static final double STAFF_ANGULAR_SPEED = UPDATE_RATE * 50 * Math.PI / 180;
    // FIREBALL
    public static final Vector FIREBALL_POSITION = new Vector((double) SCREEN_WIDTH / 2, SCREEN_HEIGHT - 120);
    public static final int FIREBALL_RADIUS = 8; // as in 8 pixels
    public static final int FIREBALL_SPEED = -8;
    // BARRIERS
    public static final int BARRIER_HEIGHT = 20;
    public static final int BARRIER_WIDTH = L/5;
    public static final int EXPLOSIVE_RADIUS = 15;
    public static final int CIRCULAR_MOTION_RADIUS = 1;
    // SPELLS
    public static final int SPELL_DURATION = 30; // in seconds
    public static final int REWARD_BOX_HEIGHT = BARRIER_HEIGHT;
    public static final int REWARD_BOX_WIDTH = BARRIER_WIDTH;
    public static final int CANON_WIDTH = STAFF_WIDTH / 10;
    public static final double CANON_HEIGHT = STAFF_HEIGHT * 2;
    public static final int HEX_RADIUS = 3;
    public static final double HEX_SPEED = 5;
    // the minimum required barriers
    public static final int MIN_SIMPLE = 75;
    public static final int MIN_REINFORCED = 10;
    public static final int MIN_EXPLOSIVE = 5;
    public static final int MIN_REWARDING = 10;
    // maximum barriers if required 7 rows of barriers
    public static final int MAX_SIMPLE = 125;
    public static final int MAX_REINFORCED = 28;
    public static final int MAX_EXPLOSIVE = 15;
    public static final int MAX_REWARDING = 20;
    // BARRIER POSITIONING
    public static final int BARRIER_X_OFFSET = 60;
    public static final int BARRIER_Y_OFFSET = 50;
}

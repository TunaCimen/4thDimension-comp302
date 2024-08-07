package org.LanceOfDestiny.domain;

import org.LanceOfDestiny.domain.physics.Vector;

public class Constants {
    // GENERAL
    public static final float UPDATE_RATE = 0.016f;
    public static final int SCREEN_WIDTH = 800;
    public static final int SCREEN_HEIGHT = 800;
    public static final int L = SCREEN_WIDTH / 5;
    public static final int T = 20;
    // MAGICAL STAFF
    public static final int STAFF_WIDTH = L;
    public static final int STAFF_HEIGHT = T;
    public static final float STAFF_SPEED = L * UPDATE_RATE * 4;
    public static final Vector STAFF_POSITION = new Vector((double) SCREEN_WIDTH / 2 - ((double) STAFF_WIDTH / 2), SCREEN_HEIGHT-180);
    public static final double STAFF_ANGULAR_SPEED = UPDATE_RATE * 50 * Math.PI / 180;
    // FIREBALL
    public static final Vector FIREBALL_POSITION = new Vector((double) SCREEN_WIDTH / 2, SCREEN_HEIGHT - 187);
    public static final int FIREBALL_RADIUS = 8;
    public static final int FIREBALL_SPEED = (int) (STAFF_SPEED/2);
    // BARRIERS
    public static final int BARRIER_HEIGHT = 20;
    public static final int BARRIER_WIDTH = L/5;
    public static final int EXPLOSIVE_RADIUS = 15;
    public static final int CIRCULAR_MOTION_RADIUS = 1;
    // SPELLS
    public static final int SPELL_DURATION = 30; // in seconds
    public static final int CURSE_DURATION = 15;
    public static final int REWARD_BOX_HEIGHT = BARRIER_HEIGHT;
    public static final int REWARD_BOX_WIDTH = BARRIER_WIDTH;
    public static final int CANON_WIDTH = STAFF_WIDTH / 10;
    public static final double CANON_HEIGHT = STAFF_HEIGHT * 2;
    public static final int HEX_RADIUS = 3;
    public static final double HEX_SPEED = 5;
    // BUILD MODE: BARRIER REQUIREMENTS
    public static final int MIN_SIMPLE = 30;
    public static final int MIN_REINFORCED = 10;
    public static final int MIN_EXPLOSIVE = 5;
    public static final int MIN_REWARDING = 10;
    public static final int MAX_SIMPLE = 60;
    public static final int MAX_REINFORCED = 20;
    public static final int MAX_EXPLOSIVE = 10;
    public static final int MAX_REWARDING = 20;
    // BARRIER POSITIONING
    public static final int BARRIER_X_OFFSET = 60;
    public static final int BARRIER_Y_OFFSET = 50;

    public static final int DEFAULT_CHANCES = 3;
}

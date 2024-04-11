package org.LanceOfDestiny.domain.player;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.GameObject;

public class MagicalStaff extends GameObject {

    private final int width = Constants.STAFF_WIDTH;
    private final int height = Constants.STAFF_HEIGHT;

    private double x;
    private double y;

    private boolean isCanonActivated = false;
    private boolean isExpanded = false;

    public MagicalStaff() {
        super();

    }



}

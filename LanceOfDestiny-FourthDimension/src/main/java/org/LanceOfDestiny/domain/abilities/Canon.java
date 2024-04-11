package org.LanceOfDestiny.domain.abilities;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.GameObject;

public class Canon extends GameObject {

    protected int x;
    protected int y;

    public static final int CANON_HEIGHT = Constants.CANON_HEIGHT;
    public static final int CANON_WIDTH = Constants.CANON_WIDTH;

    public boolean isActive;

    public Canon(int x, int y) {
        super();
        this.x = x;
        this.y = y;
        isActive = false;
    }

    @Override
    public void Update() {
        if(!isActive)
            return;
        // TODO
    }

    public Hex createHex(){
        return new Hex(x,y);
    }

    public void activateCanon(){
        isActive = true;
    }
    public void deactivateCanon(){
        isActive = false;
    }
}

package org.LanceOfDestiny.domain.spells;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.GameObject;
import org.LanceOfDestiny.domain.managers.ManagerHub;
import org.LanceOfDestiny.domain.physics.Vector;
import org.LanceOfDestiny.domain.player.MagicalStaff;

public class Canon extends GameObject {

    private Vector position;
    private MagicalStaff magicalStaff;

    public static final int CANON_HEIGHT = Constants.CANON_HEIGHT;
    public static final int CANON_WIDTH = Constants.CANON_WIDTH;

    public boolean isActive;

    public Canon(Vector position) {
        super();
        this.position = position;
        isActive = false;
        magicalStaff = ManagerHub.getInstance().getMagicalStaff();
    }

    @Override
    public void Update() {
        if(!isActive)
            return;
        setPosition(magicalStaff.getPosition());
    }

    public Hex createHex(){
        return new Hex(position);
    }

    public void activateCanon(){
        isActive = true;
    }

    public void deactivateCanon(){
        isActive = false;
    }
}
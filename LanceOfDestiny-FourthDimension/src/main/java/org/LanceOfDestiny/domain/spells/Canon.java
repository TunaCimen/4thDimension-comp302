package org.LanceOfDestiny.domain.spells;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.behaviours.GameObject;
import org.LanceOfDestiny.domain.managers.SessionManager;
import org.LanceOfDestiny.domain.physics.Vector;
import org.LanceOfDestiny.domain.player.MagicalStaff;
import org.LanceOfDestiny.domain.sprite.RectangleSprite;

import java.awt.*;

public class Canon extends GameObject {

    private MagicalStaff magicalStaff;

    public static final double CANON_HEIGHT = Constants.CANON_HEIGHT;
    public static final int CANON_WIDTH = Constants.CANON_WIDTH;

    public boolean isActive;

    public Canon(Vector position) {
        super();
        this.position = position;
        isActive = false;
        magicalStaff = SessionManager.getInstance().getMagicalStaff();
        this.sprite = new RectangleSprite(this, Color.DARK_GRAY, CANON_WIDTH, (int) CANON_HEIGHT);
        if(!isActive) getSprite().setVisible(false);
    }

    @Override
    public void update() {

    }


    public Hex createHex(){
        return new Hex(position);
    }

    public void activateCanon(){
        isActive = true;
        getSprite().setVisible(true);
    }

    public void deactivateCanon(){
        isActive = false;
        getSprite().setVisible(false);
    }
}

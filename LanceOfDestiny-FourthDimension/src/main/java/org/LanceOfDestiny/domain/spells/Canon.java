package org.LanceOfDestiny.domain.spells;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.behaviours.GameObject;
import org.LanceOfDestiny.domain.looper.LoopExecutor;
import org.LanceOfDestiny.domain.managers.SessionManager;
import org.LanceOfDestiny.domain.physics.Vector;
import org.LanceOfDestiny.domain.player.MagicalStaff;
import org.LanceOfDestiny.domain.sprite.RectangleSprite;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Canon extends GameObject {

    public static final double CANON_HEIGHT = Constants.CANON_HEIGHT;
    public static final int CANON_WIDTH = Constants.CANON_WIDTH;
    public final double period = 0.5;
    public boolean isShooting = false;
    public boolean isActive;
    private MagicalStaff magicalStaff;
    private SessionManager sessionManager;
    private LoopExecutor loopExecutor;
    private double finishTime;
    public Queue<Hex> hexes;

    public Canon(Vector position) {
        super();
        this.position = position;
        isActive = false;
        this.sprite = new RectangleSprite(this, Color.DARK_GRAY, CANON_WIDTH, (int) CANON_HEIGHT);
        if (!isActive) getSprite().setVisible(false);
        sessionManager = SessionManager.getInstance();
        loopExecutor = SessionManager.getInstance().getLoopExecutor();
        finishTime = 0;
        hexes = new LinkedList<>();
        createHexes();

    }

    @Override
    public void update() {
        super.update();
        if (!isActive)
            return;
        if (finishTime < loopExecutor.getSecondsPassed()) {
            shootHex();
        }
    }


    public void shootHex() {
        finishTime = (double) (sessionManager.getLoopExecutor().getSecondsPassed()) + period;
        var hex = hexes.poll();
        hexes.add(hex);
        hex.shoot();
    }

    public void createHexes(){
        for (int i = 0; i < Constants.SPELL_DURATION/period; i++) {
            hexes.add(new Hex(this));
        }
    }

    public void activateCanon() {
        isActive = true;
        getSprite().setVisible(true);
    }

    public void deactivateCanon() {
        isActive = false;
        getSprite().setVisible(false);
    }
}

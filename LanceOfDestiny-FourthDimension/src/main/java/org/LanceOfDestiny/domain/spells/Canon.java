package org.LanceOfDestiny.domain.spells;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.behaviours.GameObject;
import org.LanceOfDestiny.domain.looper.LoopExecutor;
import org.LanceOfDestiny.domain.managers.SessionManager;
import org.LanceOfDestiny.domain.physics.Vector;
import org.LanceOfDestiny.domain.sprite.RectangleSprite;

import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;

public class Canon extends GameObject {

    public static final double CANON_HEIGHT = Constants.CANON_HEIGHT;
    public static final int CANON_WIDTH = Constants.CANON_WIDTH;

    public final double period = 0.5;
    private final LoopExecutor loopExecutor;
    public boolean isActive = false;
    public boolean isLeft;
    public Queue<Hex> hexes;
    private double finishTime;

    public Canon(Vector position, boolean isLeft) {
        super();
        this.position = position;
        this.sprite = new RectangleSprite(this, Color.DARK_GRAY, CANON_WIDTH, (int) CANON_HEIGHT);
        this.isLeft = isLeft;
        if (!isActive) getSprite().setVisible(false);
        this.loopExecutor = SessionManager.getInstance().getLoopExecutor();
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
        finishTime = (double) (loopExecutor.getSecondsPassed()) + period;
        var hex = hexes.poll();
        hexes.add(hex);
        if (hex != null)
            hex.shoot();

    }

    public void createHexes() {
        for (int i = 0; i < Constants.SPELL_DURATION / period + 2; i++) {
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

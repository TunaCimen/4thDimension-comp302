package org.LanceOfDestiny.domain.spells;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.behaviours.GameObject;
import org.LanceOfDestiny.domain.looper.LoopExecutor;
import org.LanceOfDestiny.domain.managers.SessionManager;
import org.LanceOfDestiny.domain.physics.ColliderFactory;
import org.LanceOfDestiny.domain.physics.ColliderType;
import org.LanceOfDestiny.domain.physics.Vector;
import org.LanceOfDestiny.domain.sprite.ImageLibrary;
import org.LanceOfDestiny.domain.sprite.ImageOperations;
import org.LanceOfDestiny.domain.sprite.RectangleSprite;

import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;

public class Canon extends GameObject {

    public static final double HEIGHT = Constants.CANON_HEIGHT;
    public static final int WIDTH = Constants.CANON_WIDTH;

    public final double period = 0.2;
    private final LoopExecutor loopExecutor;
    public boolean isActive = false;
    public boolean isLeft;
    public Queue<Hex> hexes = new LinkedList<>();
    private double finishTime;

    public Canon(Vector position, boolean isLeft) {
        super();
        this.position = position;
        this.isLeft = isLeft;
        this.loopExecutor = SessionManager.getInstance().getLoopExecutor();
        finishTime = 0;
        createColliderAndSprite();
        createHexes();
    }

    private void createColliderAndSprite() {
        this.sprite = new RectangleSprite(this, new Color(0,0,0,0), WIDTH, (int) HEIGHT);
        this.collider = ColliderFactory.createRectangleCollider(this, Vector.getZeroVector(), ColliderType.STATIC, WIDTH, HEIGHT);
        collider.setEnabled(false);
        sprite.setVisible(false);
        this.sprite.setImage(ImageOperations.resizeImageToSprite(ImageLibrary.Canon.getImage(), this.sprite));
    }

    @Override
    public void update() {
        super.update();
        if (!isActive) return;
        if (finishTime < loopExecutor.getSecondsPassed()) {
            shootHex();
        }
    }

    public void shootHex() {
        finishTime = (double) (loopExecutor.getSecondsPassed()) + period;
        var hex = hexes.poll();
        hexes.add(hex);
        if (hex != null) hex.shoot();

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

package org.LanceOfDestiny.domain.managers;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.looper.GameLooper;
import org.LanceOfDestiny.domain.looper.LoopExecutor;
import org.LanceOfDestiny.domain.barriers.BarrierFactory;
import org.LanceOfDestiny.domain.barriers.BarrierTypes;
import org.LanceOfDestiny.domain.physics.Vector;
import org.LanceOfDestiny.domain.player.FireBall;
import org.LanceOfDestiny.domain.player.MagicalStaff;
import org.LanceOfDestiny.domain.player.Player;
import org.LanceOfDestiny.domain.sprite.ImageLibrary;
import org.LanceOfDestiny.domain.sprite.ImageOperations;
import org.LanceOfDestiny.ui.DrawCanvas;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class SessionManager {

    private static SessionManager instance;
    GameLooper gameLooper;
    Status currentMode;
    private MagicalStaff magicalStaff;
    private FireBall fireBall;
    private Player player;
    private LoopExecutor loopExecutor = new LoopExecutor();
    private DrawCanvas drawCanvas;
    BufferedImage image;
    private SessionBuilder builder;

    private SessionManager() {
        this.drawCanvas = new DrawCanvas();
        this.gameLooper = new GameLooper(drawCanvas);
        this.loopExecutor = new LoopExecutor();
        this.builder = new SessionBuilder(75, 10, 5, 10);
        currentMode = Status.EditMode;
        loopExecutor.setLooper(gameLooper);
    }

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void initializeSession() {
        fireBall = new FireBall();

        magicalStaff = new MagicalStaff();
        player = new Player();
        builder.buildBarriers();
        //initializeBarriers();

    }

    private void initializeBarriers() {
        for (int i = 20; i < Constants.SCREEN_WIDTH - 10; i += 30) {
            for (int j = 20; j < Constants.SCREEN_HEIGHT - 400; j += 30) {
                if (i % 20 == 0) {
                    BarrierFactory.createBarrier(new Vector(i, j), BarrierTypes.EXPLOSIVE);
                } else {
                    BarrierFactory.createBarrier(new Vector(i, j), BarrierTypes.REWARDING);
                }
            }
        }
    }

    public MagicalStaff getMagicalStaff() {
        return magicalStaff;
    }

    public FireBall getFireBall() {
        return fireBall;
    }

    public LoopExecutor getLoopExecutor() {
        return loopExecutor;
    }

    public DrawCanvas getDrawCanvas() {
        return drawCanvas;
    }

    public Player getPlayer() {
        return player;
    }

    public SessionBuilder getBuilder() {
        return builder;
    }
}

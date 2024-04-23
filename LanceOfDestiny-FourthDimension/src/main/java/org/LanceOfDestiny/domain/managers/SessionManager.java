package org.LanceOfDestiny.domain.managers;

import org.LanceOfDestiny.domain.looper.GameLooper;
import org.LanceOfDestiny.domain.looper.LoopExecutor;
import org.LanceOfDestiny.domain.player.FireBall;
import org.LanceOfDestiny.domain.player.MagicalStaff;
import org.LanceOfDestiny.domain.player.Player;
import org.LanceOfDestiny.ui.DrawCanvas;

import java.awt.image.BufferedImage;

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
        // todo detelete/replace later with a proper initialization
        this.builder = new SessionBuilder(0, 0, 0, 0);
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
        builder.initializeBarriers();
        initializePlayer();
        //initializeBarriers();

    }
    public void initializePlayer(){
        if (!(player == null)) return;
        player = new Player();
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

    public void setStatus(Status status) {
        currentMode = status;
    }

    public Status getStatus() {
        return currentMode;
    }

}

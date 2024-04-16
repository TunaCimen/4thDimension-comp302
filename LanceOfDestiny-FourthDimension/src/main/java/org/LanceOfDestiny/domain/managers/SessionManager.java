package org.LanceOfDestiny.domain.managers;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.Looper.GameLooper;
import org.LanceOfDestiny.domain.Looper.LoopExecutor;
import org.LanceOfDestiny.domain.barriers.BarrierFactory;
import org.LanceOfDestiny.domain.barriers.BarrierTypes;
import org.LanceOfDestiny.domain.physics.Vector;
import org.LanceOfDestiny.domain.player.FireBall;
import org.LanceOfDestiny.domain.player.MagicalStaff;
import org.LanceOfDestiny.domain.player.Player;
import org.LanceOfDestiny.ui.DrawCanvas;

public class SessionManager {

    private static SessionManager instance;
    GameLooper gameLooper;
    Status currentMode;
    private MagicalStaff magicalStaff;
    private FireBall fireBall;
    private Player player;
    private LoopExecutor loopExecutor = new LoopExecutor();
    private DrawCanvas drawCanvas;
    private SessionBuilder builder;

    private SessionManager() {
        this.drawCanvas = new DrawCanvas();
        this.gameLooper = new GameLooper(drawCanvas);
        this.loopExecutor = new LoopExecutor();
        currentMode = Status.EditMode;
        loopExecutor.setLooper(gameLooper);
        this.builder = new SessionBuilder(75,10,5,10);
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

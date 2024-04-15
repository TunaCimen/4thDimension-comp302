package org.LanceOfDestiny.domain.managers;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.Looper.GameLooper;
import org.LanceOfDestiny.domain.Looper.LoopExecutor;
import org.LanceOfDestiny.domain.barriers.BarrierFactory;
import org.LanceOfDestiny.domain.barriers.BarrierTypes;
import org.LanceOfDestiny.domain.physics.Vector;
import org.LanceOfDestiny.domain.player.FireBall;
import org.LanceOfDestiny.domain.player.MagicalStaff;
import org.LanceOfDestiny.ui.DrawCanvas;
import org.LanceOfDestiny.ui.GameViews.Status;

public class SessionManager {

    private static SessionManager instance;
    GameLooper gameLooper;
    private Status currentMode;
    private MagicalStaff magicalStaff;
    private FireBall fireBall;
    private LoopExecutor loopExecutor = new LoopExecutor();
    private DrawCanvas drawCanvas;

    private SessionManager() {
        drawCanvas = new DrawCanvas();
        gameLooper = new GameLooper(drawCanvas);
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
        //Generate FireBall
        fireBall = new FireBall(Constants.FIREBALL_POSITION, new Vector(0, 5));
        //Generate Magical Staff
        magicalStaff = new MagicalStaff(Constants.STAFF_POSITION);
        // Generate barriers
        for (int i = 10; i < Constants.SCREEN_WIDTH - 10; i += 30) {
            for (int j = 10; j < Constants.SCREEN_HEIGHT - 400; j += 30) {
                if (j == 190) {  // Check if it's the first row
                    BarrierFactory.createBarrier(new Vector(i, j), BarrierTypes.EXPLOSIVE);
                } else {
                    BarrierFactory.createBarrier(new Vector(i, j), BarrierTypes.REINFORCED);
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
}

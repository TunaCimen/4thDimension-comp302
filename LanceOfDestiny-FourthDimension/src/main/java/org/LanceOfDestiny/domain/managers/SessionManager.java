package org.LanceOfDestiny.domain.managers;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.events.Events;
import org.LanceOfDestiny.domain.looper.GameLooper;
import org.LanceOfDestiny.domain.looper.LoopExecutor;
import org.LanceOfDestiny.domain.player.FireBall;
import org.LanceOfDestiny.domain.player.MagicalStaff;
import org.LanceOfDestiny.domain.player.Player;
import org.LanceOfDestiny.domain.ymir.Ymir;
import org.LanceOfDestiny.ui.DrawCanvas;

import java.awt.image.BufferedImage;

public class SessionManager {

    private static SessionManager instance;
    GameLooper gameLooper;
    Status currentMode;
    private MagicalStaff magicalStaff;
    private FireBall fireBall;
    private Player player;
    private Ymir ymir;
    private LoopExecutor loopExecutor = new LoopExecutor();
    private DrawCanvas drawCanvas;
    BufferedImage image;
    private SessionBuilder builder;
    private GameMode gameMode;

    private SessionManager() {
        this.drawCanvas = new DrawCanvas();
        this.gameLooper = new GameLooper(drawCanvas);
        this.loopExecutor = new LoopExecutor();
        // todo detelete/replace later with a proper initialization
        this.builder = new SessionBuilder(0, 0, 0, 0);
        currentMode = Status.EditMode;
        loopExecutor.setLooper(gameLooper);
        subscribeEvents();

    }

    private void subscribeEvents() {
        Events.BuildDoneEvent.addRunnableListener(this::initializeBarriers);
       
        //should change, we only have single player mode now
        gameMode = GameMode.SINGLEPLAYER;


        Events.Reset.addRunnableListener(()->getPlayer().setChancesLeft(Constants.DEFAULT_CHANCES));
        Events.Reset.addRunnableListener(()->getLoopExecutor().setLoadedTime(0));
        Events.Reset.addRunnableListener(()->getLoopExecutor().setTimePassed(0));
        Events.LoadGame.addRunnableListener(()->getLoopExecutor().setTimePassed(0));
        Events.EndGame.addRunnableListener(()->getLoopExecutor().stop());
        Events.ResumeGame.addRunnableListener(()->getLoopExecutor().resume());
        Events.PauseGame.addRunnableListener(()->{
            getLoopExecutor().stop();
            setStatus(Status.PausedMode);
        });
        Events.StartGame.addRunnableListener(()->{
            if (!getLoopExecutor().isStarted()) {
                getLoopExecutor().start();
            } else {
                getLoopExecutor().resume();
            }
            setStatus(Status.RunningMode);
        });
    }

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    /**
     * Initializes the session by setting up key game elements.
     *
     * Requires: None
     * Modifies: this.fireBall, this.magicalStaff, this.player, this.ymir
     * Effects: Initializes the fireBall, magicalStaff, player, and ymir objects.
     *          Calls initializePlayer and initializeYmir to ensure player and ymir are set up.
     */
    public void initializeSession() {
        fireBall = new FireBall();
        magicalStaff = new MagicalStaff();
        initializePlayer();
        initializeYmir();
        //builder.initializeBarriers();

    }

    public void initializePlayer() {
        if (!(player == null)) return;
        player = new Player();
    }

    public void initializeYmir() {
        if (!(ymir == null)) return;
        ymir = new Ymir();
    }

    public void initializeBarriers() {
        builder.initializeBarriers();
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

    public Ymir getYmir() {
        return ymir;
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

    public void setTimePassed(int timePassed) {
        loopExecutor.setLoadedTime(timePassed);
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public enum GameMode {
        MULTIPLAYER, SINGLEPLAYER
    }
}

package org.LanceOfDestiny.domain.managers;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.events.Events;
import org.LanceOfDestiny.domain.looper.GameLooper;
import org.LanceOfDestiny.domain.looper.LoopExecutor;
import org.LanceOfDestiny.domain.network.NetworkBehavior;
import org.LanceOfDestiny.domain.player.FireBall;
import org.LanceOfDestiny.domain.player.MagicalStaff;
import org.LanceOfDestiny.domain.player.Player;
import org.LanceOfDestiny.domain.spells.CurseManager;
import org.LanceOfDestiny.domain.ymir.Ymir;
import org.LanceOfDestiny.ui.UIUtilities.DrawCanvas;

public class SessionManager {

    private static SessionManager instance;
    GameLooper gameLooper;
    Status currentMode;
    private MagicalStaff magicalStaff;
    private FireBall fireBall;
    private Player player;
    private Ymir ymir;
    private CurseManager curseManager;
    private LoopExecutor loopExecutor = new LoopExecutor();
    private DrawCanvas drawCanvas;
    private SessionBarrierBuilder barrierBuilder;
    private GameMode gameMode;

    private SessionManager() {
        this.drawCanvas = new DrawCanvas();
        this.gameLooper = new GameLooper(drawCanvas);
        this.loopExecutor = new LoopExecutor();
        // todo delete/replace later with a proper initialization
        this.barrierBuilder = new SessionBarrierBuilder();
        currentMode = Status.EditMode;
        loopExecutor.setLooper(gameLooper);
        subscribeEvents();
    }

    private void subscribeEvents() {
        Events.BuildDoneEvent.addRunnableListener(this::initializeBarriers);

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

        Events.MultiplayerSelected.addRunnableListener(NetworkBehavior::new);

    }

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void initializeSession() {
        System.out.println("Session initialized");
        fireBall = new FireBall();
        magicalStaff = new MagicalStaff();
        initializePlayer();
        initializeCurseManager();
        //builder.initializeBarriers();

    }

    private void initializeCurseManager() {
        curseManager = CurseManager.getInstance();
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
        barrierBuilder.initializeBarriers();
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

    public SessionBarrierBuilder getBarrierBuilder() {
        return barrierBuilder;
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

    public enum GameMode {
        MULTIPLAYER, SINGLEPLAYER
    }
}

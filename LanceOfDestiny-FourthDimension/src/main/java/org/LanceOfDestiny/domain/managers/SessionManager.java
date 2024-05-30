package org.LanceOfDestiny.domain.managers;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.events.Event;
import org.LanceOfDestiny.domain.looper.GameLooper;
import org.LanceOfDestiny.domain.looper.LoopExecutor;
import org.LanceOfDestiny.domain.looper.UILooper;
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
    UILooper uiLooper;
    Status currentMode;
    private MagicalStaff magicalStaff;
    private FireBall fireBall;
    private Player player;
    private Ymir ymir;
    private AudioManager audioManager;
    private CurseManager curseManager;
    private LoopExecutor loopExecutor;
    public LoopExecutor UILoopExecutor;
    private DrawCanvas drawCanvas;
    private SessionBarrierBuilder barrierBuilder;
    private GameMode gameMode;

    private SessionManager() {
        this.drawCanvas = new DrawCanvas();
        this.gameLooper = new GameLooper(drawCanvas);
        this.uiLooper = new UILooper(drawCanvas);
        this.loopExecutor = new LoopExecutor();
        this.UILoopExecutor = new LoopExecutor();
        this.audioManager = AudioManager.getInstance();
        UILoopExecutor.setLooper(uiLooper);
        UILoopExecutor.start();
        this.barrierBuilder = new SessionBarrierBuilder();
        currentMode = Status.EditMode;
        loopExecutor.setLooper(gameLooper);
        subscribeEvents();
    }

    private void subscribeEvents() {
        Event.BuildDoneEvent.addRunnableListener(this::initializeBarriers);
        Event.Reset.addRunnableListener(()->getPlayer().setChancesLeft(Constants.DEFAULT_CHANCES));
        Event.Reset.addRunnableListener(()->getLoopExecutor().setLoadedTime(0));
        Event.Reset.addRunnableListener(()->getLoopExecutor().setTimePassed(0));
        Event.LoadGame.addRunnableListener(()->getLoopExecutor().setTimePassed(0));
        Event.EndGame.addRunnableListener(()->getLoopExecutor().stop());
        Event.ResumeGame.addRunnableListener(()->getLoopExecutor().resume());
        Event.PauseGame.addRunnableListener(()->{
            getLoopExecutor().stop();
            setStatus(Status.PausedMode);
        });
        Event.StartGame.addRunnableListener(()->{
            if (!getLoopExecutor().isStarted()) {
                getLoopExecutor().start();
            } else {
                getLoopExecutor().restart();
            }
            setStatus(Status.RunningMode);
        });
        Event.MultiplayerSelected.addRunnableListener(()->{
            gameMode = GameMode.MULTIPLAYER;
        });
        Event.SingleplayerSelected.addRunnableListener(()->{
            gameMode = GameMode.SINGLEPLAYER;
            initializeYmir();
        });
        Event.MultiplayerSelected.addRunnableListener(NetworkBehavior::new);
        Event.ResetGameMode.addRunnableListener(()->{
            gameMode = GameMode.SINGLEPLAYER;
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
        System.out.println("Session initialized");
        fireBall = new FireBall();
        magicalStaff = new MagicalStaff();
        initializePlayer();
        initializeCurseManager();
        new GameStateHandler();
    }

    private void initializeCurseManager() {
        curseManager = CurseManager.getInstance();
    }

    public void initializePlayer() {
        if (!(player == null)) return;
        player = new Player();
    }

    public void initializeYmir() {
        System.out.println("Ymir initialized");
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
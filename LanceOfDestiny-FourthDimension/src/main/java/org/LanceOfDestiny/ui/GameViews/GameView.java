package org.LanceOfDestiny.ui.GameViews;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.events.Events;
import org.LanceOfDestiny.domain.managers.InputManager;
import org.LanceOfDestiny.domain.managers.ScoreManager;
import org.LanceOfDestiny.domain.managers.SessionManager;
import org.LanceOfDestiny.domain.managers.Status;
import org.LanceOfDestiny.domain.sprite.ImageLibrary;
import org.LanceOfDestiny.ui.*;
import org.LanceOfDestiny.ui.Window;

import javax.swing.*;
import java.awt.*;

public class GameView extends JFrame implements Window {
    private static GameView instance = null;
    private SessionManager sessionManager;

    JButton buttonPlay;
    JButton buttonPause;
    JButton buttonBuild;

    private GameView(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
        initializeComponents();
    }

    public static GameView getInstance(SessionManager sessionManager) {
        if (instance == null) {
            instance = new GameView(sessionManager);
        } else {
            instance.setSessionManager(sessionManager);
        }
        return instance;
    }

    private void setSessionManager(SessionManager newSessionManager) {
        this.sessionManager = newSessionManager;
        reinitializeUI();
    }

    private void initializeComponents() {
        setFocusable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
        setDefaultLookAndFeelDecorated(true);
        setResizable(false);
    }

    public void reinitializeUI() {
        getContentPane().removeAll();
        createAndShowUI();
        validate();
        repaint();
    }

    public void setPlayButtonVisibility(boolean isVisible) {
        buttonPlay.setEnabled(isVisible);

    }
    public void setPauseButtonVisibility(boolean isVisible) {
        buttonPause.setEnabled(isVisible);

    }
    public void setBuildButtonVisibility(boolean isVisible) {
        buttonBuild.setEnabled(isVisible);

    }

    public void startGame() {
        buttonPlay.setEnabled(false);
        buttonPause.setEnabled(true);
        buttonBuild.setEnabled(true);
        sessionManager.setStatus(Status.RunningMode);
        Events.ResumeGame.invoke();
        sessionManager.getLoopExecutor().start();
    }

    @Override
    public void createAndShowUI() {
        addKeyListener(InputManager.getInstance());
        Events.ResumeGame.addRunnableListener(this::requestFocusInWindow);
        //we need to initialize the player before initializing the other objects to canvas to render
        sessionManager.initializePlayer();


        JPanel controlPanel = new JPanel(new GridLayout(1, 5, 20, 20));
        controlPanel.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, 50));
        add(controlPanel, BorderLayout.NORTH);
        add(sessionManager.getDrawCanvas(), BorderLayout.CENTER);

         buttonPlay = new JButton("Start Game");
         buttonPause = new JButton("Pause Game");
         buttonBuild = new JButton("Build Game");

        // Disable buttons triggering with space key
        buttonPlay.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("SPACE"), "none");
        buttonPause.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("SPACE"), "none");
        buttonBuild.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("SPACE"), "none");
        buttonPause.setEnabled(false);

        SpellInventory spellInventory = new SpellInventory();

        ImageIcon healthBar = new ImageIcon(ImageLibrary.Heart.getImage());
        HealthBar healthBarDisplay = new HealthBar(3);
        JLabel chanceBar = new JLabel( "<3:  "+ String.valueOf(sessionManager.getPlayer().getChancesLeft()));
        chanceBar.setIcon(healthBar);

        JLabel scoreBar = new JLabel( "Score:  " + String.valueOf(ScoreManager.getInstance().getScore()));
        scoreBar.setFont(new Font("Impact", Font.BOLD, 24));
        scoreBar.setPreferredSize(new Dimension(100,30));

        Events.UpdateScore.addListener(e-> scoreBar.setText("Score:  " + String.valueOf(ScoreManager.getInstance().getScore())));
        Events.UpdateChance.addListener(e-> chanceBar.setText("<3:  " + String.valueOf(sessionManager.getPlayer().getChancesLeft())));

        controlPanel.add(buttonPlay);
        controlPanel.add(buttonPause);
        controlPanel.add(buttonBuild);
        controlPanel.add(healthBarDisplay);
        controlPanel.add(spellInventory);
        controlPanel.add(scoreBar);





        buttonPlay.addActionListener(e -> {startGame();});
        buttonPause.addActionListener(e -> {
            sessionManager.getLoopExecutor().stop();
            buttonPlay.setEnabled(true);
            WindowManager.getInstance().showWindow(Windows.PauseView);
            System.out.println(sessionManager.getLoopExecutor().getSecondsPassed());
            sessionManager.setStatus(Status.PausedMode);
            Events.PauseGame.invoke();
        });
        buttonBuild.addActionListener(e -> {
            buttonPause.setEnabled(true);
            buttonPlay.setEnabled(true);
            WindowManager.getInstance().showWindow(Windows.BuildViewMini);
            System.out.println(sessionManager.getLoopExecutor().getSecondsPassed());
            Events.PauseGame.invoke();
        });

        setVisible(true);
    }
}

package org.LanceOfDestiny.ui.GameViews;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.events.Events;
import org.LanceOfDestiny.domain.managers.InputManager;
import org.LanceOfDestiny.domain.managers.ScoreManager;
import org.LanceOfDestiny.domain.managers.SessionManager;
import org.LanceOfDestiny.domain.sprite.ImageLibrary;
import org.LanceOfDestiny.ui.*;
import org.LanceOfDestiny.ui.Window;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class GameView extends JFrame implements Window {

    private final SessionManager sessionManager;

    public GameView(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public void createAndShowUI() {

        setFocusable(true);
        addKeyListener(InputManager.getInstance()); //Add Input Manager.
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
        setDefaultLookAndFeelDecorated(true);
        setResizable(false);
        Events.ResumeGame.addRunnableListener(this::requestFocusInWindow);

        //Buttons
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(1,5,20,20));


        controlPanel.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, 50));
        JButton buttonPlay = new JButton("Start Game");
        JButton buttonPause = new JButton("Pause Game");
        JButton buttonBuild = new JButton("Build Game");
        JButton buttonResume = new JButton("Resume Game");

        //butonlar space tusuna basildiginda calismasin diye
        buttonPlay.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "none");
        buttonPause.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "none");
        buttonBuild.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "none");
        buttonResume.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "none");

        ImageIcon healthBar = new ImageIcon(ImageLibrary.Heart.getImage());
        buttonPause.setEnabled(false);

        add(controlPanel, BorderLayout.NORTH);
        add(sessionManager.getDrawCanvas(), BorderLayout.CENTER);
        sessionManager.initializePlayer();

        HealthBar healthBarDisplay = new HealthBar(3);
        SpellInventory spellInventory = new SpellInventory();

        JLabel chanceBar = new JLabel( "<3:  "+ String.valueOf(sessionManager.getPlayer().getChancesLeft()));
        chanceBar.setIcon(healthBar);

        Events.UpdateChance.addListener(e-> chanceBar.setText("<3:  " + String.valueOf(sessionManager.getPlayer().getChancesLeft())));
        JLabel scoreBar = new JLabel( "Score:  " + String.valueOf(ScoreManager.getInstance().getScore()));
        scoreBar.setPreferredSize(new Dimension(100,30));
        Events.UpdateScore.addListener(e-> scoreBar.setText("Score:  " + String.valueOf(ScoreManager.getInstance().getScore())));

        controlPanel.add(spellInventory);
        controlPanel.add(healthBarDisplay);
        controlPanel.add(scoreBar);
        controlPanel.add(buttonPlay);
        controlPanel.add(buttonPause);
        controlPanel.add(buttonBuild);

        //pausePanel.add(chanceBar);

        addKeyListener(InputManager.getInstance());
        buttonPlay.addActionListener(e -> {
            buttonPlay.setVisible(false);
            buttonPause.setEnabled(true);
            buttonBuild.setEnabled(false);

            add(sessionManager.getDrawCanvas(), BorderLayout.CENTER);
            //Initialize the Game Objects
            sessionManager.initializeSession();


            sessionManager.getLoopExecutor().start();
        });
        buttonPause.addActionListener(e -> {
            WindowManager.getInstance().showWindow(Windows.PauseView);
            System.out.println(sessionManager.getLoopExecutor().getSecondsPassed());
            Events.PauseGame.invoke();
        });
        buttonBuild.addActionListener(e -> {
            buttonPause.setEnabled(false);
            buttonPlay.setEnabled(true);
            WindowManager.getInstance().showWindow(Windows.BuildViewMini);
            System.out.println(sessionManager.getLoopExecutor().getSecondsPassed());
            Events.PauseGame.invoke();
        });



        //Add panels to frame.

        //Show the frame.
        setVisible(true);

    }


}

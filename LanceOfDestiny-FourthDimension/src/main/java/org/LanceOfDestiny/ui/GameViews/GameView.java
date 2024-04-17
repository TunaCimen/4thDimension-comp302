package org.LanceOfDestiny.ui.GameViews;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.events.Events;
import org.LanceOfDestiny.domain.managers.InputManager;
import org.LanceOfDestiny.domain.managers.ScoreManager;
import org.LanceOfDestiny.domain.managers.SessionManager;
import org.LanceOfDestiny.domain.sprite.ImageLibrary;
import org.LanceOfDestiny.ui.HealthBar;
import org.LanceOfDestiny.ui.Window;
import org.LanceOfDestiny.ui.WindowManager;
import org.LanceOfDestiny.ui.Windows;

import javax.swing.*;
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
        JPanel pausePanel = new JPanel();


        pausePanel.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, 50));
        JButton button = new JButton("Start Game");
        JButton buttonPause = new JButton("Pause Game");
        JButton buttonResume = new JButton("Resume Game");
        ImageIcon healthBar = new ImageIcon(ImageLibrary.Heart.getImage());
        buttonPause.setVisible(false);
        buttonResume.setVisible(false);
        pausePanel.add(button);
        pausePanel.add(buttonPause);
        pausePanel.add(buttonResume);
        addKeyListener(InputManager.getInstance());
        button.addActionListener(e -> {
            buttonPause.setVisible(true);
            buttonResume.setVisible(true);
            button.setVisible(false);
            sessionManager.getLoopExecutor().start();
        });
        buttonPause.addActionListener(e -> {
            WindowManager.getInstance().showWindow(Windows.PauseView);
            System.out.println(sessionManager.getLoopExecutor().getSecondsPassed());
            Events.PauseGame.invoke();
        });
        buttonResume.addActionListener(e -> {
            Events.ResumeGame.invoke();
            requestFocusInWindow(true);
        });

        //Add panels to frame.
        add(sessionManager.getDrawCanvas(), BorderLayout.CENTER);
        add(pausePanel, BorderLayout.NORTH);

        //Initialize the Game Objects
        sessionManager.initializeSession();
        HealthBar healthBarDisplay = new HealthBar(3);

        JLabel chanceBar = new JLabel( "<3:  "+ String.valueOf(sessionManager.getPlayer().getChancesLeft()));
        chanceBar.setIcon(healthBar);
        Events.UpdateChance.addListener(e-> chanceBar.setText("<3:  " + String.valueOf(sessionManager.getPlayer().getChancesLeft())));
        JLabel scoreBar = new JLabel( "Score:  " + String.valueOf(ScoreManager.getInstance().getScore()));
        Events.UpdateScore.addListener(e-> scoreBar.setText("Score:  " + String.valueOf(ScoreManager.getInstance().getScore())));
        pausePanel.add(scoreBar);
        //pausePanel.add(chanceBar);
        pausePanel.add(healthBarDisplay);
        //Show the frame.
        setVisible(true);

    }
}

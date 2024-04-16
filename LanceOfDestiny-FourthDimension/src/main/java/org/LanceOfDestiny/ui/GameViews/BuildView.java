package org.LanceOfDestiny.ui.GameViews;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.EventSystem.Events;
import org.LanceOfDestiny.domain.managers.InputManager;
import org.LanceOfDestiny.domain.managers.SessionManager;
import org.LanceOfDestiny.ui.Window;

import javax.swing.*;
import java.awt.*;


public class BuildView extends JFrame implements Window {

    private final SessionManager sessionManager;

    public BuildView(SessionManager sessionManager) {
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

        //Buttons
        JPanel pausePanel = new JPanel();


        pausePanel.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, 50));
        JButton button = new JButton("Start Game");
        JButton buttonPause = new JButton("Pause Game");
        JButton buttonResume = new JButton("Resume Game");
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
        JLabel chanceBar = new JLabel( "<3  "+ String.valueOf(sessionManager.getPlayer().getChances()));
        Events.UpdateChance.addListener(e-> chanceBar.setText("<3  " + String.valueOf(sessionManager.getPlayer().getChances())));
        pausePanel.add(chanceBar);
        //Show the frame.
        setVisible(true);

    }
}

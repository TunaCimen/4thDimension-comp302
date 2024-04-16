package org.LanceOfDestiny.ui.GameViews;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.EventSystem.Events;
import org.LanceOfDestiny.domain.managers.InputManager;
import org.LanceOfDestiny.domain.managers.SessionManager;
import org.LanceOfDestiny.ui.Window;

import javax.swing.*;
import java.awt.*;

public class GameView extends JFrame implements Window {

    private final SessionManager sessionManager;

    private JTextField textFieldBarrierSimle;
    private JTextField textFieldBarrierReinforced;
    private JTextField textFieldBarrierExplosive;
    private JTextField textFieldBarrierRewarding;

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

        //build panel
        JPanel userInputPanel = new JPanel(new GridLayout(5, 2)); // 5 rows, 2 columns
        userInputPanel.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, 100));

        // Barrier 1
        JLabel labelBarrier1 = new JLabel("Simple Barrier :");
        textFieldBarrierSimle = new JTextField(10);
        userInputPanel.add(labelBarrier1);
        userInputPanel.add(textFieldBarrierSimle);

        // Barrier 2
        JLabel labelBarrier2 = new JLabel("Reinforced Barrier ");
        textFieldBarrierReinforced = new JTextField(10);
        userInputPanel.add(labelBarrier2);
        userInputPanel.add(textFieldBarrierReinforced);

        // Barrier 3
        JLabel labelBarrier3 = new JLabel("Explosive Barrier :");
        textFieldBarrierExplosive = new JTextField(10);
        userInputPanel.add(labelBarrier3);
        userInputPanel.add(textFieldBarrierExplosive);

        // Barrier 4
        JLabel labelBarrier4 = new JLabel("Rewarding Barrier :");
        textFieldBarrierRewarding = new JTextField(10);
        userInputPanel.add(labelBarrier4);
        userInputPanel.add(textFieldBarrierRewarding);

        // Play Game Button
        JButton buttonPlay = new JButton("Play Game");
        userInputPanel.add(buttonPlay);
        buttonPlay.addActionListener(e -> {

            int numOfSimple = Integer.parseInt(textFieldBarrierSimle.getText());
            int numOfReinforced = Integer.parseInt(textFieldBarrierReinforced.getText());
            int numOfExplosive = Integer.parseInt(textFieldBarrierExplosive.getText());
            int numOfRewarding = Integer.parseInt(textFieldBarrierRewarding.getText());

            this.sessionManager.getBuilder().setNumOfSimple(numOfSimple);
            this.sessionManager.getBuilder().setNumOfExplosive(numOfExplosive);
            this.sessionManager.getBuilder().setNumOfRewarding(numOfRewarding);
            this.sessionManager.getBuilder().setNumOfReinforced(numOfReinforced);

            userInputPanel.setVisible(false);
            sessionManager.initializeSession();

        });

        add(userInputPanel, BorderLayout.SOUTH);
        //Add panels to frame.
        add(sessionManager.getDrawCanvas(), BorderLayout.CENTER);
        add(pausePanel, BorderLayout.NORTH);

        //Initialize the Game Objects
        //sessionManager.initializeSession();
        JLabel chanceBar = new JLabel( "❤️"+ String.valueOf(sessionManager.getPlayer().getChances()));
        Events.UpdateChance.addListener(e-> chanceBar.setText("<3  " + String.valueOf(sessionManager.getPlayer().getChances())));
        pausePanel.add(chanceBar);
        //Show the frame.
        setVisible(true);

    }
}

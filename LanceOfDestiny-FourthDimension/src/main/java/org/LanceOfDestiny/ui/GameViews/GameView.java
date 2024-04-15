package org.LanceOfDestiny.ui.GameViews;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.EventSystem.Events;
import org.LanceOfDestiny.domain.managers.InputManager;
import org.LanceOfDestiny.domain.managers.SessionManager;
import org.LanceOfDestiny.domain.physics.Vector;
import org.LanceOfDestiny.ui.Window;

import javax.swing.*;
import java.awt.*;

public class GameView extends JFrame implements Window {

    private final SessionManager sessionManager;

    public GameView(SessionManager sessionManager){
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
        JPanel pausePanel =  new JPanel();
        pausePanel.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH,50));
        JButton button = new JButton("Start Game");
        JButton buttonPause = new JButton("Pause Game");
        JButton buttonResume = new JButton("Resume Game");
        buttonPause.setVisible(false);
        buttonResume.setVisible(false);
        pausePanel.add(button);
        pausePanel.add(buttonPause);
        pausePanel.add(buttonResume);
        addKeyListener(InputManager.getInstance());
        button.addActionListener(e->{
            buttonPause.setVisible(true);
            buttonResume.setVisible(true);
            button.setVisible(false);
            sessionManager.loopExecutor.start();
        });
        buttonPause.addActionListener(e->{
            Events.PauseGame.invoke();});
        buttonResume.addActionListener(e->{
            Events.ResumeGame.invoke();
        });

        //Add panels to frame.
        add(sessionManager.drawCanvas,BorderLayout.CENTER);
        add(pausePanel,BorderLayout.NORTH);

        //Initialize the Game Objects
        sessionManager.initializeSession();

        //Show the frame.
        setVisible(true);

    }
}

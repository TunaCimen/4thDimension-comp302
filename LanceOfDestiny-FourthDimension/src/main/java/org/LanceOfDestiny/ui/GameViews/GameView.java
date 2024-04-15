package org.LanceOfDestiny.ui.GameViews;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.managers.InputManager;
import org.LanceOfDestiny.domain.managers.SessionManager;
import org.LanceOfDestiny.domain.physics.Vector;
import org.LanceOfDestiny.ui.DrawCanvas;
import org.LanceOfDestiny.ui.Window;

import javax.swing.*;
import javax.swing.border.Border;
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
        JPanel buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, 50));
        JButton button = new JButton("Start Game");
        buttonPanel.add(button);
        addKeyListener(InputManager.getInstance());
        button.addActionListener(e->{
            buttonPanel.setVisible(false);
            sessionManager.getMagicalStaff().setPosition(Constants.STAFF_POSITION.add(new Vector(0,45)));

            sessionManager.getLoopExecutor().start();
        });

        //Add panels to frame.
        add(sessionManager.getDrawCanvas(),BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        //Initialize the Game Objects
        sessionManager.initializeSession();

        //Show the frame.
        setVisible(true);

    }
}

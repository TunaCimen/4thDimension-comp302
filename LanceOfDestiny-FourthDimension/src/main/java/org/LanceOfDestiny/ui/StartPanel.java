package org.LanceOfDestiny.ui;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.events.Events;

import javax.swing.*;
import java.awt.*;

public class StartPanel extends JPanel {

    final Dimension maximumSizeButton = new Dimension(150, 45);
    public StartPanel() {
        setLayout(new BorderLayout());
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(Box.createRigidArea(new Dimension(0, Constants.SCREEN_HEIGHT / 2 - 100)));
        add(UILibrary.createLabel("Lance Of Destiny"));
        add(Box.createRigidArea(new Dimension(0, 15)));
        add(createNewGameButton());
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(UILibrary.createButton("LOAD", ()->{
            Events.Load.invoke();
            WindowManager.getInstance().showWindow(Windows.LoadView);
        }));
    }

    private JButton createNewGameButton() {
        JButton newGameButton = new JButton("NEW GAME");
        newGameButton.setFont(new Font("Monospaced", Font.BOLD, 15));
        newGameButton.setMaximumSize(maximumSizeButton);
        newGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        newGameButton.addActionListener(e -> {
            Events.Reset.invoke();
        });
        return newGameButton;
    }


    }

package org.LanceOfDestiny.ui.UIElements;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.events.Events;
import org.LanceOfDestiny.ui.UIElements.UILibrary;

import javax.swing.*;
import java.awt.*;

public class InitPanel extends JPanel {

    private JButton multiplayerButton;
    private JButton singlePlayerButton;

    public InitPanel(){
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        multiplayerButton = new JButton("Multi Player");
        singlePlayerButton = new JButton("Single Player");
        multiplayerButton.setFont(new Font("Monospaced", Font.BOLD, 15));
        singlePlayerButton.setFont(new Font("Monospaced", Font.BOLD, 15));
        multiplayerButton.setMaximumSize(new Dimension(150,45));
        singlePlayerButton.setMaximumSize(new Dimension(150,45));
        multiplayerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        singlePlayerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(Box.createRigidArea(new Dimension(Constants.SCREEN_WIDTH,(Constants.SCREEN_HEIGHT /2)-100)));
        add(UILibrary.createLabel("LANCE OF DESTINY"));
        add(singlePlayerButton);
        add(multiplayerButton);

        multiplayerButton.addActionListener(e -> {
            Events.MultiplayerSelected.invoke();
        });

        singlePlayerButton.addActionListener(e -> {
            Events.SingleplayerSelected.invoke();
        });


    }

}

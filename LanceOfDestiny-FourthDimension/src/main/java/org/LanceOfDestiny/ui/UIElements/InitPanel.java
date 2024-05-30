package org.LanceOfDestiny.ui.UIElements;

import org.LanceOfDestiny.domain.Constants;

import org.LanceOfDestiny.domain.events.Event;
import org.LanceOfDestiny.domain.sprite.ImageLibrary;
import org.LanceOfDestiny.ui.UIElements.UILibrary;
import org.LanceOfDestiny.ui.UIUtilities.BackgroundJPanel;


import javax.swing.*;
import java.awt.*;

public class InitPanel extends BackgroundJPanel {

    private JButton multiplayerButton;
    private JButton singlePlayerButton;

    public InitPanel(){
        setBackground(ImageLibrary.Background.getImage());
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

            Event.MultiplayerSelected.invoke();
        });

        singlePlayerButton.addActionListener(e -> {
            Event.SingleplayerSelected.invoke();
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(ImageLibrary.Background.getImage(), 0,0,this.getWidth(),this.getHeight(),this);
    }
}

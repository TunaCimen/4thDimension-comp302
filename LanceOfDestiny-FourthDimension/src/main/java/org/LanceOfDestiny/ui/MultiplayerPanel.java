package org.LanceOfDestiny.ui;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.events.Events;

import javax.swing.*;
import java.awt.*;

public class MultiplayerPanel extends JPanel {

    private JButton hostButton;
    private JButton joinButton;

    private JTextField ipField;

    public MultiplayerPanel(){
        ipField = new JTextField();
        ipField.setAlignmentX(Component.CENTER_ALIGNMENT);
        ipField.setMaximumSize(new Dimension(150,45));
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        hostButton = UILibrary.createButton("Host Game",Events.Reset::invoke);
        joinButton = UILibrary.createButton("Join Game", this::showHostPrompt);
        add(Box.createRigidArea(new Dimension(Constants.SCREEN_WIDTH,Constants.SCREEN_HEIGHT/2-100)));
        add(hostButton);
        add(joinButton);
    }

    public void showHostPrompt(){
        remove(hostButton);
        remove(joinButton);
        add(ipField);
        add(joinButton);
        joinButton.removeActionListener(joinButton.getActionListeners()[0]);
        joinButton.addActionListener(e->Events.TryJoiningSession.invoke(ipField));
        revalidate();
        repaint();
    }
}

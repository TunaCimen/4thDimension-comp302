package org.LanceOfDestiny.ui.UIElements;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.events.Events;
import org.LanceOfDestiny.ui.UIElements.UILibrary;

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
        hostButton = UILibrary.createButton("Host Game",this::showHostPrompt);
        joinButton = UILibrary.createButton("Join Game", this::showJoinPrompt);
        add(Box.createRigidArea(new Dimension(Constants.SCREEN_WIDTH,Constants.SCREEN_HEIGHT/2-100)));
        add(hostButton);
        add(joinButton);
    }

    public void showJoinPrompt(){
        remove(hostButton);
        remove(joinButton);
        add(ipField);
        add(joinButton);
        joinButton.removeActionListener(joinButton.getActionListeners()[0]);
        joinButton.addActionListener(e->{
            Events.TryJoiningSession.invoke(ipField.getText());
        });
        revalidate();
        repaint();
    }

    public void showHostPrompt(){
        System.out.println("Shown host prompt");
        remove(hostButton);
        remove(joinButton);
        add(hostButton);
        hostButton.removeActionListener(joinButton.getActionListeners()[0]);
        hostButton.addActionListener(e->{
            Events.TryHostingSession.invoke();
            Events.Reset.invoke();
            System.out.println("Host Clickeeeeeeeed");
        });

        revalidate();
        repaint();
    }


}

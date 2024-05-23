package org.LanceOfDestiny.ui.UIElements;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.events.Event;

import javax.swing.*;
import java.awt.*;

public class MultiplayerPanel extends JPanel {

    private JButton hostButton;
    private JButton joinButton;
    private JTextField ipField;

    private JButton backButton;

    public MultiplayerPanel(){
        ipField = new JTextField();
        ipField.setAlignmentX(Component.CENTER_ALIGNMENT);
        ipField.setMaximumSize(new Dimension(150,45));
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        hostButton = UILibrary.createButton("Host Game",this::showHostPrompt);
        joinButton = UILibrary.createButton("Join Game", this::showJoinPrompt);
        backButton = UILibrary.createButton("Back",()->{
            Event.MultiplayerSelected.invoke();
            showMainPrompt();
        });

        showMainPrompt();
    }

    public void showMainPrompt(){
        removeAll();
        add(Box.createRigidArea(new Dimension(Constants.SCREEN_WIDTH,Constants.SCREEN_HEIGHT/2-100)));
        add(hostButton);
        add(joinButton);
        add(backButton);
        backButton.removeActionListener(backButton.getActionListeners()[0]);
        backButton.addActionListener(Event.ShowInitGame::invoke);
        revalidate();
        repaint();
    }

    public void showJoinPrompt(){
        removeAll();
        add(Box.createRigidArea(new Dimension(Constants.SCREEN_WIDTH,Constants.SCREEN_HEIGHT/2-100)));
        remove(hostButton);
        remove(joinButton);
        add(ipField);
        add(joinButton);
        add(backButton);
        backButton.removeActionListener(backButton.getActionListeners()[0]);
        backButton.addActionListener((e)->{
            Event.MultiplayerSelected.invoke();
            showMainPrompt();
        });
        joinButton.removeActionListener(joinButton.getActionListeners()[0]);
        joinButton.addActionListener(e->{
            Event.TryJoiningSession.invoke(ipField.getText());
            showMainPrompt();
        });
        revalidate();
        repaint();
    }

    public void showHostPrompt(){
        removeAll();
        System.out.println("Shown host prompt");
        add(Box.createRigidArea(new Dimension(Constants.SCREEN_WIDTH,Constants.SCREEN_HEIGHT/2-100)));
        add(hostButton);
        add(backButton);
        backButton.removeActionListener(backButton.getActionListeners()[0]);
        backButton.addActionListener((e)->{
            Event.MultiplayerSelected.invoke();
            showMainPrompt();
        });
        hostButton.removeActionListener(joinButton.getActionListeners()[0]);
        hostButton.addActionListener(e->{
            Event.Reset.invoke();
            System.out.println("Host Clickeeeeeeeed");
        });

        revalidate();
        repaint();
    }


}

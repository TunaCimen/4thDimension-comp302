package org.LanceOfDestiny.ui.UIElements;

import org.LanceOfDestiny.domain.Constants;

import org.LanceOfDestiny.domain.events.Event;
import org.LanceOfDestiny.domain.network.NetworkManager;
import org.LanceOfDestiny.domain.sprite.ImageLibrary;
import org.LanceOfDestiny.ui.UIElements.UILibrary;
import org.LanceOfDestiny.ui.UIUtilities.BackgroundJPanel;


import javax.swing.*;
import java.awt.*;

public class MultiplayerPanel extends BackgroundJPanel {

    private JButton hostButton;
    private JButton joinButton;
    private JTextField ipField;

    private JButton backButton;

    public MultiplayerPanel(){
        setBackground(ImageLibrary.Background.getImage());
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
        joinButton.removeActionListener(joinButton.getActionListeners()[0]);
        hostButton.removeActionListener(hostButton.getActionListeners()[0]);
        add(Box.createRigidArea(new Dimension(Constants.SCREEN_WIDTH,Constants.SCREEN_HEIGHT/2-100)));
        add(hostButton);
        add(joinButton);
        add(backButton);
        joinButton.addActionListener(e->showJoinPrompt());
        hostButton.addActionListener(e->showHostPrompt());
        backButton.removeActionListener(backButton.getActionListeners()[0]);
        backButton.addActionListener(e->{
            Event.ShowInitGame.invoke();
        });
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
        joinButton.removeActionListener(joinButton.getActionListeners()[0]);
        backButton.removeActionListener(backButton.getActionListeners()[0]);
        backButton.addActionListener((e)->{
            showMainPrompt();


        });
        joinButton.addActionListener(e->{
            Event.TryJoiningSession.invoke(ipField.getText());
            ipField.setText("");
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
            showMainPrompt();
        });
        hostButton.removeActionListener(hostButton.getActionListeners()[0]);
        hostButton.addActionListener(e->{
            Event.Reset.invoke();
            showMainPrompt();
        });

        revalidate();
        repaint();
    }


}

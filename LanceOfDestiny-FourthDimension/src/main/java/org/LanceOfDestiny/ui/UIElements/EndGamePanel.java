package org.LanceOfDestiny.ui.UIElements;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.events.Events;
import org.LanceOfDestiny.domain.managers.ScoreManager;
import org.LanceOfDestiny.ui.UIElements.UILibrary;

import javax.swing.*;
import java.awt.*;

public class EndGamePanel extends JPanel {
    /**
     * Lazy Instantiated Class for the end game
     * @param score
     */

    final Dimension maximumSizeButton = new Dimension(150, 45);


    public EndGamePanel(){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JLabel endLabel = UILibrary.createLabel("");
        Events.EndGame.addListener(e -> endLabel.setText((String) e));
        JButton newGameButton = UILibrary.createButton("NEW GAME", Events.Reset::invoke);
        JLabel scoreLabel = new JLabel();
        scoreLabel.setFont(new Font("Impact", Font.BOLD, 24));
        newGameButton.addActionListener(e -> {
            Events.ReturnStartScreen.invoke();
        });
        endLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        endLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        newGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(Box.createRigidArea(new Dimension(0, Constants.SCREEN_HEIGHT / 2 - 100)));
        add(endLabel);
        add(scoreLabel);
        add(newGameButton);
        Events.EndGame.addRunnableListener(()->scoreLabel.setText("Score: " + ScoreManager.getInstance().getScore()));
    }



}

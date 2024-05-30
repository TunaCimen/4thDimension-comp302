package org.LanceOfDestiny.ui.UIElements;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.events.Event;
import org.LanceOfDestiny.domain.managers.ScoreManager;

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
        Event.GameWon.addRunnableListener(() -> endLabel.setText("You Won"));
        Event.GameLost.addRunnableListener(() -> endLabel.setText("You Lost"));
        JButton newGameButton = UILibrary.createButton("NEW GAME", Event.Reset::invoke);
        JLabel scoreLabel = new JLabel();
        scoreLabel.setFont(new Font("Impact", Font.BOLD, 24));
        newGameButton.addActionListener(e -> {
            Event.ReturnStartScreen.invoke();
        });
        endLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        endLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        newGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(Box.createRigidArea(new Dimension(0, Constants.SCREEN_HEIGHT / 2 - 100)));
        add(endLabel);
        add(scoreLabel);
        add(newGameButton);
        Event.GameWon.addRunnableListener(()->scoreLabel.setText("Score: " + ScoreManager.getInstance().getScore()));
        Event.GameLost.addRunnableListener(()->scoreLabel.setText("Score: " + ScoreManager.getInstance().getScore()));
    }



}

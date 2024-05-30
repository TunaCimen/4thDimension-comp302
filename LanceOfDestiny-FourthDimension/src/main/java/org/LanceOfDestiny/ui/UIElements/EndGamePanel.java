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
        Event.EndGame.addListener(e -> endLabel.setText((String) e));
        JButton mainScreenButton = UILibrary.createButton("Main Menu", Event.ShowInitGame::invoke);
        JLabel scoreLabel = new JLabel();
        scoreLabel.setFont(new Font("Impact", Font.BOLD, 24));
        endLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        endLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainScreenButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(Box.createRigidArea(new Dimension(0, Constants.SCREEN_HEIGHT / 2 - 100)));
        add(endLabel);
        add(scoreLabel);
        add(mainScreenButton);
        Event.EndGame.addRunnableListener(()->scoreLabel.setText("Your Score: " + ScoreManager.getInstance().getScore()));
    }



}

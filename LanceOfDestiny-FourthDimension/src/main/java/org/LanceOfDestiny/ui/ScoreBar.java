package org.LanceOfDestiny.ui;

import org.LanceOfDestiny.domain.events.Events;
import org.LanceOfDestiny.domain.managers.ScoreManager;

import javax.swing.*;
import java.awt.*;

public class ScoreBar extends JLabel {


    private final String text = "SCORE: ";

    public ScoreBar(){
        setFont(new Font("Impact", Font.BOLD, 24));
        setPreferredSize(new Dimension(100,30));
        Events.UpdateScore.addRunnableListener(this::updateScore);
        Events.Reset.addRunnableListener(this::resetScore);
        updateScore();
    }
    public void updateScore(){
        setText(text + ScoreManager.getInstance().getScore());
        revalidate();
        repaint();
    }

    public void resetScore(){
        setText(text + 0);
        revalidate();
        repaint();
    }


}

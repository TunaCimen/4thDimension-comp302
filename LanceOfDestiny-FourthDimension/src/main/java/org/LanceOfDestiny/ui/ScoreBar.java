package org.LanceOfDestiny.ui;

import org.LanceOfDestiny.domain.events.Events;
import org.LanceOfDestiny.domain.managers.ScoreManager;

import javax.swing.*;
import java.awt.*;
import java.util.function.IntSupplier;

public class ScoreBar extends JLabel {


    private final String text = "SCORE: ";
    private IntSupplier scoreSupplier;
    public ScoreBar(IntSupplier scoreSupplier){
        setMaximumSize(new Dimension(300,50));
        setAlignmentX(EAST);
        this.scoreSupplier = scoreSupplier;
        setFont(new Font("Impact", Font.BOLD, 24));
        setPreferredSize(new Dimension(300,30));
        Events.UpdateScore.addRunnableListener(this::updateScore);
        Events.Reset.addRunnableListener(this::resetScore);
        updateScore();
    }
    public void updateScore(){
        setText(text + scoreSupplier.getAsInt());
        revalidate();
        repaint();
    }

    public void resetScore(){
        setText(text + 0);
        revalidate();
        repaint();
    }


}

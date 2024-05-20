package org.LanceOfDestiny.ui.UIElements;

import org.LanceOfDestiny.domain.events.Events;
import org.LanceOfDestiny.domain.managers.ScoreManager;

import javax.swing.*;
import java.awt.*;
import java.util.function.IntSupplier;

public class ScoreBar extends JLabel {


    private final String text = "SCORE: ";
    private IntSupplier scoreSupplier;
    public ScoreBar(IntSupplier scoreSupplier){
        setMaximumSize(new Dimension(150,50));
        setAlignmentX(EAST);
        this.scoreSupplier = scoreSupplier;
        setFont(new Font("Impact", Font.BOLD, 24));
        setPreferredSize(new Dimension(150,30));
        Events.UpdateScore.addRunnableListener(this::updateScore);
        Events.Reset.addRunnableListener(this::resetScore);
        updateScore();
    }

    /***
     * Constructor for non-updated event based score bar.
     * @param updateEvent Event that the update info will be fetched from.
     */
    public ScoreBar(Events updateEvent){
        updateEvent.addListener(this::setScore);
        setMaximumSize(new Dimension(300,50));
        setAlignmentX(EAST);
        setFont(new Font("Impact", Font.BOLD, 24));
        setPreferredSize(new Dimension(300,30));
        Events.Reset.addRunnableListener(this::resetScore);
    }
    public void updateScore(){
        setText(text + scoreSupplier.getAsInt());
        revalidate();
        repaint();
    }
    public void setScore(Object o){
        System.out.println("Hello there I am here but your score bar is very gay");
        setText(text + (int) o);
        revalidate();
        repaint();
    }

    public void resetScore(){
        setText(text + 0);
        revalidate();
        repaint();
    }


}

package org.LanceOfDestiny.ui.UIElements;

import org.LanceOfDestiny.domain.events.Event;

import javax.swing.*;
import java.awt.*;
import java.util.function.IntSupplier;

public class  ScoreBar extends JLabel {


    private String text;
    private IntSupplier scoreSupplier;
    public ScoreBar(IntSupplier scoreSupplier, boolean isMine){
        setMaximumSize(new Dimension(150,50));
        setAlignmentX(EAST);
        this.scoreSupplier = scoreSupplier;
        setFont(new Font("Impact", Font.BOLD, 24));
        setPreferredSize(new Dimension(150,30));
        Event.UpdateScore.addRunnableListener(this::updateScore);
        Event.Reset.addRunnableListener(this::resetScore);
        this.text = isMine ? "MyScore: " : "EnemyScore: ";
        updateScore();
    }

    /***
     * Constructor for non-updated event based score bar.
     * @param updateEvent Event that the update info will be fetched from.
     */
    public ScoreBar(Event updateEvent, boolean isMine){
        updateEvent.addListener(this::setScore);
        setMaximumSize(new Dimension(300,50));
        setAlignmentX(EAST);
        setFont(new Font("Impact", Font.BOLD, 24));
        setPreferredSize(new Dimension(300,30));
        Event.Reset.addRunnableListener(this::resetScore);
        this.text = isMine ? "MyScore: " : "EnemyScore: ";
    }
    public void updateScore(){
        setText(text + scoreSupplier.getAsInt());
        revalidate();
        repaint();
    }
    public void setScore(Object o){
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

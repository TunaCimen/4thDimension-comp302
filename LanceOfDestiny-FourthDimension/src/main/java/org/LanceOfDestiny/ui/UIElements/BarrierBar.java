package org.LanceOfDestiny.ui.UIElements;

import javax.swing.*;
import java.awt.*;
import java.util.function.IntSupplier;
import org.LanceOfDestiny.domain.events.Event;

public class BarrierBar extends JLabel {

    private String labelText;
    private IntSupplier barrierCountSupplier;

    public BarrierBar(IntSupplier barrierCountSupplier, boolean isMine) {
        setMaximumSize(new Dimension(150, 50));
        setAlignmentX(EAST);
        this.barrierCountSupplier = barrierCountSupplier;
        setFont(new Font("Impact", Font.BOLD, 24));
        setPreferredSize(new Dimension(150, 30));
        Event.UpdateBarrierCount.addRunnableListener(this::updateBarrierCount);
        Event.Reset.addRunnableListener(this::resetBarrierCount);
        this.labelText = isMine ? "My Barriers: " : "Enemy Barriers: ";
        updateBarrierCount();
    }

    /**
     * Constructor for a barrier count display that updates based on a specific event.
     *
     * @param updateEvent Event that triggers the barrier count update.
     * @param isMine Indicates whether this display is for the player or the opponent.
     */
    public BarrierBar(Event updateEvent, boolean isMine) {
        updateEvent.addListener(this::setBarrierCount);
        setMaximumSize(new Dimension(300, 50));
        setAlignmentX(EAST);
        setFont(new Font("Impact", Font.BOLD, 24));
        setPreferredSize(new Dimension(300, 30));
        Event.Reset.addRunnableListener(this::resetBarrierCount);
        this.labelText = isMine ? "My Barriers: " : "Enemy Barriers: ";
    }

    public void updateBarrierCount() {
        setText(labelText + barrierCountSupplier.getAsInt());
        revalidate();
        repaint();
    }

    public void setBarrierCount(Object count) {
        setText(labelText + (int) count);
        revalidate();
        repaint();
    }

    public void resetBarrierCount() {
        setText(labelText + 0);
        revalidate();
        repaint();
    }
}

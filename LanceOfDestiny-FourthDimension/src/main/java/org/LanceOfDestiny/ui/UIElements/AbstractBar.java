package org.LanceOfDestiny.ui.UIElements;

import javax.swing.*;
import java.awt.*;
import java.util.function.IntSupplier;
import org.LanceOfDestiny.domain.events.Event;

public abstract class AbstractBar extends JLabel {

    protected String labelText;
    protected IntSupplier valueSupplier;

    public AbstractBar(IntSupplier valueSupplier, boolean isMine, String labelTextPrefix, Event updateEvent) {
        setMaximumSize(new Dimension(150, 50));
        setAlignmentX(EAST);
        this.valueSupplier = valueSupplier;
        setFont(new Font("IMPACT", Font.BOLD, 20));
        setPreferredSize(new Dimension(150, 30));
        if (updateEvent != null) {
            updateEvent.addListener(this::setValue);
        } else {
            getUpdateEvent().addRunnableListener(this::updateValue);
        }
        Event.Reset.addRunnableListener(this::resetValue);
        this.labelText = isMine ? "My " + labelTextPrefix : labelTextPrefix;
        updateValue();
    }

    protected abstract Event getUpdateEvent();

    public void updateValue() {
        if (valueSupplier != null) {
            setText(labelText + valueSupplier.getAsInt());
        }
        revalidate();
        repaint();
    }

    public void setValue(Object count) {
        setText(labelText + (int) count);
        revalidate();
        repaint();
    }

    public void resetValue() {
        setText(labelText + 0);
        revalidate();
        repaint();
    }
}

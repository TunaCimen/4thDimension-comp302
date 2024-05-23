package org.LanceOfDestiny.ui.UIElements;

import javax.swing.*;
import java.util.function.IntSupplier;
import org.LanceOfDestiny.domain.events.Event;

public class ScoreBar extends AbstractBar {

    public ScoreBar(IntSupplier scoreSupplier, boolean isMine) {
        super(scoreSupplier, isMine, "Score: ", null);
    }

    public ScoreBar(Event updateEvent, boolean isMine) {
        super(null, isMine, "Score: ", updateEvent);
    }

    @Override
    protected Event getUpdateEvent() {
        return Event.UpdateScore;
    }
}

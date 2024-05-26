package org.LanceOfDestiny.ui.UIElements;

import javax.swing.*;
import java.util.function.IntSupplier;
import org.LanceOfDestiny.domain.events.Event;

public class BarrierBar extends AbstractBar {

    public BarrierBar(IntSupplier barrierCountSupplier, boolean isMine) {
        super(barrierCountSupplier, isMine, "Barriers: ", null);
    }

    public BarrierBar(Event updateEvent, boolean isMine) {
        super(null, isMine, "Barriers: ", updateEvent);
    }

    @Override
    protected Event getUpdateEvent() {
        return Event.UpdateBarrierCount;
    }
}

package org.LanceOfDestiny.ui.UIElements;

import javax.swing.*;
import java.util.function.IntSupplier;
import org.LanceOfDestiny.domain.events.Event;

public class ChanceBar extends AbstractBar {

    public ChanceBar(IntSupplier chanceSupplier, boolean isMine) {
        super(chanceSupplier, isMine, "Chance: ", null);
    }

    public ChanceBar(Event updateEvent, boolean isMine) {
        super(null, isMine, "Chance: ", updateEvent);
    }

    @Override
    protected Event getUpdateEvent() {
        return Event.UpdateChance;
    }
}

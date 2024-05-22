package org.LanceOfDestiny.domain.spells;

import org.LanceOfDestiny.domain.behaviours.MonoBehaviour;
import org.LanceOfDestiny.domain.events.Events;
import org.LanceOfDestiny.domain.managers.SessionManager;

public class SpellActivation extends MonoBehaviour {

    SpellType spellType;
    private final int duration;

    private int startTime;
    private int endTime;
    private boolean isActive;

    public SpellActivation(SpellType spellType, int duration) {
        this.spellType = spellType;
        this.duration = duration;
        Events.LoadGame.addRunnableListener(this::deactivate);
        Events.Reset.addRunnableListener(this::deactivate);
    }

    @Override
    public void update() {
        super.update();
        var time = SessionManager.getInstance().getLoopExecutor().getSecondsPassed();
        if (isActive && time >= endTime) {
            deactivate();
        }
    }

    public void activate() {
        isActive = true;
        spellType.activate();
        startTime = SessionManager.getInstance().getLoopExecutor().getSecondsPassed();
        endTime = startTime + duration;
    }

    public void deactivate() {
        spellType.deactivate();
        isActive = false;
    }
}

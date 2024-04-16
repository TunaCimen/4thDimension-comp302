package org.LanceOfDestiny.domain.looper;

import org.LanceOfDestiny.domain.behaviours.Behaviour;
import org.LanceOfDestiny.domain.Constants;

import javax.swing.*;

public abstract class Looper {


    protected boolean active = false;

    protected abstract void routine() throws LoopEndedException;

    public void run() {
        active = true;
        try {
            routine();
        } catch (LoopEndedException e) {
            e.getMessage();
            return;
        }
        done();
    }

    public void done() {
        System.out.println("done");
    }

    public void stop() {
        System.out.println("Stopped");
        active = false;
    }

    public boolean isActive() {
        return active;
    }

    public boolean isActiveWithThrow() throws LoopEndedException {
        if (!isActive()) throw new LoopEndedException();
        return isActive();
    }

    public void execute(Behaviour action) throws LoopEndedException {
        action.start();

        Timer timer = new Timer((int) (1000 * Constants.UPDATE_RATE), e -> {
            action.update();
        });
        timer.start();

    }

    abstract int getSecondsPassed();


}


package org.LanceOfDestiny.domain.Looper;

import org.LanceOfDestiny.domain.Behaviour;

import javax.swing.*;

public abstract class Looper {

    protected double updateRate = 0.016;
    protected boolean active = false;

    protected abstract void routine() throws LoopEndedException;

    public void run(){
        active = true;
        try {
            routine();
        } catch (LoopEndedException e) {
            e.getMessage();
            return;
        }
        done();
    }

    public void done(){
        System.out.println("done");
    }

    public void stop(){
        System.out.println("Stopped");
        active = false;
    }
    public boolean isActive(){
        return active;
    }

    public boolean isActiveWithThrow() throws LoopEndedException{
        if(!isActive())throw new LoopEndedException();
        return isActive();
    }

    public void execute(Behaviour action) throws LoopEndedException {
            action.Awake();

            Timer timer = new Timer((int) (1000*updateRate), e->{
                action.Update();
            });
            timer.start();

            }
}


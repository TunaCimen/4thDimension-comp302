package org.LanceOfDestiny.domain.looper;

import org.LanceOfDestiny.domain.behaviours.Behaviour;
import org.LanceOfDestiny.domain.Constants;

import javax.swing.*;

public abstract class Looper {
    protected boolean active = false;
    protected abstract void routine() throws LoopEndedException;
    long timePassed;
    long startTime;

    int loadedTime;

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

    public void resume(){
        active = true;
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
            if(isActive()){
                timePassed += System.nanoTime() - startTime;
                startTime = System.nanoTime();
                action.update();
                //System.out.println(getSecondsPassed());
            }
            else{
                startTime = System.nanoTime();
            }
        });
            timer.start();

    }

    public int getSecondsPassed(){
        return ((int) (timePassed * Math.pow(10, -9))) + loadedTime ;
    }

    public void setTimePassed(int timePassed){
        this.timePassed = timePassed;
    }



}


package org.LanceOfDestiny.domain.looper;

import org.LanceOfDestiny.domain.behaviours.Behaviour;
import org.LanceOfDestiny.domain.Constants;

import javax.swing.*;

public abstract class Looper {
    protected boolean active = false;
    private boolean isRunning;
    boolean restartFlag = true;

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

    public void restart(){
        active = true;
        restartFlag = true;
    }

    public void setIsRunning(boolean bool){
        isRunning = bool;
    }

    public boolean isActive() {
        return active;
    }



    public void execute(Behaviour action) throws LoopEndedException {
        System.out.println("Here executing the loop");
        Timer timer = new Timer((int) (1000 * Constants.UPDATE_RATE), e -> {
            if(restartFlag){
                action.start();
                restartFlag =false;
            }
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


    private boolean isRunning() {
        return isRunning;
    }

    public int getSecondsPassed(){
        return ((int) (timePassed * Math.pow(10, -9))) + loadedTime ;
    }

    public void setTimePassed(int timePassed){
        this.timePassed = timePassed;
    }



}


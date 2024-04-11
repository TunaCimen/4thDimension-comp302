package org.LanceOfDestiny.Looper;

import org.LanceOfDestiny.domain.Behaviour;
import org.LanceOfDestiny.domain.GameObject;

public abstract class Looper {

    protected double updateRate = 1/50;
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
        while(isActiveWithThrow()){
            long waitTime = (long) updateRate*1000;
            try{
                Thread.sleep(waitTime);
            }
                catch(InterruptedException e){
                    e.printStackTrace();
            }
            action.Update();
        }
    }
}

package org.LanceOfDestiny.domain.behaviours;

import org.LanceOfDestiny.domain.events.Event;
import org.LanceOfDestiny.domain.managers.SessionManager;

public abstract class TimedAction extends MonoBehaviour {
    /**
     * Kotlin style class to implement a time limited Behaviour
     */
    private boolean isStarted;
    private final int duration;

    private int startTime;

    private boolean isKilled;
    private int timePassed;

    Boolean resetFlag;

    public TimedAction(int duration){
        isStarted = false;
        isKilled = false;
        this.duration = duration;
        timePassed = 0;
    }

    public abstract void onUpdate();

    @Override
    public void update() {
        if(isKilled){
            onFinish();
            return;
        }
        if(isStarted && timePassed<=duration){
            onUpdate();
            timePassed = SessionManager.getInstance().getLoopExecutor().getSecondsPassed()- startTime;;
        }
        if(isStarted && timePassed>duration){
            onFinish();
        }

    }
    public void onFinish(){
        destroy();
    };

    public void start() {
        isKilled = false;
        isStarted = true;
        startTime = SessionManager.getInstance().getLoopExecutor().getSecondsPassed();
    }

    public void stop(){
        isStarted = false;
        startTime = SessionManager.getInstance().getLoopExecutor().getSecondsPassed();
    }
    public void kill(){
        System.out.println("Destroying this daaamn timed event");
        destroy();
        isKilled = true;
    }

    public int getTimePassed(){
        return timePassed;
    }
}

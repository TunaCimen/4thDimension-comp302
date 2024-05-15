package org.LanceOfDestiny;

import org.LanceOfDestiny.domain.behaviours.Behaviour;
import org.LanceOfDestiny.domain.behaviours.MonoBehaviour;
import org.LanceOfDestiny.domain.managers.SessionManager;

public abstract class TimedAction extends MonoBehaviour {
    /**
     * Kotlin style class to implement a time limited Behaviour
     */
    private boolean isStarted;
    private final int duration;

    private int startTime;
    private int timePassed;
    public TimedAction(int duration){
        isStarted = false;
        this.duration = duration;
        timePassed = 0;
    }

    public abstract void onUpdate();

    @Override
    public void update() {
        if(isStarted && timePassed<duration){
            onUpdate();
            timePassed = SessionManager.getInstance().getLoopExecutor().getSecondsPassed()- startTime;;
            System.out.println(timePassed);
        }
    }

    public void start() {
        isStarted = true;
        startTime = SessionManager.getInstance().getLoopExecutor().getSecondsPassed();
    }

    public void stop(){
        isStarted = false;
        startTime = SessionManager.getInstance().getLoopExecutor().getSecondsPassed();
    }

    public int getTimePassed(){
        return timePassed;
    }
}

package org.LanceOfDestiny.domain.looper;

public class LoopExecutor {

    private Looper mLoop;
    private Thread mThread = null;

    private boolean isStarted = false;

    public void start() {
        if (mThread != null) {
            isStarted = true;
            mLoop.startTime = System.nanoTime();
            mThread.start();
        }
    }

    public boolean isStarted(){
        return isStarted;
    }

    public void stop() {
        if (mLoop != null) {
            mLoop.stop();
        }
        //mThread = null;
    }
    public void resume() {
        if (mLoop != null) {
            mLoop.resume();
        }
       // mThread = null;
    }

    public void setLooper(Looper newLooper) {
        mLoop = newLooper;
        mThread = new Thread(() -> {
            if (mLoop != null) {
                mLoop.run();
            }
        });
    }

    public int getSecondsPassed() {
        return mLoop.getSecondsPassed();
    }
    public void setTimePassed(int timePassed) {
        mLoop.setTimePassed(timePassed);
    }

    public void setLoadedTime(int loadedTime){
        mLoop.loadedTime = loadedTime;
    }


    public Looper getLoop() {
        return mLoop;
    }


}

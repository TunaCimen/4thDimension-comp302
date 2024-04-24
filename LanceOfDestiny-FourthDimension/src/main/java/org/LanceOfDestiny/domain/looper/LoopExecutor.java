package org.LanceOfDestiny.domain.looper;

import java.time.LocalTime;

public class LoopExecutor {

    private Looper mLoop;
    private Thread mThread = null;
    private LocalTime startingTime;

    public void start() {
        if (mThread != null) {
            startingTime = LocalTime.now();
            mThread.start();
        }
    }

    public void stop() {
        if (mLoop != null) {
            mLoop.stop();
        }
        mThread = null;
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

    public Looper getLoop() {
        return mLoop;
    }

    public LocalTime getStartingTime() {
        return startingTime;
    }
}

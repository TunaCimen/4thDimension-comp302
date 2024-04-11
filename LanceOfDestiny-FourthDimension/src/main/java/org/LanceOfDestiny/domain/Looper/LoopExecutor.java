package org.LanceOfDestiny.domain.Looper;

public class LoopExecutor {


    private Looper mLoop;
    private Thread mThread = null;

    public void setLooper(Looper newLooper){
        mLoop = newLooper;
        mThread = new Thread(() -> {
            if (mLoop!=null){
                mLoop.run();
            }
        });
    }

    public void start(){
        if (mThread != null){
            mThread.start();
        }
    }

    public void stop(){
        if(mLoop!=null){
            mLoop.stop();
        }
        mThread=null;
    }

    public Looper getLoop(){
        return mLoop;
    }

}

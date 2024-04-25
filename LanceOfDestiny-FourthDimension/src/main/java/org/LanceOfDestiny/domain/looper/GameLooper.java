package org.LanceOfDestiny.domain.looper;

import javax.swing.*;

public class GameLooper extends Looper {
    JPanel drawCanvas;
    GameExec gameExec;

    public GameLooper(JPanel drawCanvas) {
        this.drawCanvas = drawCanvas;
        gameExec = new GameExec(drawCanvas);
    }

    public int getSecondsPassed() {
        return (int) (gameExec.timePassed * Math.pow(10, -9));
    }

    public void setTimePassed(double timePassed){
        gameExec.setTimePassed(timePassed);
    }

    @Override
    protected void routine() throws LoopEndedException {
        execute(gameExec);
    }
}

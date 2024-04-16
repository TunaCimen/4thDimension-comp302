package org.LanceOfDestiny.domain.looper;

import org.LanceOfDestiny.domain.behaviours.Behaviour;

import javax.swing.*;

public class GameLooper extends Looper {
    JPanel drawCanvas;
    GameExec gameExec;

    public GameLooper(JPanel drawCanvas) {
        this.drawCanvas = drawCanvas;
        gameExec = new GameExec(Behaviour.getBehaviours(), drawCanvas);
    }

    public int getSecondsPassed() {
        return (int) (gameExec.timePassed * Math.pow(10, -9));
    }


    @Override
    protected void routine() throws LoopEndedException {
        execute(gameExec);
    }
}

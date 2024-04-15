package org.LanceOfDestiny.domain.Looper;

import org.LanceOfDestiny.domain.GameObject;

import javax.swing.*;

public class GameLooper extends Looper {
    JPanel drawCanvas;
    GameExec gameExec;

    public GameLooper(JPanel drawCanvas) {
        this.drawCanvas = drawCanvas;
        gameExec = new GameExec(GameObject.getGameObjects(), drawCanvas);
    }

    public int getSecondsPassed() {
        return (int) (gameExec.timePassed * Math.pow(10, -9));
    }


    @Override
    protected void routine() throws LoopEndedException {
        execute(gameExec);
    }
}

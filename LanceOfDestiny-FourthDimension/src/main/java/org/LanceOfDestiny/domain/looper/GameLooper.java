package org.LanceOfDestiny.domain.looper;

import javax.swing.*;

public class GameLooper extends Looper {
    JPanel drawCanvas;
    GameExec gameExec;

    public GameLooper(JPanel drawCanvas) {
        this.drawCanvas = drawCanvas;
        gameExec = new GameExec(drawCanvas);
    }

    @Override
    protected void routine() throws LoopEndedException {
        execute(gameExec);
    }
}

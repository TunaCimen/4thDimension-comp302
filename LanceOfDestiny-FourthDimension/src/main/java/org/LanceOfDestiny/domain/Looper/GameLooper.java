package org.LanceOfDestiny.domain.Looper;

import org.LanceOfDestiny.domain.GameObject;

import javax.swing.*;

public class GameLooper extends Looper{

    JFrame frame;

    public GameLooper(JFrame frame){
        this.frame = frame;
    }
    @Override
    protected void routine() throws LoopEndedException {
        execute(
                new GameExec(frame, GameObject.getGameObjects())
        );

    }
}

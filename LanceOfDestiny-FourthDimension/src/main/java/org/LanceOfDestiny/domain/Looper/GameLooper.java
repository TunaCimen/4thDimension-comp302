package org.LanceOfDestiny.domain.Looper;

import org.LanceOfDestiny.domain.GameObject;

import javax.swing.*;

public class GameLooper extends Looper {

    JFrame frame;

    @Override
    protected void routine() throws LoopEndedException {
        execute(new GameExec(GameObject.getGameObjects()));
    }

}

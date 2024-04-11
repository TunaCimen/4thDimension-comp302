package org.LanceOfDestiny.domain.Looper;

import org.LanceOfDestiny.domain.GameObject;

public class GameLooper extends Looper{
    @Override
    protected void routine() throws LoopEndedException {
        System.out.println("Main Game Loop");
        execute(new ParallelExec(
                GameObject.getGameObjects()
        ));
    }
}

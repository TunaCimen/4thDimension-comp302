package org.LanceOfDestiny.Looper;

import org.LanceOfDestiny.domain.Behaviour;
import org.LanceOfDestiny.domain.GameObject;

import java.util.List;

public class GameLooper extends Looper{
    @Override
    protected void routine() throws LoopEndedException {
        System.out.println("Main Game Loop");
        execute(new ParallelExec(
                GameObject.getGameObjects()
        ));
    }
}

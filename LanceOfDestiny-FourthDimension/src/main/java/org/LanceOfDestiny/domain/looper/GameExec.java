package org.LanceOfDestiny.domain.looper;

import org.LanceOfDestiny.domain.behaviours.Behaviour;
import org.LanceOfDestiny.domain.events.Events;
import org.LanceOfDestiny.domain.managers.InputManager;
import org.LanceOfDestiny.domain.physics.PhysicsManager;

import javax.swing.*;

public class GameExec extends Behaviour {

    private final JPanel drawCanvas;
    private boolean isPaused = false;
    public GameExec(JPanel drawCanvas) {
        this.drawCanvas = drawCanvas;
        Events.PauseGame.addRunnableListener(this::pauseGame);
        Events.ResumeGame.addRunnableListener(this::resumeGame);
        Events.Reset.addRunnableListener(this::pauseGame);
        Events.LoadGame.addRunnableListener(this::start);
    }

    @Override
    public void start() {
        for (int i = 0; i < getBehaviours().size(); i++) {
            getBehaviours().get(i).start();
        }
    }
    @Override
    public void update() {
        InputManager.getInstance().updateActions();
        PhysicsManager.getInstance().updateCollisions();
        for (int i = 0; i < getBehaviours().size(); i++) {
            var behaviour = getBehaviours().get(i);
            behaviour.update();
            drawCanvas.repaint();
        }
    }

    public void pauseGame() {
        isPaused = true;
    }

    public void resumeGame() {
        isPaused = false;
    }

}

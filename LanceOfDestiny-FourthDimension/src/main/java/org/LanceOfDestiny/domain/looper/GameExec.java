package org.LanceOfDestiny.domain.looper;

import org.LanceOfDestiny.domain.behaviours.Behaviour;
import org.LanceOfDestiny.domain.events.Events;
import org.LanceOfDestiny.domain.managers.InputManager;
import org.LanceOfDestiny.domain.physics.PhysicsManager;

import javax.swing.*;

public class GameExec extends Behaviour {

    private final JPanel drawCanvas;

    public void setTimePassed(double timePassed) {
        this.timePassed = timePassed;
    }

    protected double timePassed;
    private boolean isPaused = false;
    private double startTime;

    public GameExec(JPanel drawCanvas) {
        timePassed = 0;
        this.drawCanvas = drawCanvas;
        Events.PauseGame.addRunnableListener(this::pauseGame);
        Events.ResumeGame.addRunnableListener(this::resumeGame);
    }

    @Override
    public void start() {
        startTime = System.nanoTime();
        for (int i = 0; i < getBehaviours().size(); i++) {
            getBehaviours().get(i).start();
        }
    }

    @Override
    public void update() {
        if (isPaused) {
            startTime = System.nanoTime();
            return;
        }
        timePassed += System.nanoTime() - startTime;
        startTime = System.nanoTime();
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

package org.LanceOfDestiny.domain.looper;

import org.LanceOfDestiny.domain.behaviours.Behaviour;
import org.LanceOfDestiny.domain.events.Events;
import org.LanceOfDestiny.domain.managers.InputManager;
import org.LanceOfDestiny.domain.physics.PhysicsManager;

import javax.swing.*;
import java.util.List;

public class GameExec extends Behaviour {

    private final List<Behaviour> behaviourList;
    private final JPanel drawCanvas;
    protected double timePassed;
    private boolean isPaused = false;
    private double startTime;

    public GameExec(List<Behaviour> behaviourList, JPanel drawCanvas) {
        this.behaviourList = behaviourList;
        this.drawCanvas = drawCanvas;
        Events.PauseGame.addRunnableListener(this::pauseGame);
        Events.ResumeGame.addRunnableListener(this::resumeGame);
    }

    public void pauseGame() {
        isPaused = true;
    }

    public void resumeGame() {
        isPaused = false;
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
        for (Behaviour b : behaviourList) {
            b.update();
            drawCanvas.repaint();
        }
    }

    @Override
    public void start() {
        System.out.println("Started the Game Exec");
        timePassed = 0;
        startTime = System.nanoTime();
        for (Behaviour b : behaviourList) {
            b.start();
        }
    }
}

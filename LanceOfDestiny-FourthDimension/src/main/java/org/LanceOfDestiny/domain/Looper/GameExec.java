package org.LanceOfDestiny.domain.Looper;

import org.LanceOfDestiny.domain.Behaviour;
import org.LanceOfDestiny.domain.EventSystem.Events;
import org.LanceOfDestiny.domain.managers.InputManager;
import org.LanceOfDestiny.domain.physics.PhysicsManager;

import javax.swing.*;
import java.time.LocalTime;
import java.util.List;

public class GameExec extends Behaviour {

    private final List<Behaviour> behaviourList;
    private final JPanel drawCanvas;
    private boolean isPaused = false;
    protected double timePassed;
    private double startTime;
    public GameExec(List<Behaviour> behaviourList, JPanel drawCanvas){
        this.behaviourList = behaviourList;
        this.drawCanvas = drawCanvas;
        Events.PauseGame.addRunnableListener(this::pauseGame);
        Events.ResumeGame.addRunnableListener(this::resumeGame);
    }

    public void pauseGame(){
        isPaused = true;
    }

    public void resumeGame(){
        isPaused = false;
    }

    @Override
    public void awake() {
        for(Behaviour b : behaviourList){
            b.awake();

        }

    }
    @Override
    public void update() {

        if(isPaused)return;
        timePassed += System.nanoTime() - startTime;
        startTime = System.nanoTime();
        InputManager.getInstance().updateActions();
        PhysicsManager.getInstance().handleCollisionEvents(PhysicsManager.getInstance().checkCollisions());
        for(Behaviour b : behaviourList){
            b.update();
            drawCanvas.repaint();
        }
    }

    @Override
    public void start() {
        System.out.println("Started the Game Exec");
        timePassed = 0;
        startTime = System.nanoTime();
        for(Behaviour b : behaviourList){
            b.start();
        }
    }
}

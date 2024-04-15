package org.LanceOfDestiny.domain.Looper;

import org.LanceOfDestiny.domain.Behaviour;
import org.LanceOfDestiny.domain.managers.InputManager;
import org.LanceOfDestiny.domain.physics.PhysicsManager;

import javax.swing.*;
import java.util.List;

public class GameExec extends Behaviour {

    private List<Behaviour> behaviourList;
    private JPanel drawCanvas;
    public GameExec(List<Behaviour> behaviourList, JPanel drawCanvas){
        this.behaviourList = behaviourList;
        this.drawCanvas = drawCanvas;
    }

    @Override
    public void Awake() {
        for(Behaviour b : behaviourList){
            b.Awake();

        }

    }
    @Override
    public void Update() {
        InputManager.getInstance().updateActions();
        PhysicsManager.getInstance().handleCollisionEvents(PhysicsManager.getInstance().checkCollisions());
        for(Behaviour b : behaviourList){
            b.Update();
            drawCanvas.repaint();
        }





    }

    @Override
    public void Start() {
        for(Behaviour b : behaviourList){
            b.Start();
        }


    }
}

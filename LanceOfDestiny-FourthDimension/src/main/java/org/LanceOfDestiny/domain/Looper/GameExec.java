package org.LanceOfDestiny.domain.Looper;

import org.LanceOfDestiny.domain.Behaviour;
import org.LanceOfDestiny.domain.managers.InputManager;
import org.LanceOfDestiny.domain.physics.PhysicsManager;

import javax.swing.*;
import java.util.List;

public class GameExec extends Behaviour {

    private List<Behaviour> behaviourList;
    public GameExec(List<Behaviour> behaviourList){
        this.behaviourList = behaviourList;
    }

    @Override
    public void Awake() {
        for(Behaviour b : behaviourList){
            b.Awake();

        }

    }
    @Override
    public void Update() {
        PhysicsManager.getInstance().handleCollisionEvents(PhysicsManager.getInstance().checkCollisions());
        InputManager.getInstance().updateActions();
        for(Behaviour b : behaviourList){
            b.Update();
            b.gameObject.sprite().repaint();
        }





    }

    @Override
    public void Start() {
        for(Behaviour b : behaviourList){
            b.Start();
            b.gameObject.sprite().repaint();
        }


    }
}

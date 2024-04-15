package org.LanceOfDestiny.domain.Looper;

import org.LanceOfDestiny.domain.Behaviour;
import org.LanceOfDestiny.domain.managers.InputManager;
import org.LanceOfDestiny.domain.physics.PhysicsManager;

import java.util.List;

public class GameExec extends Behaviour {

    private List<Behaviour> behaviourList;

    public GameExec(List<Behaviour> behaviourList) {
        this.behaviourList = behaviourList;
    }

    @Override
    public void awake() {
        for (Behaviour b : behaviourList) {
            b.awake();
        }
    }

    @Override
    public void update() {
        InputManager.getInstance().updateActions();
        PhysicsManager.getInstance().handleCollisionEvents(PhysicsManager.getInstance().checkCollisions());
        for (Behaviour b : behaviourList) {
            b.update();
            b.gameObject.getSprite().repaint();
        }
    }

    @Override
    public void start() {
        for (Behaviour b : behaviourList) {
            b.start();
            b.gameObject.getSprite().repaint();
        }
    }
}

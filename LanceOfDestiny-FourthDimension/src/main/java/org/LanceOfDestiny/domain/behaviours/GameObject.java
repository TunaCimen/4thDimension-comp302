package org.LanceOfDestiny.domain.behaviours;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.barriers.ExplosiveBarrier;
import org.LanceOfDestiny.domain.barriers.ReinforcedBarrier;
import org.LanceOfDestiny.domain.barriers.RewardingBarrier;
import org.LanceOfDestiny.domain.barriers.SimpleBarrier;
import org.LanceOfDestiny.domain.physics.Collider;
import org.LanceOfDestiny.domain.physics.Collision;
import org.LanceOfDestiny.domain.physics.PhysicsManager;
import org.LanceOfDestiny.domain.physics.Vector;
import org.LanceOfDestiny.domain.spells.Hex;
import org.LanceOfDestiny.domain.spells.RewardBox;
import org.LanceOfDestiny.domain.sprite.RectangleSprite;
import org.LanceOfDestiny.domain.sprite.Sprite;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class GameObject extends Behaviour {

    private static List<Behaviour> gameObjects = new ArrayList<>();
    protected Vector position;
    protected Collider collider;
    protected Sprite sprite;
    protected double angle;
    public GameObject parent;

    public GameObject() {
        super();
        gameObject = this;
        parent = this;
        GameObject.gameObjects.add(this);
        this.sprite = new RectangleSprite(this, Color.magenta, 0, 0);
    }

    @Override
    public void start() {
        super.start();
    }

    public void update() {
        super.update();
        setPosition((getPosition().add(getCollider().getVelocity())));
    }

    public void destroy() {
        super.destroy();
        gameObjects.remove(this);
        PhysicsManager.getInstance().removeCollider(getCollider());
    }

    public void onCollisionEnter(Collision collision) {
    }

    public void onTriggerEnter(Collision collision) {
    }

    public static List<Behaviour> getGameObjects() {
        return gameObjects;
    }

    public Vector getPosition() {
        return position;
    }

    public void setPosition(Vector position) {
        this.position = position;
    }
    public void shiftPosition(Vector position) {
    }


    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public Collider getCollider() {
        return collider;
    }

    public void setCollider(Collider collider) {
        this.collider = collider;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public Vector getDirection() {
        return getCollider().getVelocity().normalize();
    }
  
    public void setParent(GameObject gameObject) {
        this.parent = gameObject;
    }

    public static void displayGameObjects(){
        System.out.println("Explosive Barrier Count: " + gameObjects.stream().filter(e -> {
            if(e instanceof ExplosiveBarrier)return true;
            return false;
        }).toList().size());
        System.out.println("Reinforced Barrier Count: " + gameObjects.stream().filter(e -> {
            if(e instanceof ReinforcedBarrier)return true;
            return false;
        }).toList().size());
        System.out.println("Simple Barrier Count: " + gameObjects.stream().filter(e -> {
            if(e instanceof SimpleBarrier)return true;
            return false;
        }).toList().size());
        System.out.println("Rewarding Barrier Count: " + gameObjects.stream().filter(e -> {
            if(e instanceof RewardingBarrier)return true;
            return false;
        }).toList().size());
        System.out.println("Hex Count: " + gameObjects.stream().filter(e -> {
            if(e instanceof Hex)return true;
            return false;
        }).toList().size());
        System.out.println("Reward Box Count: " + gameObjects.stream().filter(e -> {
            if(e instanceof RewardBox)return true;
            return false;
        }).toList().size());
    }
}

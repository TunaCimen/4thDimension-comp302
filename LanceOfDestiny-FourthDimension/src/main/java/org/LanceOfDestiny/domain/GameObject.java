package org.LanceOfDestiny.domain;

import org.LanceOfDestiny.domain.physics.Collider;
import org.LanceOfDestiny.domain.physics.Collision;
import org.LanceOfDestiny.domain.physics.Vector;
import org.LanceOfDestiny.domain.sprite.RectangleSprite;
import org.LanceOfDestiny.domain.sprite.Sprite;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class GameObject extends Behaviour {

    private static int idCounter;
    private static List<Behaviour> gameObjects = new ArrayList<>();
    protected Vector position;
    protected double angle;
    private int id;

    protected Collider collider;

    public GameObject() {
        gameObject = this;
        this.id = idCounter++;
        GameObject.gameObjects.add(this);
    }

    public static List<Behaviour> getGameObjects() {
        return gameObjects;
    }

    public static void destroyAll() {
        gameObjects.clear();
    }

    public void awake() {
        super.awake();
    }

    public void destroy() {
        gameObjects.remove(this);
    }

    public void onCollisionEnter(Collision collision) {
        return;
    }

    public void onTriggerEnter(Collision collision) {
        return;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public Sprite getSprite() {
        return new RectangleSprite(this, Color.magenta, 0, 0); // Default Sprite Given to everything.
    }

    public Vector getPosition() {
        return position;
    }

    public void setPosition(Vector position) {
        this.position = position;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public Collider getCollider(){
        return collider;
    }

}

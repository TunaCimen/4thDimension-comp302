package org.LanceOfDestiny.domain;
import org.LanceOfDestiny.domain.physics.Collision;
import org.LanceOfDestiny.domain.physics.Vector;
import org.LanceOfDestiny.ui.RectangleSprite;
import org.LanceOfDestiny.ui.Sprite;

import java.awt.*;
import java.util.*;
import java.util.List;

public abstract class GameObject extends Behaviour{

    protected Vector position;
    protected double angle;

    public Sprite getSprite(){
        return new RectangleSprite(this, Color.white,15,35); // Default Sprite Given to everything.
    }
    private static List<Behaviour> gameObjects = new ArrayList<>();


    public GameObject() {
        gameObject = this;
        GameObject.gameObjects.add(this);
    }

  public Vector getPosition() {

        return position;
    }

    public double getAngle(){
        return angle;
    }
    public void setAngle(double angle){
        this.angle = angle;
    }

    public void setPosition(Vector position) {
        this.position = position;
    }

    public void Awake() {
    }

    public static List<Behaviour> getGameObjects() {
        return gameObjects;
    }

    public void Destroy() {
        gameObjects.remove(this);
    }

    public static void DestroyAll(){
        gameObjects.clear();
    }

    public void onCollisionEnter(Collision collision) {
        return;
    }
}

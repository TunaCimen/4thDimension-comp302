package org.LanceOfDestiny.domain;
import org.LanceOfDestiny.domain.physics.Vector;
import org.LanceOfDestiny.ui.RectangleSprite;
import org.LanceOfDestiny.ui.Sprite;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public abstract class GameObject extends Behaviour{

    protected Vector position;

    public Sprite sprite(){
        return new RectangleSprite(this, Color.white,15,35); // Default Sprite Given to everything.
    }
    private static List<Behaviour> gameObjects = new ArrayList<>();


    public GameObject() {
        System.out.println("Game Object Init");
        gameObject = this;
        GameObject.gameObjects.add(this);
    }

  public Vector getPosition() {
        return position;
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


}

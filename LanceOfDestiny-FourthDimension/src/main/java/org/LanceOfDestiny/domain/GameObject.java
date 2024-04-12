package org.LanceOfDestiny.domain;
import org.LanceOfDestiny.domain.physics.Vector;

import javax.swing.*;
import java.util.*;

public abstract class GameObject extends Behaviour{


    

    protected Vector position;

    public JPanel sprite(){
        return new JPanel(); // Default Sprite Given to everything.
    }
    private static List<Behaviour> gameObjects = new ArrayList<>();


    public GameObject() {
        super();
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

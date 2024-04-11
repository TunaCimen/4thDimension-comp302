package org.LanceOfDestiny.domain;
import javax.swing.*;
import java.util.*;

public abstract class GameObject extends Behaviour{

    public JPanel sprite(){
        return new JPanel(); // Default Sprite Given to everything.
    }
    private static ArrayList<Behaviour> gameObjects = new ArrayList<>();

    public GameObject() {
        GameObject.gameObjects.add(this);
    }


    public static ArrayList<Behaviour> getGameObjects() {
        return gameObjects;
    }

    public void Destroy() {
        gameObjects.remove(this);
    }

    public static void DestroyAll(){
        gameObjects.clear();
    }

}

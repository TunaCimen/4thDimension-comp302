package org.LanceOfDestiny.domain;
import java.util.*;

public abstract class GameObject {

    private static List<GameObject> gameObjects = new ArrayList<>();
    private LinkedList<Object> components = new LinkedList<>();

    public GameObject() {
        GameObject.gameObjects.add(this);
    }

    public void Awake() {
    }

    public void Start() {
    }

    public void Update() {
    }

    public static List<GameObject> getGameObjects() {
        return gameObjects;
    }

    public void Destroy() {
        gameObjects.remove(this);
        var components = getComponents();
        // this should remove all components associated with the game object hopefully
        for (int i = 0; i < components.size(); i++) {
            components.removeFirst();
        }
    }

    public static void DestroyAll(){
        gameObjects.clear();
    }

    public LinkedList<Object> getComponents() {
        return components;
    }

    public void setComponents(LinkedList<Object> components) {
        this.components = components;
    }

    public <T> T getComponent(Class<T> componentType) {
        for (Object component : components) {
            if (componentType.isInstance(component)) {
                return componentType.cast(component);
            }
        }
        return null; // or optionally throw an exception if the component is not found
    }
    // Barrier barrier = getComponent(Barrier.class); an example usage

}

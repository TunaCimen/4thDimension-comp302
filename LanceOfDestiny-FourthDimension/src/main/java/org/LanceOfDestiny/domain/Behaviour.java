package org.LanceOfDestiny.domain;

<<<<<<< Updated upstream
public abstract class Behaviour {

    public void Awake() {
    }

    public void Start() {
    }

    public void Update() {
=======

import java.util.ArrayList;
import java.util.Collections;

public class Behaviour {

    public Transform transform;
    ArrayList<Object> components;

    public Behaviour(){
        components = new ArrayList<>(Collections.singletonList(transform));
        transform = new Transform(0,0,0);
    }

    public Object getComponent(Object component){
        for(Object object : components){
            if(component.getClass().equals(object.getClass())){
                return object;
            }
        }
        return null;
    }

    public void addComponent(Object o){
        components.add(o);
>>>>>>> Stashed changes
    }

}

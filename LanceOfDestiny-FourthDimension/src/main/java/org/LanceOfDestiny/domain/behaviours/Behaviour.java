package org.LanceOfDestiny.domain.behaviours;

import java.util.ArrayList;
import java.util.List;

public abstract class Behaviour {

    public GameObject gameObject = null;
    protected static List<Behaviour> behaviours = new ArrayList<>();

    public Behaviour(){
        if (this instanceof GameObject || this instanceof MonoBehaviour)
            behaviours.add(this);
    }

    public void start() {
    }

    public void update() {
    }

    public void destroy() {
        behaviours.remove(this);
    }

    public static List<Behaviour> getBehaviours() {
        return behaviours;
    }




}

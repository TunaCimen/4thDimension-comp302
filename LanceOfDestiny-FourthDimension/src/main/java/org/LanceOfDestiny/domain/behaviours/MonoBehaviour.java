package org.LanceOfDestiny.domain.behaviours;

import java.util.ArrayList;
import java.util.List;

public class MonoBehaviour extends Behaviour {

    public static List<MonoBehaviour> monoBehaviours = new ArrayList<>();

    public MonoBehaviour() {
        super();
        behaviours.add(this);
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void update() {
        super.update();
    }

    public static List<MonoBehaviour> getMonoBehaviours() {
        return monoBehaviours;
    }

    @Override
    public void destroy() {
        super.destroy();
        monoBehaviours.remove(this);
    }
}

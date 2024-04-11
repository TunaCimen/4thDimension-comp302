package org.LanceOfDestiny.Looper;

import org.LanceOfDestiny.domain.Behaviour;

import java.util.ArrayList;
import java.util.List;

public class ParallelExec extends Behaviour {


    private final ArrayList<Behaviour> behaviours;
    public ParallelExec(List<Behaviour> behaviours){
        this.behaviours = new ArrayList<>(behaviours);
    }
    @Override
    public void Update() {
        for(Behaviour b : behaviours){
            b.Update();
        }
    }

    @Override
    public void Start() {
        for(Behaviour b : behaviours){
            b.Start();
        }
    }

    @Override
    public void Awake() {
        for(Behaviour b : behaviours){
            b.Awake();
        }
    }
}

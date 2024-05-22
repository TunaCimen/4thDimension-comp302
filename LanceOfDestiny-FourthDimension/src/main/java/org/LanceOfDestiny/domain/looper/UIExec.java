package org.LanceOfDestiny.domain.looper;

import org.LanceOfDestiny.domain.behaviours.AnimationBehaviour;
import org.LanceOfDestiny.domain.behaviours.Behaviour;

import javax.swing.*;
import java.util.stream.Collectors;

public class UIExec extends Behaviour{
    /**
     * Another loop that plays before the game starts for UI and Cutscene(?) animations.
     */
    JPanel drawCanvas;
    public UIExec(JPanel drawCanvas){
        this.drawCanvas = drawCanvas;
    }
    @Override
    public void update() {
        for(AnimationBehaviour ab : AnimationBehaviour.getAnimationBehaviourList().stream().filter(e -> e.isAnimating).toList()){
            ab.onAnimate();
        }
        drawCanvas.repaint();

    }
}

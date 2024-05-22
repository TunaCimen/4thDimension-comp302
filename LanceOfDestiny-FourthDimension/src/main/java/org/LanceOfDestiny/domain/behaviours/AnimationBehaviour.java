package org.LanceOfDestiny.domain.behaviours;

import java.util.ArrayList;
import java.util.List;

public abstract class AnimationBehaviour{

    public boolean isAnimating = true;
    private static List<AnimationBehaviour> animationBehaviourList = new ArrayList<>();
    public AnimationBehaviour(){
        animationBehaviourList.add(this);
    }
    public abstract void onAnimate();

    public void killAnimation(){
        animationBehaviourList.remove(this);
    }

    public static List<AnimationBehaviour> getAnimationBehaviourList() {
        return animationBehaviourList;
    }
}

package org.LanceOfDestiny.ui.UIUtilities;

import org.LanceOfDestiny.Animation.Animation;
import org.LanceOfDestiny.domain.behaviours.AnimationBehaviour;
import org.LanceOfDestiny.domain.events.Events;

import java.util.function.IntConsumer;

public interface Animatable {
    default void setAnimationBehaviour(Animation animation){
        animation.setAnimation();
    }

    default void setAnimationBehaviourOnEvent(Animation animation, Events event){
        event.addRunnableListener(animation::setAnimation);
    }

}

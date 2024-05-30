package org.LanceOfDestiny.ui.UIUtilities;

import org.LanceOfDestiny.ui.Animation.Animation;
import org.LanceOfDestiny.domain.events.Event;

public interface Animatable {
    default void setAnimationBehaviour(Animation animation){
        animation.setAnimation();
    }

    default void setAnimationBehaviourOnEvent(Animation animation, Event event){
        event.addRunnableListener(animation::setAnimation);
    }

}

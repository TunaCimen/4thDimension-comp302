package org.LanceOfDestiny.ui.Animation;

import java.util.function.Consumer;

public record CountdownAnimation(int count, Consumer<String> setter,Runnable cont) implements Animation {
    @Override
    public void setAnimation() {
        if(cont == null)new CountdownAnimationBehaviour(count, setter);
        else new CountdownAnimationBehaviour(count,setter,cont);

    }
}


package org.LanceOfDestiny.Animation;


import org.LanceOfDestiny.ui.UIUtilities.Animatable;

import java.util.function.IntConsumer;
import java.util.function.IntSupplier;

public record LinearInterpolation(int threshold, int initVal, IntSupplier getter, IntConsumer setter) implements Animation {

    @Override
    public void setAnimation() {
        new LinearInterpolationBehaviour(this);
    }
}

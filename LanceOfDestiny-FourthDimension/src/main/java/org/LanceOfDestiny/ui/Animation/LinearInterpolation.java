package org.LanceOfDestiny.ui.Animation;


import java.util.function.IntConsumer;
import java.util.function.IntSupplier;

public record LinearInterpolation(int threshold, int initVal, IntSupplier getter, IntConsumer setter) implements Animation {

    @Override
    public void setAnimation() {
        new LinearInterpolationBehaviour(this);
    }
}

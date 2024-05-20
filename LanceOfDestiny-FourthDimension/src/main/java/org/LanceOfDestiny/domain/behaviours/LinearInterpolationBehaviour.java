package org.LanceOfDestiny.domain.behaviours;

import java.util.function.IntConsumer;
import java.util.function.IntSupplier;

public class LinearInterpolationBehaviour extends AnimationBehaviour {


    IntSupplier valSupplier;

    IntConsumer valSetter;

    private int threshold;
    private int initVal;
    int direction = 1;
    public LinearInterpolationBehaviour(IntSupplier valSupplier, IntConsumer valSetter, int threshold, int initVal){
        this.valSupplier = valSupplier;
        this.valSetter = valSetter;
        this.threshold = threshold;
        this.initVal = initVal;
    }
    @Override
    public void onAnimate() {
        if(valSupplier.getAsInt() >= initVal + threshold)direction = -1;
        if(valSupplier.getAsInt() <= initVal - threshold)direction = 1;
        valSetter.accept(valSupplier.getAsInt() + direction);
    }
}

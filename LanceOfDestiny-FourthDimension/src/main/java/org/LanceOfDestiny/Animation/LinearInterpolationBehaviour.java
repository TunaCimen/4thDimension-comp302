package org.LanceOfDestiny.Animation;

import org.LanceOfDestiny.Animation.LinearInterpolation;
import org.LanceOfDestiny.domain.behaviours.AnimationBehaviour;
import org.LanceOfDestiny.ui.UIUtilities.Animatable;

import java.util.function.IntConsumer;
import java.util.function.IntSupplier;

public class LinearInterpolationBehaviour extends AnimationBehaviour {


    IntSupplier valSupplier;

    IntConsumer valSetter;

    private int threshold;
    private int initVal;
    int direction = 1;
    LinearInterpolationBehaviour(LinearInterpolation animData){
        this.valSupplier = animData.getter();
        this.valSetter = animData.setter();
        this.threshold = animData.threshold();
        this.initVal = animData.initVal();
    }
    @Override
    public void onAnimate() {
        if(valSupplier.getAsInt() >= initVal + threshold)direction = -1;
        if(valSupplier.getAsInt() <= initVal - threshold)direction = 1;
        valSetter.accept(valSupplier.getAsInt() + direction);
    }
}

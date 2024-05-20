package org.LanceOfDestiny.ui.UIElements;

import org.LanceOfDestiny.domain.behaviours.AnimationBehaviour;
import org.LanceOfDestiny.domain.behaviours.LinearInterpolationBehaviour;
import org.LanceOfDestiny.domain.sprite.ImageLibrary;
import org.LanceOfDestiny.domain.sprite.ImageOperations;
import org.LanceOfDestiny.ui.UIUtilities.Animatable;


import javax.swing.*;
import java.awt.*;

public class YmirUI implements Animatable {

    private final Image ymirImage;
    private final int ymirWidth = 250;
    private final int ymirHeight = 250;

    protected int initLocX, initLocY, locX,locY;

    private int ymirDisplacement;
    public YmirUI(int locX, int locY){
        this.locX = locX;
        this.locY = locY;
        this.initLocX = locX;
        this.initLocY = locY;
        new LinearInterpolationBehaviour(this::getY, this::setY,50,initLocY);
        this.ymirImage = new ImageIcon(ImageOperations.resizeImage(ImageLibrary.Ymir.getImage(),ymirWidth,ymirHeight)).getImage();

    }

    public Image getYmirImage() {
        return ymirImage;
    }

    public int getYmirHeight() {
        return ymirHeight;
    }

    public int getYmirWidth() {
        return ymirWidth;
    }

    public int getX(){
        return locX;
    }

    public int getY(){
        return locY;
    }
    public void setY(int y){
        locY = y;
    }

}

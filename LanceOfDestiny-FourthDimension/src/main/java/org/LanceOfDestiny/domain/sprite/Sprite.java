package org.LanceOfDestiny.domain.sprite;

import org.LanceOfDestiny.domain.Behaviour;
import org.LanceOfDestiny.domain.GameObject;

import javax.swing.*;
import java.awt.*;

public abstract class Sprite {

    public GameObject attachedGameObject;
    public Color color;
    public String number;
    public boolean isVisible = true;

    public Sprite(GameObject attachedGameObject, Color color){
        this.attachedGameObject = attachedGameObject;
        this.color = color;
    }

    public abstract void drawShape(Graphics g);

    public void setVisible(boolean visible){
        isVisible = visible;
    }


}





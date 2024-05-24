package org.LanceOfDestiny.domain.sprite;

import org.LanceOfDestiny.domain.behaviours.GameObject;

import java.awt.*;


public abstract class Sprite implements Drawable {
    public GameObject attachedGameObject;
    public Color color;
    public String number;
    public boolean isVisible = true;
    Image image;
    public Sprite(GameObject attachedGameObject, Color color){
        this.attachedGameObject = attachedGameObject;
        this.color = color;
        image = null;
    }
    public void setImage(Image image){
        this.image = image;
    }
    public void setVisible(boolean visible){
        isVisible = visible;
    }
    public Image getImage() {
        return image;
    }
}





package org.LanceOfDestiny.ui;

import org.LanceOfDestiny.domain.GameObject;

import java.awt.*;

public class RectangleSprite extends Sprite{


    int width;
    int height;

    public RectangleSprite(GameObject attached, Color color,int width,int height){
        super(attached,color);
        this.width = width;
        this.height = height;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.fillRect((int) attachedGameObject.getPosition().getX(), (int) attachedGameObject.getPosition().getY(),width,height);
    }

    @Override
    public void drawShape(Graphics g) {
        g.fillRect((int) attachedGameObject.getPosition().getX(),
                (int) attachedGameObject.getPosition().getY(),
                width,
                height);
    }
}

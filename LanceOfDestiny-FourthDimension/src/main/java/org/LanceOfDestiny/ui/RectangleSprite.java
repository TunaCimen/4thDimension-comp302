package org.LanceOfDestiny.ui;

import org.LanceOfDestiny.domain.GameObject;

import java.awt.*;

public class RectangleSprite extends Sprite{


    int width;
    int height;

    double angle;

    public RectangleSprite(GameObject attached, Color color,int width,int height){
        super(attached,color);
        this.width = width;
        this.height = height;
        angle = 0;
    }


    @Override
    public void drawShape(Graphics g) {
        g.fillRect((int) attachedGameObject.getPosition().getX(),(int) attachedGameObject.getPosition().getY(), width,height);
    }

    public void setAngle(double angle){
        this.angle = angle;

    }
}

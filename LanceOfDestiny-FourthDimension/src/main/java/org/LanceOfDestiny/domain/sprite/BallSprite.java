package org.LanceOfDestiny.domain.sprite;

import org.LanceOfDestiny.domain.behaviours.GameObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BallSprite extends Sprite {

    int radius;

    static List<BallSprite> ballList = new ArrayList<>();


    public BallSprite(GameObject attachedObject,Color color, int radius ){
        super(attachedObject,color);
        ballList.add(this);
        this.radius = radius;
        this.color  = color;
    }



    @Override
    public void drawShape(Graphics g) {
        g.fillOval((int) attachedGameObject.getPosition().getX() - radius,
        (int) attachedGameObject.getPosition().getY() - radius,
        2* radius,2*radius);
    }


    //super.paintComponent(g);

                //g.setColor(bs.color);
                //g.fillOval((int) bs.attachedGameObject.getPosition().getX(),
                        //(int) bs.attachedGameObject.getPosition().getY(),
                        //bs.radius,bs.radius);



}

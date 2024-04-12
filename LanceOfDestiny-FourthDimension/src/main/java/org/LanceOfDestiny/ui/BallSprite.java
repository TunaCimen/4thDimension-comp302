package org.LanceOfDestiny.ui;

import org.LanceOfDestiny.domain.GameObject;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BallSprite extends JPanel {

    int radius;

    static List<BallSprite> ballList = new ArrayList<>();
    GameObject attachedObject;
    public Color color;

    public BallSprite(GameObject attachedObject, int radius, Color color){
        ballList.add(this);
        this.attachedObject = attachedObject;
        this.radius = radius;
        this.color  =color;
    }




    @Override
    public void paintComponent(Graphics g) {
        //super.paintComponent(g);
        for(BallSprite bs : ballList){
            g.setColor(bs.color);
            g.fillOval((int) bs.attachedObject.getPosition().getX(), (int) bs.attachedObject.getPosition().getY(),bs.radius,bs.radius);
        }
        //g.setColor(color);
        //g.fillOval((int) attachedObject.getPosition().getX(), (int) attachedObject.getPosition().getY(),radius,radius);
    }
}

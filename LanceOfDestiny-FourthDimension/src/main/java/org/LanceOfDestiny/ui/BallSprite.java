package org.LanceOfDestiny.ui;

import javax.swing.*;
import java.awt.*;

public class BallSprite extends JPanel {

    public int x,y,radius;
    public Color color;

    public BallSprite(int x, int y, int radius, Color color){
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.color  =color;
    }



    @Override
    public void paintComponent(Graphics g) {
        System.out.println("Repaint Ball Sprite: " + x);
        super.paintComponent(g);
        g.setColor(color);
        g.fillOval(x,y,radius,radius);
    }
}

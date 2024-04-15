package org.LanceOfDestiny.ui;

import org.LanceOfDestiny.domain.Behaviour;
import org.LanceOfDestiny.domain.GameObject;

import javax.swing.*;
import java.awt.*;

/**
 * Custom drawing JPanel for graphics drawings.
 * paints all the game objects.
 */
public class DrawCanvas extends JPanel {

    public DrawCanvas(){

    }

    @Override
    protected void paintComponent(Graphics g) {
        for(Behaviour behaviour : GameObject.getGameObjects()) {
            if (behaviour.gameObject != null) {
                Sprite bs = behaviour.gameObject.getSprite();
                g.setColor(bs.color);
                bs.drawShape(g);
            }
        }
    }
}

package org.LanceOfDestiny.ui;

import org.LanceOfDestiny.domain.Behaviour;
import org.LanceOfDestiny.domain.GameObject;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Sprite extends JPanel {

    public GameObject attachedGameObject;
    public Color color;


    public Sprite(GameObject attachedGameObject, Color color){
        this.attachedGameObject = attachedGameObject;
        this.color = color;
    }


    public void drawShape(Graphics g){
        g.fillRect((int) attachedGameObject.getPosition().getX()
                , (int) attachedGameObject.getPosition().getY(),
                20,20);
    }

    @Override
    public void paintComponent(Graphics g) {
        for(Behaviour behaviour : GameObject.getGameObjects()) {
            if (behaviour.gameObject != null) {
                Sprite bs = behaviour.gameObject.sprite();
                g.setColor(bs.color);
                bs.drawShape(g);
            }
        }
    }


}

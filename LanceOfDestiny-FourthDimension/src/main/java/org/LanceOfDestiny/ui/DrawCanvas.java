package org.LanceOfDestiny.ui;

import org.LanceOfDestiny.domain.behaviours.Behaviour;
import org.LanceOfDestiny.domain.behaviours.GameObject;
import org.LanceOfDestiny.domain.spells.Canon;
import org.LanceOfDestiny.domain.sprite.BallSprite;
import org.LanceOfDestiny.domain.sprite.RectangleSprite;
import org.LanceOfDestiny.domain.sprite.Sprite;

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
                Sprite gameObjectSprite = behaviour.gameObject.getSprite();
                if(!gameObjectSprite.isVisible) continue;
                if(gameObjectSprite.attachedGameObject instanceof Canon){
                    System.out.println("Cannon drawing");
                    System.out.println(gameObjectSprite.attachedGameObject.getSprite().getClass());
                    System.out.println(gameObjectSprite.attachedGameObject.getSprite().color);
                    System.out.println(gameObjectSprite.attachedGameObject.getPosition().getX());
                    System.out.println(gameObjectSprite.attachedGameObject.getPosition().getY());
                }

                g.setColor(gameObjectSprite.color);
                gameObjectSprite.drawShape(g);
                if(gameObjectSprite.getImage() != null){
                    if(gameObjectSprite instanceof BallSprite){
                        g.drawImage(gameObjectSprite.getImage()
                                , ((int) gameObjectSprite.attachedGameObject.getPosition().getX() - gameObjectSprite.width())
                                , ((int) gameObjectSprite.attachedGameObject.getPosition().getY() - gameObjectSprite.height())
                                ,null);
                    } else if (gameObjectSprite instanceof RectangleSprite) {
                        g.drawImage(gameObjectSprite.getImage()
                                , ((int) gameObjectSprite.attachedGameObject.getPosition().getX())
                                , ((int) gameObjectSprite.attachedGameObject.getPosition().getY())
                                ,null);
                    }

                }
            }
        }
    }
}

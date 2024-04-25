package org.LanceOfDestiny.domain.sprite;

import org.LanceOfDestiny.domain.behaviours.GameObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BallSprite extends Sprite {

    static List<BallSprite> ballList = new ArrayList<>();
    private int radius;

    public BallSprite(GameObject attachedObject, Color color, int radius) {
        super(attachedObject, color);
        ballList.add(this);
        this.radius = radius;
        this.color = color;
    }

    @Override
    public void drawShape(Graphics g) {
        g.fillOval((int) attachedGameObject.getPosition().getX() - radius,
                (int) attachedGameObject.getPosition().getY() - radius,
                2 * radius, 2 * radius);
        if(image != null){
            g.drawImage(getImage()
                    , ((int) attachedGameObject.getPosition().getX() - width())
                    , ((int) attachedGameObject.getPosition().getY() - height())
                    ,null);
        }

    }

    @Override
    public int width() {
        return radius;
    }

    @Override
    public int height() {
        return radius;
    }
}

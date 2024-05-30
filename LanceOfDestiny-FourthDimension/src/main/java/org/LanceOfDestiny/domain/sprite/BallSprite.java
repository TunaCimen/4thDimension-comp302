package org.LanceOfDestiny.domain.sprite;
import org.LanceOfDestiny.domain.behaviours.GameObject;
import org.LanceOfDestiny.domain.events.Event;
import org.LanceOfDestiny.domain.physics.Vector;

import java.awt.*;
import java.util.*;
import java.util.List;

public class BallSprite extends Sprite {

    static List<BallSprite> ballList = new ArrayList<>();
    private int radius;
    Queue<Vector> lastPositions;
    public void setComet(boolean comet) {
        isComet = comet;
    }

    private boolean isComet;

    public BallSprite(GameObject attachedObject, Color color, int radius) {
        super(attachedObject, color);
        isComet = false;
        ballList.add(this);
        this.radius = radius;
        this.color = color;
        lastPositions = new ArrayDeque<>();
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
        if(isComet)
        animate(g);
    }

    private void animate(Graphics g) {
        lastPositions.add(attachedGameObject.getPosition());
        if(lastPositions.size() == 5)lastPositions.remove();
        int i = 0;
        int tailSize = lastPositions.size();
        for (Vector e : lastPositions) {
            int currentRadius = (int) (radius * Math.pow(0.8, tailSize - i));
            g.fillOval((int) e.getX() - currentRadius,
                    (int) e.getY() - currentRadius,
                    2 * currentRadius, 2 * currentRadius);
            if (image != null) {
                g.drawImage(getImage(),
                        (int) e.getX() - currentRadius,
                        (int) e.getY() - currentRadius,
                        2 * currentRadius, 2 * currentRadius, null);
            }
            i++;
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

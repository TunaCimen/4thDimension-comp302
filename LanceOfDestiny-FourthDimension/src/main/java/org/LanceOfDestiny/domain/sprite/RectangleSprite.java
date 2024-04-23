package org.LanceOfDestiny.domain.sprite;

import org.LanceOfDestiny.domain.behaviours.GameObject;
import org.LanceOfDestiny.domain.physics.Vector;
import org.LanceOfDestiny.domain.spells.Canon;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class RectangleSprite extends Sprite{


    int width;
    int height;
    public GameObject parentObject;


    public RectangleSprite(GameObject attached, Color color,int width,int height){
        super(attached,color);
        this.width = width;
        this.height = height;
        parentObject = attached;
    }

    private void drawNumber(Graphics g, String number) {
        if(!isVisible) return;
        g.setColor(Color.BLACK); // Text color
        g.setFont(new Font("Arial", Font.BOLD, 12)); // Text font

        String numberText = String.valueOf(number);
        FontMetrics metrics = g.getFontMetrics();
        int x = (int) attachedGameObject.getPosition().getX() + (width - metrics.stringWidth(numberText)) / 2;
        int y = (int) attachedGameObject.getPosition().getY() + ((height - metrics.getHeight()) / 2) + metrics.getAscent();

        g.drawString(numberText, x, y);
    }


    @Override
    public void drawShape(Graphics g) {
        if(!isVisible) return;
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform originalTransform = ((Graphics2D) g).getTransform();

        // Calculate the center of the rectangle
        if(attachedGameObject.getPosition() == null) return;

        //Calculate rotation anchor

        int x = (int) parentObject.getPosition().getX();
        int y = (int) parentObject.getPosition().getY();
        int centerX = x + parentObject.getSprite().width() / 2;
        int centerY = y + parentObject.getSprite().height() / 2;

        // Perform the rotation
        g2d.rotate(attachedGameObject.getAngle(), centerX, centerY);
        g.fillRect((int) attachedGameObject.getPosition().getX(), (int) attachedGameObject.getPosition().getY(), width,height);
        if(image != null){
            g.drawImage(getImage()
                    , ((int) attachedGameObject.getPosition().getX())
                    , ((int) attachedGameObject.getPosition().getY())
                    ,null);
        }
        ((Graphics2D) g).setTransform(originalTransform);
        if (number != null) {
            drawNumber(g, number);
        }
    }

    @Override
    public int width() {
        return width;
    }

    @Override
    public int height() {
        return height;
    }


}

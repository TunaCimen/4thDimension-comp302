package org.LanceOfDestiny.domain.sprite;

import org.LanceOfDestiny.domain.behaviours.GameObject;
import org.LanceOfDestiny.domain.physics.Vector;
import org.LanceOfDestiny.domain.spells.Canon;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class RectangleSprite extends Sprite{


    int width;
    int height;

    public int anchorXShift;
    public int anchorYShift;


    public RectangleSprite(GameObject attached, Color color,int width,int height){
        super(attached,color);
        this.width = width;
        this.height = height;
        anchorXShift = 0;
        anchorYShift = 0;
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
        int x = (int) attachedGameObject.getPosition().getX();
        int y = (int) attachedGameObject.getPosition().getY();
        int centerX = x + width / 2 + anchorXShift;
        int centerY = y + height / 2 + anchorYShift;


        // Perform the rotation
        g2d.rotate(attachedGameObject.getAngle(), centerX, centerY);
        g.fillRect((int) attachedGameObject.getPosition().getX(), (int) attachedGameObject.getPosition().getY(), width,height);

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

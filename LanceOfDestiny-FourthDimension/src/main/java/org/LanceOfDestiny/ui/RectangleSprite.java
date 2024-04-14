package org.LanceOfDestiny.ui;

import org.LanceOfDestiny.domain.GameObject;

import java.awt.*;

public class RectangleSprite extends Sprite{


    int width;
    int height;


    public RectangleSprite(GameObject attached, Color color,int width,int height){
        super(attached,color);
        this.width = width;
        this.height = height;
    }

    private void drawNumber(Graphics g, int number) {
        g.setColor(Color.BLACK); // Text color
        g.setFont(new Font("Arial", Font.BOLD, 12)); // Text font

        String numberText = String.valueOf(number);
        FontMetrics metrics = g.getFontMetrics();
        int x = (int) attachedGameObject.getPosition().getX() + (20 - metrics.stringWidth(numberText)) / 2;
        int y = (int) attachedGameObject.getPosition().getY() + ((20 - metrics.getHeight()) / 2) + metrics.getAscent();

        g.drawString(numberText, x, y);
    }


    @Override
    public void drawShape(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        // Calculate the center of the rectangle
        int x = (int) attachedGameObject.getPosition().getX();
        int y = (int) attachedGameObject.getPosition().getY();
        int centerX = x + width / 2;
        int centerY = y + height / 2;

        // Perform the rotation
        g2d.rotate(attachedGameObject.getAngle(), centerX, centerY);
        g.fillRect((int) attachedGameObject.getPosition().getX(), (int) attachedGameObject.getPosition().getY(), width,height);

        if (number != null) {
            drawNumber(g, number);
        }
    }



}

package org.LanceOfDestiny.ui.UIElements;

import org.LanceOfDestiny.Animation.LinearInterpolation;
import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.sprite.Drawable;
import org.LanceOfDestiny.ui.UIUtilities.Animatable;

import java.awt.*;

public class TextUI implements Animatable, Drawable {

    private int locX;
    private int locY;
    private Font font;
    private Color color;
    private String text;
    public TextUI(String text, Color color, Font font, float size, int locX, int locY){
        this.locX = locX;
        this.locY = locY;
        this.color = color;
        this.text = text;
        this.font = font.deriveFont(size);
        //setAnimationBehaviour(new LinearInterpolation(15, locX, this::getLocX, this::setLocX));
    }
    @Override
    public void drawShape(Graphics g) {
        g.setColor(color);
        g.setFont(font);
        g.drawString(text, locX- font.getSize(), locY);
    }

    @Override
    public int width() {
        return 0;
    }

    @Override
    public int height() {
        return 0;
    }

    public int getLocX() {
        return locX;
    }

    public void setLocX(int locX) {
        this.locX = locX;
    }

    public void setText(String text) {
        System.out.println("Changed the daaaamn text" );
        this.text = text;
    }


}

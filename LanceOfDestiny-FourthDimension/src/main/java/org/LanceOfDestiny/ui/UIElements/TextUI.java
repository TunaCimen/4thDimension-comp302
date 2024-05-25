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

    private int opacity;
    public TextUI(String text, Color color, Font font, float size, int locX, int locY, int opacity){
        this.locX = locX;
        this.locY = locY;
        this.color = color;
        this.text = text;
        this.font = font.deriveFont(size);
        this.opacity = Math.max(Math.min(opacity,100),0);
        //setAnimationBehaviour(new LinearInterpolation(15, locX, this::getLocX, this::setLocX));
    }
    @Override
    public void drawShape(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setFont(font);
            FontMetrics metrics = g2d.getFontMetrics(font);
            int textWidth = metrics.stringWidth(text);

            g2d.setColor(color);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity / 100f));
            System.out.println("Opacity: " + opacity);
            g2d.drawString(text, locX - textWidth/2, locY);
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

    public int getOpacity() {
        return opacity;
    }

    public void setOpacity(int opacity) {
        this.opacity = Math.max(Math.min(opacity,100),0);
    }
}

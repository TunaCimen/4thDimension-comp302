package org.LanceOfDestiny.ui.UIUtilities;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class BackgroundJPanel extends JPanel {

    Image background;
    public BackgroundJPanel(BufferedImage image){
        this.background = image;
    }

    public BackgroundJPanel(){

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(background==null)return;
        g.drawImage(background,0,0,this.getWidth(),this.getHeight(),this);
    }

    public void setBackground(Image background) {
        this.background = background;
    }
}

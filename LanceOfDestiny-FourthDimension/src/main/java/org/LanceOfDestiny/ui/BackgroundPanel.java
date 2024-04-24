package org.LanceOfDestiny.ui;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.sprite.ImageLibrary;
import org.LanceOfDestiny.domain.sprite.ImageOperations;

import javax.swing.*;
import java.awt.*;

public class BackgroundPanel extends JPanel {

    private final Image backgroundImage;

    public BackgroundPanel() {
        var width = Constants.SCREEN_WIDTH;
        var height = Constants.SCREEN_HEIGHT;
        this.backgroundImage = new ImageIcon(ImageOperations.resizeImage(ImageLibrary.Background.getImage(), width, height)).getImage();
        setVisible(true);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, this);
    }
}

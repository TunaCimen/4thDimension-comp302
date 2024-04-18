package org.LanceOfDestiny.domain.sprite;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageOperations{

    public static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = resizedImage.createGraphics();

        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);

        g2d.dispose();
        return resizedImage;
    }

    public static BufferedImage resizeImageToSprite(BufferedImage originalImage, Sprite sprite) {
        int targetWidth = sprite.width();
        int targetHeight = sprite.height();
        return resizeImage(originalImage,targetWidth,targetHeight);
    }

    public static ImageIcon reducedTransparencyImageIcon(ImageIcon originalIcon){
        BufferedImage img = new BufferedImage(
                originalIcon.getIconWidth(),
                originalIcon.getIconHeight(),
                BufferedImage.TYPE_INT_ARGB);

        // Draw the image with reduced transparency
        Graphics2D g2d = img.createGraphics();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f)); // Set transparency level (0.0 to 1.0)
        g2d.drawImage(originalIcon.getImage(), 0, 0, null);
        g2d.dispose();
        return new ImageIcon(img);
    }

}

package org.LanceOfDestiny.domain.sprite;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageOperations{

    public static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        // Create a new BufferedImage of the desired size
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);

        // Get a Graphics2D object to draw on the BufferedImage
        Graphics2D g2d = resizedImage.createGraphics();

        // Set rendering hints for quality
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw the original image, scaled to the new size
        g2d.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);

        // Dispose of the graphics context to release system resources
        g2d.dispose();

        // Return the resized image
        return resizedImage;
    }
}

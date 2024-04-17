package org.LanceOfDestiny.domain.sprite;

import org.LanceOfDestiny.domain.spells.RewardBox;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public enum ImageLibrary {

    FireBall("LanceOfDestiny-FourthDimension/src/main/java/org/LanceOfDestiny/Resources/Image/FireBallImage.png"),
    RewardingBarrier("LanceOfDestiny-FourthDimension/src/main/java/org/LanceOfDestiny/Resources/Image/200Greengem 2.png"),
    Heart("LanceOfDestiny-FourthDimension/src/main/java/org/LanceOfDestiny/Resources/Image/200Heart.png"),
    MagicalStaff("LanceOfDestiny-FourthDimension/src/main/java/org/LanceOfDestiny/Resources/Image/200Player.png"),
    RewardBox("LanceOfDestiny-FourthDimension/src/main/java/org/LanceOfDestiny/Resources/Image/RewardBox.png");

    private final BufferedImage image;

    ImageLibrary(String path){
        try {
            this.image = ImageIO.read(new File(path));
        } catch (IOException e) {
            throw new RuntimeException(String.format("Path : %s doesnt exist", path));
        }
    }

    public BufferedImage getImage(){
        return image;
    }
}

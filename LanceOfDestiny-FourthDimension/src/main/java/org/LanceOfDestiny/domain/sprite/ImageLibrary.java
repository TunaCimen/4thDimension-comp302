package org.LanceOfDestiny.domain.sprite;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public enum ImageLibrary {

    FireBall("Image/Fireball.png"),
    RewardingBarrier("Image/200Greengem.png"),
    Heart("Image/200Heart.png"),
    MagicalStaff("Image/200Player.png"),
    RewardBox("Image/RewardBox.png"),
    CannonSpell("Image/CannonSpell.png");

    private final BufferedImage image;

    ImageLibrary(String path){
        try {
            
            File f = new File(path);
            this.image = ImageIO.read(f);
            if(this.image == null)throw new RuntimeException("Image not found but loaded");
        } catch (IOException e) {
            throw new RuntimeException(String.format("Path : %s doesnt exist", path));
        }
    }

    public BufferedImage getImage(){
        return image;
    }
}

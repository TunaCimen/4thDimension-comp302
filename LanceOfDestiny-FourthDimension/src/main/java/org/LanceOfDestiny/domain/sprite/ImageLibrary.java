package org.LanceOfDestiny.domain.sprite;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public enum ImageLibrary {

    FireBall("/Users/feridun/Desktop/Workspace/Java/4thDimension-comp302/LanceOfDestiny-FourthDimension/Image/200Fireball.png"),
    RewardingBarrier("/Users/feridun/Desktop/Workspace/Java/4thDimension-comp302/LanceOfDestiny-FourthDimension/Image/200Greengem.png"),
    Heart("/Users/feridun/Desktop/Workspace/Java/4thDimension-comp302/LanceOfDestiny-FourthDimension/Image/200Heart.png"),
    MagicalStaff("/Users/feridun/Desktop/Workspace/Java/4thDimension-comp302/LanceOfDestiny-FourthDimension/Image/200Player.png"),
    RewardBox("/Users/feridun/Desktop/Workspace/Java/4thDimension-comp302/LanceOfDestiny-FourthDimension/Image/RewardBox.png"),
    CannonSpell("/Users/feridun/Desktop/Workspace/Java/4thDimension-comp302/LanceOfDestiny-FourthDimension/Image/CannonSpell.png"),
    OverWhelmingSpell("/Users/feridun/Desktop/Workspace/Java/4thDimension-comp302/LanceOfDestiny-FourthDimension/Image/Overwhelm.png"),
    ExpansionSpell("/Users/feridun/Desktop/Workspace/Java/4thDimension-comp302/LanceOfDestiny-FourthDimension/Image/expansion.png"),
    ExplosiveBarrier("/Users/feridun/Desktop/Workspace/Java/4thDimension-comp302/LanceOfDestiny-FourthDimension/Image/bomb.png"),
    SimpleBarrier("/Users/feridun/Desktop/Workspace/Java/4thDimension-comp302/LanceOfDestiny-FourthDimension/Image/BlueGem.png");

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

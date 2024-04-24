package org.LanceOfDestiny.domain.sprite;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public enum ImageLibrary {

    FireBall("Image/200Fireball.png"),
    FireBallOverwhelmed("Image/FireballOverwhelmed.png"),
    RewardingBarrier("Image/200Greengem.png"),
    Heart("Image/200Heart.png"),
    MagicalStaff("Image/200Player.png"),
    RewardBox("Image/RewardBox.png"),
    CannonSpell("Image/CannonSpell.png"),
    OverWhelmingSpell("Image/Overwhelm.png"),
    ExpansionSpell("Image/expansion.png"),
    ExplosiveBarrier("Image/bomb.png"),
    SimpleBarrier("Image/BlueGem.png"),
    ReinforcedBarrier("Image/2_health_firm.png"),
    Canon("Image/Canon.png"),
    Background("Image/Background.png");

    private final BufferedImage image;

    ImageLibrary(String path){

        try {
            var userDirectory = System.getProperty("user.dir");
            if(userDirectory.endsWith("/LanceOfDestiny-FourthDimension")){
                var f = new File(path).getAbsolutePath();
                this.image = ImageIO.read(new File(f));
            }
            else {
                path = userDirectory + "/LanceOfDestiny-FourthDimension" + "/" + path;
                this.image = ImageIO.read(new File(path));
            }
            if(this.image == null)throw new RuntimeException("Image not found but loaded");
        } catch (IOException e) {
            throw new RuntimeException(String.format("Path : %s doesnt exist", path));
        }
    }

    public BufferedImage getImage(){
        return image;
    }
}

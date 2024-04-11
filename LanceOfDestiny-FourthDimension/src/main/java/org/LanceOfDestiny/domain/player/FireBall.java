package org.LanceOfDestiny.domain.player;

import org.LanceOfDestiny.domain.GameObject;
import org.LanceOfDestiny.ui.BallSprite;

<<<<<<< Updated upstream
import javax.swing.*;
=======
>>>>>>> Stashed changes
import java.awt.*;

public class FireBall extends GameObject {

<<<<<<< Updated upstream
    BallSprite bs;

    public FireBall(){
        bs = new BallSprite(40,40,40, Color.red);
    }
    @Override
    public JPanel sprite() {
        return bs;
    }
=======
    BallSprite sprite = new BallSprite(100,100,50, Color.RED);

    public FireBall(){
        super();
        transform.setSprite(sprite);
    }


>>>>>>> Stashed changes
}

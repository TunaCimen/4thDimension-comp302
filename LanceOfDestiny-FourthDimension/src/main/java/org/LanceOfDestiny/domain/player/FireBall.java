package org.LanceOfDestiny.domain.player;

import org.LanceOfDestiny.domain.GameObject;
import org.LanceOfDestiny.ui.BallSprite;

import javax.swing.*;
import java.awt.*;

public class FireBall extends GameObject {

    BallSprite bs;

    public FireBall(){
        bs = new BallSprite(40,40,40, Color.red);
    }
    @Override
    public JPanel sprite() {
        return bs;
    }
}

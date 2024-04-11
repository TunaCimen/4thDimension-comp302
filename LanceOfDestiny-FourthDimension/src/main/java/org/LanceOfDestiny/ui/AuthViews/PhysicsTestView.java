package org.LanceOfDestiny.ui.AuthViews;

import org.LanceOfDestiny.domain.player.FireBall;
import org.LanceOfDestiny.ui.BallSprite;
import org.LanceOfDestiny.ui.Window;

import javax.swing.*;
import java.awt.*;


public class PhysicsTestView extends JFrame implements Window {

    FireBall fb;

    public PhysicsTestView(){
        new FireBall();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        fb.sprite().paint(g);

    }

    @Override
    public void createAndShowUI() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(new Dimension(750,750));
        setDefaultLookAndFeelDecorated(true);
        setResizable(false);
        setVisible(true);

    }
}

package org.LanceOfDestiny.ui.AuthViews;

import org.LanceOfDestiny.domain.player.FireBall;
import org.LanceOfDestiny.ui.BallSprite;
import org.LanceOfDestiny.ui.Window;

import javax.swing.*;
import java.awt.*;


public class PhysicsTestView extends JFrame implements Window {

<<<<<<< Updated upstream
    FireBall fb;

    public PhysicsTestView(){
        new FireBall();
=======
    BallSprite ball1,ball2;
    FireBall fb;

    public PhysicsTestView(){
        ball1 = new BallSprite(25,25,20,Color.BLUE);
        ball2 = new BallSprite(730,730,20,Color.RED);
        fb = new FireBall();
>>>>>>> Stashed changes
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
<<<<<<< Updated upstream
        fb.sprite().paint(g);

=======
        ball1.paint(g);
        ball2.paint(g);
        fb.transform.getSprite().paint(g);
>>>>>>> Stashed changes
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

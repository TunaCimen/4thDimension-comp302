package org.LanceOfDestiny.ui.AuthViews;

import org.LanceOfDestiny.domain.physics.Vector;
import org.LanceOfDestiny.domain.Looper.GameLooper;
import org.LanceOfDestiny.domain.Looper.LoopExecutor;
import org.LanceOfDestiny.domain.player.FireBall;
import org.LanceOfDestiny.domain.player.MagicalStaff;
import org.LanceOfDestiny.ui.Window;
import javax.swing.*;
import java.awt.*;


public class PhysicsTestView extends JFrame implements Window {

    FireBall fb;
    LoopExecutor loopExecutor = new LoopExecutor();
    public PhysicsTestView() {
        fb = new FireBall(new Vector(20,20));
        GameLooper gameLooper = new GameLooper();
        loopExecutor.setLooper(gameLooper);
    }

    @Override
    public void createAndShowUI() {
        add(fb.sprite());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(new Dimension(750,750));
        setDefaultLookAndFeelDecorated(true);
        setResizable(false);
        setVisible(true);
        loopExecutor.start();

    }
}

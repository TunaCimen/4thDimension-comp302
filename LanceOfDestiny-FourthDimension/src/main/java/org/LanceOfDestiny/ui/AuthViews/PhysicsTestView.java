package org.LanceOfDestiny.ui.AuthViews;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.managers.InputManager;
import org.LanceOfDestiny.domain.physics.Vector;
import org.LanceOfDestiny.domain.Looper.GameLooper;
import org.LanceOfDestiny.domain.Looper.LoopExecutor;
import org.LanceOfDestiny.domain.player.FireBall;
import org.LanceOfDestiny.domain.player.MagicalStaff;
import org.LanceOfDestiny.ui.Window;
import javax.swing.*;
import java.awt.*;
import java.util.Arrays;


public class PhysicsTestView extends JFrame implements Window {

    FireBall fb;
    FireBall fb2;

    FireBall fb3;

    MagicalStaff magicalStaff;



    LoopExecutor loopExecutor = new LoopExecutor();
    public PhysicsTestView() {
        fb = new FireBall(new Vector(50,50));
        fb2 = new FireBall(new Vector(500,500));

        magicalStaff = new MagicalStaff(new Vector(200,700));
        GameLooper gameLooper = new GameLooper();
        loopExecutor.setLooper(gameLooper);
    }



    @Override
    public void createAndShowUI() {
        addKeyListener(InputManager.getInstance());
        add(fb.sprite());
        //add(fb3.sprite());
        add(fb2.sprite());
        add(magicalStaff.sprite());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(new Dimension(Constants.SCREEN_WIDTH,Constants.SCREEN_HEIGHT));
        setDefaultLookAndFeelDecorated(true);
        setResizable(false);
        setVisible(true);
        loopExecutor.start();

    }
}

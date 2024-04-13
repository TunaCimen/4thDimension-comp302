package org.LanceOfDestiny.ui.AuthViews;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.barriers.Barrier;
import org.LanceOfDestiny.domain.barriers.BarrierFactory;
import org.LanceOfDestiny.domain.barriers.BarrierTypes;
import org.LanceOfDestiny.domain.managers.InputManager;
import org.LanceOfDestiny.domain.physics.ColliderFactory;
import org.LanceOfDestiny.domain.physics.ColliderType;
import org.LanceOfDestiny.domain.physics.Vector;
import org.LanceOfDestiny.domain.Looper.GameLooper;
import org.LanceOfDestiny.domain.Looper.LoopExecutor;
import org.LanceOfDestiny.domain.player.FireBall;
import org.LanceOfDestiny.domain.player.MagicalStaff;
import org.LanceOfDestiny.ui.Window;
import javax.swing.*;
import java.awt.*;


public class PhysicsTestView extends JFrame implements Window {

    MagicalStaff magicalStaff;

    LoopExecutor loopExecutor = new LoopExecutor();
    public PhysicsTestView() {

        new FireBall(new Vector(Constants.SCREEN_WIDTH/2,Constants.SCREEN_HEIGHT - 40), new Vector(0,7));
        //fb2 = new FireBall(new Vector(50+Constants.FIREBALL_RADIUS,90+Constants.FIREBALL_RADIUS),ColliderType.STATIC);
        //fb3 = new FireBall(new Vector(120+Constants.FIREBALL_RADIUS,490+Constants.FIREBALL_RADIUS),ColliderType.STATIC);
        //fb4 = new FireBall(new Vector(250+Constants.FIREBALL_RADIUS,190+Constants.FIREBALL_RADIUS),ColliderType.STATIC);
        //fb3 = new FireBall(new Vector(3,500));
        //fb3 = new FireBall(new Vector(Constants.SCREEN_WIDTH-3*Constants.FIREBALL_RADIUS,Constants.SCREEN_HEIGHT-4*Constants.FIREBALL_RADIUS), new Vector(3,2));
        for (int i = 10; i < Constants.SCREEN_WIDTH - 10; i += 30) {
            for (int j = 10; j < Constants.SCREEN_HEIGHT - 400; j += 30) {
                BarrierFactory.getInstance().createBarrier(new Vector(i , j), BarrierTypes.REINFORCED);
            }
        }
        magicalStaff = new MagicalStaff(new Vector(350,500));

        GameLooper gameLooper = new GameLooper();
        loopExecutor.setLooper(gameLooper);
    }



    @Override
    public void createAndShowUI() {
        addKeyListener(InputManager.getInstance());
        //add(fb.sprite());
        //add(fb3.sprite());
        //add(fb4.sprite());
        //add(fb2.sprite());
        add(magicalStaff.getSprite());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(new Dimension(Constants.SCREEN_WIDTH,Constants.SCREEN_HEIGHT));
        setDefaultLookAndFeelDecorated(true);
        setResizable(false);
        setVisible(true);
        loopExecutor.start();

    }
}

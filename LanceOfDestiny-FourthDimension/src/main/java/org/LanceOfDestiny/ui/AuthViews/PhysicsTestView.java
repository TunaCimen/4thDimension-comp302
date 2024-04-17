package org.LanceOfDestiny.ui.AuthViews;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.barriers.Barrier;
import org.LanceOfDestiny.domain.barriers.BarrierFactory;
import org.LanceOfDestiny.domain.barriers.BarrierTypes;
import org.LanceOfDestiny.domain.barriers.SimpleBarrier;
import org.LanceOfDestiny.domain.managers.InputManager;
import org.LanceOfDestiny.domain.physics.ColliderFactory;
import org.LanceOfDestiny.domain.physics.ColliderType;
import org.LanceOfDestiny.domain.physics.Vector;
import org.LanceOfDestiny.domain.Looper.GameLooper;
import org.LanceOfDestiny.domain.Looper.LoopExecutor;
import org.LanceOfDestiny.domain.player.FireBall;
import org.LanceOfDestiny.domain.player.MagicalStaff;
import org.LanceOfDestiny.ui.DrawCanvas;
import org.LanceOfDestiny.ui.Window;

import javax.swing.*;
import java.awt.*;

public class PhysicsTestView extends JFrame implements Window {
    MagicalStaff magicalStaff;
    LoopExecutor loopExecutor = new LoopExecutor();
    Barrier barrier;
    DrawCanvas drawCanvas;
    public PhysicsTestView() {
        drawCanvas = new DrawCanvas();
        GameLooper gameLooper = new GameLooper(drawCanvas);
        loopExecutor.setLooper(gameLooper);
    }

    public void initializeTestObjects(){
        new FireBall();

        // Generate barriers
        for (int i = 20; i < Constants.SCREEN_WIDTH - 10; i += 30) {
            for (int j = 10; j < Constants.SCREEN_HEIGHT - 400; j += 30) {
                if (j == 190) {  // Check if it's the first row
                    BarrierFactory.createBarrier(new Vector(i, j), BarrierTypes.EXPLOSIVE);
                } else {
                    BarrierFactory.createBarrier(new Vector(i, j), BarrierTypes.SIMPLE);
                }
            }
        }
        barrier = new SimpleBarrier(new Vector(500,500));
        magicalStaff = new MagicalStaff();

    }

    @Override
    public void createAndShowUI() {
        add(drawCanvas);
        initializeTestObjects();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
        setDefaultLookAndFeelDecorated(true);
        setResizable(false);
        setVisible(true);
        addKeyListener(InputManager.getInstance());
        loopExecutor.start();
    }
}

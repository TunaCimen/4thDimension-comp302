package org.LanceOfDestiny.domain.managers;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.Looper.GameLooper;
import org.LanceOfDestiny.domain.Looper.LoopExecutor;
import org.LanceOfDestiny.domain.barriers.BarrierFactory;
import org.LanceOfDestiny.domain.barriers.BarrierTypes;
import org.LanceOfDestiny.domain.physics.Vector;
import org.LanceOfDestiny.domain.player.FireBall;
import org.LanceOfDestiny.domain.player.MagicalStaff;
import org.LanceOfDestiny.domain.player.Player;
import org.LanceOfDestiny.ui.DrawCanvas;
import org.LanceOfDestiny.ui.GameViews.Status;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SessionManager {

    private static SessionManager instance;

    public Status currentMode;

    private Player player;
    private MagicalStaff magicalStaff;
    private FireBall fireBall;

    public LoopExecutor loopExecutor = new LoopExecutor();
    public DrawCanvas drawCanvas;
    GameLooper gameLooper;



    private SessionManager() {
        drawCanvas = new DrawCanvas();
        gameLooper = new GameLooper(drawCanvas);
        currentMode = Status.EditMode;
        loopExecutor.setLooper(gameLooper);
    }

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void initializeSession() {
        //this.player = new Player();
        fireBall = new FireBall(Constants.FIREBALL_POSITION, new Vector(0, 5));
        // Generate barriers
        for (int i = 10; i < Constants.SCREEN_WIDTH - 10; i += 30) {
            for (int j = 10; j < Constants.SCREEN_HEIGHT - 400; j += 30) {
                if (j == 190) {  // Check if it's the first row
                    BarrierFactory.createBarrier(new Vector(i, j), BarrierTypes.EXPLOSIVE);
                } else {
                    BarrierFactory.createBarrier(new Vector(i, j), BarrierTypes.REINFORCED);
                }
            }
        }
        magicalStaff = new MagicalStaff(Constants.STAFF_POSITION);
    }

    public MagicalStaff getMagicalStaff() {
        return magicalStaff;
    }

    public Player getPlayer() {
        return player;
    }

    public FireBall getFireBall() {
        return fireBall;
    }

    public void startSession(JFrame frame) {
        initializeSession();
        JPanel buttonPanel = new JPanel();
        DrawCanvas drawCanvas = new DrawCanvas();
        JButton button = new JButton("Start Game");
        button.addActionListener(e -> {
            System.out.println("Session Started");
            //button.setVisible(false);

        });
        buttonPanel.add(button);

        Container cp = frame.getContentPane();
        cp.add(frame.add(drawCanvas),BorderLayout.CENTER);
        //cp.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
        loopExecutor.start();

    }
}

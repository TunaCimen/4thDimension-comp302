package org.LanceOfDestiny.ui;

import org.LanceOfDestiny.domain.barriers.BarrierFactory;
import org.LanceOfDestiny.domain.behaviours.Behaviour;
import org.LanceOfDestiny.domain.behaviours.GameObject;
import org.LanceOfDestiny.domain.managers.BarrierManager;
import org.LanceOfDestiny.domain.managers.SessionManager;
import org.LanceOfDestiny.domain.managers.Status;
import org.LanceOfDestiny.domain.physics.Vector;
import org.LanceOfDestiny.domain.spells.Canon;
import org.LanceOfDestiny.domain.sprite.BallSprite;
import org.LanceOfDestiny.domain.sprite.RectangleSprite;
import org.LanceOfDestiny.domain.sprite.Sprite;
import org.LanceOfDestiny.ui.GameViews.GameView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Custom drawing JPanel for graphics drawings.
 * paints all the game objects.
 */
public class DrawCanvas extends JPanel {
    public DrawCanvas() {
        setupMouseListener();
    }

    private void setupMouseListener() {
        MouseAdapter mouseHandler = new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                if (SessionManager.getInstance().getStatus() == Status.EditMode){
                    handleMousePress(e);
                    //debug
                    System.out.println("Mouse Pressed");
                }
                else return;
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (SessionManager.getInstance().getStatus() == Status.EditMode){
                    handleMouseClick(e);
                    //debug
                    System.out.println("Mouse Clicked");
                }
                else return;

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (SessionManager.getInstance().getStatus() == Status.EditMode){
                    handleMouseRelease(e);
                    //debug
                    System.out.println("Mouse Released");
                }
                else return;
            }
        };
        addMouseListener(mouseHandler);
    }

    public void handleMousePress(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        if (BarrierManager.getInstance().getBarrierByLocation(x, y) != null) {
            BarrierManager.getInstance().setClickedBarrier(BarrierManager.getInstance().getBarrierByLocation(x, y));
            BarrierManager.getInstance().setOldLocationOfBarrier(BarrierManager.getInstance().getBarrierByLocation(x, y).getPosition());
        }
    }

    private void handleMouseRelease(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        if (BarrierManager.getInstance().getBarrierByLocation(x, y) == null) {
            if (BarrierManager.getInstance().validateBarrierPlacement(x, y) && !(BarrierManager.getInstance().isBarrierColliding(x, y))) {
                BarrierManager.getInstance().getClickedBarrier().setPosition(new Vector(x, y));
                //todo dont know if it will repaint the barrier check it later
                org.LanceOfDestiny.ui.GameViews.GameView gameView = GameView.getInstance(SessionManager.getInstance());
                gameView.reinitializeUI();

            }
        }
        else {
            if (SwingUtilities.isRightMouseButton(e)) {
                //do something
            }
        }
    }

    private void handleMouseClick(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        if (e.getClickCount() == 2) {

            //debug
            System.out.println("Double Click is detected not handleded yet");
            handleDoubleClick(x, y);

        } else if (SwingUtilities.isRightMouseButton(e)) {

            //debug
            System.out.println("Right Click is detected not handleded yet");
            handleRightClick(x, y);
        }
    }

    private void handleDoubleClick(int x, int y) {
        if (BarrierManager.getInstance().getBarrierByLocation(x, y) == null) {
            BarrierManager.getInstance().addBarrier(BarrierFactory.createBarrier(new Vector(x, y), BarrierManager.getInstance().getSelectedBarrierType()));

            //debug
            System.out.println("Barrier added: " + (BarrierManager.getInstance().getSelectedBarrierType()).toString() );
        }
    }

    private void handleRightClick(int x, int y) {
        if (BarrierManager.getInstance().getBarrierByLocation(x, y) != null) {
            BarrierManager.getInstance().removeBarrier(BarrierManager.getInstance().getBarrierByLocation(x, y));

            //debug
            System.out.println("Barrier removed: " + (BarrierManager.getInstance().getSelectedBarrierType()).toString() );
        }
    }




    @Override
    protected void paintComponent(Graphics g) {
        for(Behaviour behaviour : GameObject.getGameObjects()) {
            if (behaviour.gameObject != null) {
                Sprite gameObjectSprite = behaviour.gameObject.getSprite();
                if(!gameObjectSprite.isVisible) continue;
                g.setColor(gameObjectSprite.color);
                gameObjectSprite.drawShape(g);
                if(gameObjectSprite.getImage() != null){
                    if(gameObjectSprite instanceof BallSprite){
                        g.drawImage(gameObjectSprite.getImage()
                                , ((int) gameObjectSprite.attachedGameObject.getPosition().getX() - gameObjectSprite.width())
                                , ((int) gameObjectSprite.attachedGameObject.getPosition().getY() - gameObjectSprite.height())
                                ,null);
                    } else if (gameObjectSprite instanceof RectangleSprite) {
                        g.drawImage(gameObjectSprite.getImage()
                                , ((int) gameObjectSprite.attachedGameObject.getPosition().getX())
                                , ((int) gameObjectSprite.attachedGameObject.getPosition().getY())
                                ,null);
                    }

                }
            }
        }
    }


}

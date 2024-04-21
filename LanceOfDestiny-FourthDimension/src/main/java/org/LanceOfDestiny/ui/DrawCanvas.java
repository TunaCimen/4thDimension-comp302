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
                if (e.getClickCount() > 1) {
                    // Ignore double clicks or more
                    return;}
                else {
                    if (SessionManager.getInstance().getStatus() == Status.EditMode){
                        handleMousePress(e);
                    }
                    else return;
                }
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SessionManager.getInstance().getStatus() == Status.EditMode){
                    handleMouseClick(e);

                }
                else return;

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if ( e.getClickCount() > 1) {
                    // Ignore double clicks or more
                    return;}
                else {
                    if (SessionManager.getInstance().getStatus() == Status.EditMode){
                        handleMouseRelease(e);
                    }
                    else return;
                }

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
            System.out.println("Mouse Press: Barrier is clicked"+ BarrierManager.getInstance().getBarrierByLocation(x, y).toString());
        }
        else {
            System.out.println("Mouse Press: No barrier is clicked");
        }

    }

    private void handleMouseRelease(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        if (BarrierManager.getInstance().getBarrierByLocation(x, y) == null) {
            if (BarrierManager.getInstance().validateBarrierPlacement(x, y) && !(BarrierManager.getInstance().isBarrierColliding(x, y))) {
                if (BarrierManager.getInstance().getClickedBarrier() != null) {
                    BarrierManager.getInstance().getClickedBarrier().setPosition(new Vector(x, y));
                    org.LanceOfDestiny.ui.GameViews.GameView gameView = GameView.getInstance(SessionManager.getInstance());
                    gameView.reinitializeUI();
                    System.out.println("Barrier moved to new location: " + BarrierManager.getInstance().getClickedBarrier().toString() +
                            " from " + BarrierManager.getInstance().getOldLocationOfBarrier().toString() + " to " +
                            BarrierManager.getInstance().getClickedBarrier().getPosition().toString());
                    BarrierManager.getInstance().setClickedBarrier(null);

                } else {
                    System.out.println("No barrier was clicked to move. at : " + x + " " + y);
                }

            }
        }
        else {
            if (SwingUtilities.isRightMouseButton(e)) {
                //do something
            }
        }
    }

    private void handleMouseClick(MouseEvent e) {
        //debug
        System.out.println("Mouse Clicked");

        int x = e.getX();
        int y = e.getY();

        if (e.getClickCount() == 2) {

            handleDoubleClick(x, y);

        } else if (SwingUtilities.isRightMouseButton(e)) {

            handleRightClick(x, y);
        }
    }


    private void handleDoubleClick(int x, int y) {
        //debug
        System.out.println("Double Click is detected ");

        if (BarrierManager.getInstance().getBarrierByLocation(x, y) == null) {
            BarrierManager.getInstance().addBarrier(BarrierFactory.createBarrier(new Vector(x, y), BarrierManager.getInstance().getSelectedBarrierType()));

            //debug
            System.out.println("Barrier added: " + (BarrierManager.getInstance().getSelectedBarrierType()).toString() );
            BarrierManager.getInstance().getBarrierByLocation(x, y).getSprite().setVisible(true);
            GameView.getInstance(SessionManager.getInstance()).reinitializeUI();
        }
    }

    private void handleRightClick(int x, int y) {
        //debug
        System.out.println("Right Click is detected ");

        if (BarrierManager.getInstance().getBarrierByLocation(x, y) != null) {
            BarrierManager.getInstance().deleteBarrier(BarrierManager.getInstance().getBarrierByLocation(x, y));

            //debug
            System.out.println("Barrier removed: " + (BarrierManager.getInstance().getSelectedBarrierType()).toString() );
            GameView.getInstance(SessionManager.getInstance()).reinitializeUI();

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

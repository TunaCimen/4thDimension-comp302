package org.LanceOfDestiny.ui.UIUtilities;

import org.LanceOfDestiny.domain.Constants;


import org.LanceOfDestiny.domain.behaviours.Behaviour;
import org.LanceOfDestiny.domain.behaviours.GameObject;


import org.LanceOfDestiny.domain.events.Events;
import org.LanceOfDestiny.domain.sprite.*;
import org.LanceOfDestiny.ui.UIElements.YmirUI;
import org.LanceOfDestiny.ui.UIUtilities.BuildModeMouseHandler;

import javax.swing.*;
import java.awt.*;

/**
 * Custom drawing JPanel for graphics drawings.
 * paints all the game objects.
 */
public class DrawCanvas extends JPanel {
    private final Image backgroundImage;
    private final int ymirWidth = 250;
    private final int ymirHeight = 250;

    YmirUI ymirUI;
    public DrawCanvas() {
        Events.CanvasUpdateEvent.addRunnableListener(this::repaint);
        Events.Reset.addRunnableListener(Events.CanvasUpdateEvent::invoke);
        new BuildModeMouseHandler(this);
        var width = Constants.SCREEN_WIDTH;
        var height = Constants.SCREEN_HEIGHT;
        this.backgroundImage = new ImageIcon(ImageOperations.resizeImage(ImageLibrary.Background.getImage(), width, height)).getImage();
        ymirUI = new YmirUI(Constants.SCREEN_WIDTH /2 - 125, Constants.SCREEN_HEIGHT / 2 -125);
        setVisible(true);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);

        g.drawImage(ymirUI.getYmirImage(), ymirUI.getX(),ymirUI.getY(),this);
        for(Behaviour behaviour : GameObject.getGameObjects()) {
            if (behaviour.gameObject != null) {
                Sprite gameObjectSprite = behaviour.gameObject.getSprite();
                if(!gameObjectSprite.isVisible) continue;
                g.setColor(gameObjectSprite.color);
                gameObjectSprite.drawShape(g);
                }
            }
        }
    }




package org.LanceOfDestiny.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GamePanelDraw extends JPanel implements ActionListener, MouseListener, MouseMotionListener {
    //todo: animatorlar eklenmeli
    private Timer repaintTimer;
    private Image backgroundImage;
    private boolean isGameActive = false;




    private static final int REPAINT_DELAY_MS = 16; // Approx 60 FPS
    private static final String BACKGROUND_IMAGE_PATH = "java/org/LanceOfDestiny/Resources/Image/200Background.png"; //todo:image scale için bir çözüm lazım olucak

    public GamePanelDraw() {
        setFocusable(true);
        setPreferredSize(new Dimension(1280, 720));
        loadImageResources();
        setupTimers();
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    private void loadImageResources() {
        backgroundImage = new ImageIcon(BACKGROUND_IMAGE_PATH).getImage();
    }

    private void setupTimers() {
        repaintTimer = new Timer(REPAINT_DELAY_MS, this);
        repaintTimer.start();
    }

    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBackground(g);

        //todo: draw game elements
        if (isGameActive) {
            // Draw game elements

        }

        if (checkGameOver()) {
            // Handle game over condition
            endGame();
        } else if (checkWin()) {
            // Handle big win condition
            endGame();


        }
    }

    private void drawBackground(Graphics g) {
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }

    // MouseListener and MouseMotionListener interface methods
    @Override
    public void mouseDragged(MouseEvent e) {
        // Handle obstacle movement in build mode
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // Update cursor or game elements on mouse move
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // Handle game interactions like placing or removing obstacles
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Possibly handle object selection
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Handle releasing of selected objects
    }



    // Unused MouseListener methods
    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
    // Unused MouseMotionListener methods




    public void resumeGame() {
        isGameActive = true;
        repaintTimer.start();
    }
    public void pauseGame() {
        isGameActive = false;
        repaintTimer.stop();
    }

    private boolean checkGameOver() {
        // Implement game over checking logic
        return false;
    }
    private boolean checkWin() {
        // Implement win checking logic
        return false;
    }

    private void endGame() {
        // Implement what happens when the game ends
        repaintTimer.stop();
        // Display message
    }

}

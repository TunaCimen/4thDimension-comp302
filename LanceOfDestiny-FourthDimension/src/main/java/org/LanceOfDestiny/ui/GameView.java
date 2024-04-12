package org.LanceOfDestiny.ui;

import org.LanceOfDestiny.LanceOfDestiny;

import javax.swing.*;
import java.awt.*;

public class GameView {
    private GamePanelDraw gamePanel;
    private JPanel controlPanel;
    private JButton newGameButton;
    private JButton loadGameButton;
    private JButton saveGameButton;
    private JButton switchToRunningModeButton;
    private JButton helpButton;
    private JButton cannonSpellButton;
    private JButton chanceSpellButton;
    private JButton expansionSpellButton;
    private JLabel scoreLabel;
    private JLabel livesLabel;


    private void createUIElements() {
        LanceOfDestiny.getInstance().getMainFrame().getContentPane().setLayout(new BorderLayout());
        controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        newGameButton = new JButton("Build New Game");
        loadGameButton = new JButton("Load Game");
        saveGameButton = new JButton("Save Game");
        switchToRunningModeButton = new JButton("Switch to Running Mode");
        helpButton = new JButton("Help");
        cannonSpellButton = new JButton("Cannon Spell");
        chanceSpellButton = new JButton("Chance Spell");
        expansionSpellButton = new JButton("Expansion Spell");
        scoreLabel = new JLabel("Score: 0");
        livesLabel = new JLabel("Lives: 3");

    }

    public void controlPanelInitialization() {
        //this is where we gonna add elements to the control panel and set their initial visibility

    }

    public void createActionListeners() {
        //this is where we gonna add action listeners to the buttons

    }

    public void barrierSelection() {
        //this is where we gonna create a dialog for barrier selection where the user can input barrier numbers: simple, reinforced, explosive, rewarding

    }

    public void buildMap() {
        //this is where we gonna create a map from the barrier numbers with the help of the BuildModeController

    }

    public void loadMap() {
        //this is where we gonna load a map from a file with the help of the BuildModeController

    }

    public void initializeGamePanel() {
        //this is where we gonna initialize the GamePanel  add it to the main frame and set it visible


    }













}

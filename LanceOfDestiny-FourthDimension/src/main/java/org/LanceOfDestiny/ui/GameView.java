package org.LanceOfDestiny.ui;

import org.LanceOfDestiny.LanceOfDestiny;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

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


    /**
     * This method is responsible for creating the UI elements for the game.
     * It initializes the control panel, game buttons, spell buttons, and labels for score and lives.
     * It also sets the layout for the main frame of the game.
     */
    private void createUIElements() {
        // Get the instance of the game and set the layout of the main frame
        LanceOfDestiny.getInstance().getMainFrame().getContentPane().setLayout(new BorderLayout());

        // Initialize the control panel with a flow layout
        controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        // Initialize the game buttons
        newGameButton = new JButton("Build New Game");
        loadGameButton = new JButton("Load Game");
        saveGameButton = new JButton("Save Game");
        switchToRunningModeButton = new JButton("Switch to Running Mode");
        helpButton = new JButton("Help");

        // Initialize the spell buttons
        cannonSpellButton = new JButton("Cannon Spell");
        chanceSpellButton = new JButton("Chance Spell");
        expansionSpellButton = new JButton("Expansion Spell");

        // Initialize the score and lives labels
        scoreLabel = new JLabel("Score: 0");
        livesLabel = new JLabel("Lives: 3");
    }

    /**
     * This method is responsible for initializing the control panel of the game.
     * It adds the game buttons, spell buttons, and labels for score and lives to the control panel.
     * It also adds the control panel to the main frame of the game.
     */
    public void controlPanelInitialization() {
        // Adds game buttons to the control panel
        controlPanel.add(newGameButton);
        controlPanel.add(loadGameButton);
        controlPanel.add(saveGameButton);
        controlPanel.add(switchToRunningModeButton);
        controlPanel.add(helpButton);

        // Adds spell buttons to the control panel
        controlPanel.add(cannonSpellButton);
        controlPanel.add(chanceSpellButton);
        controlPanel.add(expansionSpellButton);

        // Adds score and lives labels to the control panel
        controlPanel.add(scoreLabel);
        controlPanel.add(livesLabel);

        // Adds the control panel to the main frame of the game
        LanceOfDestiny.getInstance().getMainFrame().getContentPane().add(controlPanel, BorderLayout.NORTH);
    }

    public void setVisibility() {
        // This is where we gonna set visibilities of the buttons and labels
    }


    public void createActionListeners() {
        newGameButton.addActionListener(e -> barrierSelection());
        loadGameButton.addActionListener(e -> loadMap());
        saveGameButton.addActionListener(e -> saveGame());
        switchToRunningModeButton.addActionListener(e -> switchToRunningMode());
        helpButton.addActionListener(e -> showHelpDialog());
        cannonSpellButton.addActionListener(e -> castCannonSpell());
        chanceSpellButton.addActionListener(e -> castChanceSpell());
        expansionSpellButton.addActionListener(e -> castExpansionSpell());
    }

    public void barrierSelection() {
        JDialog barrierDialog = new JDialog(LanceOfDestiny.getInstance().getMainFrame(), "Barrier Selection", true);
        barrierDialog.setLayout(new GridLayout(0, 2));

        // Map to store barrier types and their corresponding spinners
        Map<String, JSpinner> barrierFields = new HashMap<>();
        String[] barrierTypes = {"Simple", "Reinforced", "Explosive", "Rewarding"};
        int[] minimumCounts = {75, 10, 5, 10}; // Minimum counts for each type

        for (int i = 0; i < barrierTypes.length; i++) {
            barrierDialog.add(new JLabel(barrierTypes[i] + " Barriers:"));
            JSpinner spinner = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
            barrierDialog.add(spinner);
            barrierFields.put(barrierTypes[i], spinner);
        }

        JButton confirmButton = new JButton("Confirm Barrier Selection");
        barrierDialog.add(confirmButton);
        confirmButton.addActionListener(e -> {
            try {
                StringBuilder errorMessage = new StringBuilder();
                for (int i = 0; i < barrierTypes.length; i++) {
                    int count = (Integer) barrierFields.get(barrierTypes[i]).getValue();
                    if (count < minimumCounts[i]) {
                        errorMessage.append(barrierTypes[i]).append(" barriers must be at least ").append(minimumCounts[i]).append(".\n");
                    }
                }
                if (!errorMessage.isEmpty()) {
                    JOptionPane.showMessageDialog(barrierDialog, errorMessage.toString(), "Input Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    //TODO: implement build map
                    //buildMap();
                    barrierDialog.dispose();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(barrierDialog, "Error processing inputs: " + ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        barrierDialog.pack();
        barrierDialog.setLocationRelativeTo(LanceOfDestiny.getInstance().getMainFrame());
        barrierDialog.setVisible(true);

    }

    public void loadMap() {
        // Implement the logic for loading a map here
    }

    public void saveGame() {
        // Implement the logic for saving the game here
    }

    public void switchToRunningMode() {
        // Implement the logic for switching to running mode here
    }

    public void showHelpDialog() {
        // Implement the logic for showing a help dialog here
    }

    public void castCannonSpell() {
        // Implement the logic for casting a cannon spell here
    }

    public void castChanceSpell() {
        // Implement the logic for casting a chance spell here
    }

    public void castExpansionSpell() {
        // Implement the logic for casting an expansion spell here
    }
    public void buildMap() {
        //this is where we gonna create a map from the barrier numbers with the help of the BuildModeController

    }

    public void initializeGamePanel() {
        //this is where we gonna initialize the GamePanel  add it to the main frame and set it visible


    }













}

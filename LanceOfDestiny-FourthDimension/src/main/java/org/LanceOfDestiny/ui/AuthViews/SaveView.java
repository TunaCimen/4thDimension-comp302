package org.LanceOfDestiny.ui.AuthViews;

import org.LanceOfDestiny.domain.AuthModels.LogInController;
import org.LanceOfDestiny.domain.events.Events;
import org.LanceOfDestiny.domain.managers.BarrierManager;
import org.LanceOfDestiny.domain.managers.ScoreManager;
import org.LanceOfDestiny.domain.managers.SessionManager;
import org.LanceOfDestiny.domain.spells.Spell;
import org.LanceOfDestiny.domain.spells.SpellContainer;
import org.LanceOfDestiny.domain.spells.SpellType;
import org.LanceOfDestiny.ui.Window;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.Map;

public class SaveView extends JFrame implements Window {
    private final LogInController userManager;
    private final SessionManager sesManager;
    private final BarrierManager barManager;
    private final ScoreManager scoreManager;

    private JTextField saveNameField;

    public SaveView() throws HeadlessException {
        this.userManager = LogInController.getInstance();
        this.sesManager = SessionManager.getInstance();
        this.barManager = BarrierManager.getInstance();
        this.scoreManager = ScoreManager.getInstance();
    }

    public void createAndShowUI() {
        setSize(300, 220); // Reduced size for the frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout()); // Use BorderLayout for better arrangement

        // SAVE GAME label
        JLabel saveGameLabel = new JLabel("SAVE GAME", SwingConstants.CENTER);
        saveGameLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Set font style and size
        add(saveGameLabel, BorderLayout.NORTH); // Add label to the top of the layout

        // Panel for components
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout()); // Use BorderLayout for main panel

        // Panel for text field and buttons
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS)); // Vertical layout

        // Text field for save name
        saveNameField = new JTextField();
        saveNameField.setPreferredSize(new Dimension(200, 25)); // Shorter text field
        saveNameField.setMaximumSize(new Dimension(200, 25)); // Set maximum size
        saveNameField.setAlignmentX(Component.CENTER_ALIGNMENT); // Center horizontally
        inputPanel.add(saveNameField); // Add text field to input panel

        // Save button
        JButton saveButton = new JButton("Save");
        saveButton.setPreferredSize(new Dimension(200, 40)); // Set preferred size
        saveButton.setMinimumSize(new Dimension(200, 40)); // Set minimum size
        saveButton.setMaximumSize(new Dimension(200, 40)); // Set maximum size
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center horizontally
        saveButton.addActionListener(e -> {
            String saveName = saveNameField.getText();
            if (!saveName.isEmpty()) {
                int chancespell = 0;
                int expansionspell = 0;
                int overwhelmingspell = 0;
                int canonspell = 0;
                for(Map.Entry entry: SessionManager.getInstance().getPlayer().getSpellContainer().getSpellMap().entrySet()){
                    if ((entry.getKey() == SpellType.CHANCE) && ((boolean) entry.getValue())){
                        chancespell+=1;
                    }
                    else if (entry.getKey() == SpellType.EXPANSION && ((boolean) entry.getValue())){
                        expansionspell+=1;
                    }
                    else if (entry.getKey() == SpellType.OVERWHELMING && ((boolean) entry.getValue())){
                        overwhelmingspell+=1;
                    }
                    else if (entry.getKey() == SpellType.CANON && ((boolean) entry.getValue())){
                        canonspell+=1;
                    }

                }
                try {
                    String numspells = chancespell + ", " + expansionspell + ", " + overwhelmingspell + ", " + canonspell;
                    if (SaveView.this.userManager.saveGame(saveName, barManager.getBarriers(), scoreManager.getScore(), sesManager.getPlayer().getChancesLeft(), numspells)) {
                        JOptionPane.showMessageDialog(null, "Game saved successfully!");
                        SaveView.this.dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to save game!");
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please enter a valid save name!");
            }
        });
        inputPanel.add(saveButton); // Add Save button to input panel

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setPreferredSize(new Dimension(200, 40)); // Set preferred size
        cancelButton.setMinimumSize(new Dimension(200, 40)); // Set minimum size
        cancelButton.setMaximumSize(new Dimension(200, 40)); // Set maximum size
        cancelButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center horizontally
        cancelButton.addActionListener(e -> {
            SaveView.this.dispose();
            Events.ResumeGame.invoke();
        });
        inputPanel.add(cancelButton); // Add Cancel button to input panel

        inputPanel.add(cancelButton); // Add Cancel button to input panel

        mainPanel.add(inputPanel, BorderLayout.CENTER); // Add input panel to main panel

        // Add padding between components
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding of 10px

        // Add main panel to the frame
        add(mainPanel, BorderLayout.CENTER); // Centered in the layout

        // Center the window
        setLocationRelativeTo(null);

        // Set visible
        setVisible(true);
    }
}
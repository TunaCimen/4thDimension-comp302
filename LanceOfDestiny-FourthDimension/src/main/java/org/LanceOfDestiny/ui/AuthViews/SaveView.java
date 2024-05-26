package org.LanceOfDestiny.ui.AuthViews;

import org.LanceOfDestiny.domain.AuthModels.LogInController;
import org.LanceOfDestiny.domain.events.Event;
import org.LanceOfDestiny.domain.managers.BarrierManager;
import org.LanceOfDestiny.domain.managers.ScoreManager;
import org.LanceOfDestiny.domain.managers.SessionManager;
import org.LanceOfDestiny.domain.spells.SpellType;
import org.LanceOfDestiny.ui.UIElements.UILibrary;
import org.LanceOfDestiny.ui.UIUtilities.Window;

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
        setSize(350, 250); // Increase the size of the frame
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout()); // Use BorderLayout for better arrangement

        // Custom content pane to draw the background image
        JPanel contentPane = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon icon = new ImageIcon("LanceOfDestiny-FourthDimension/Image/Background.png");
                Image image = icon.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        setContentPane(contentPane);

        // SAVE GAME label
        JLabel saveGameLabel = new JLabel("SAVE GAME", SwingConstants.CENTER);
        saveGameLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Set font style and size
        saveGameLabel.setForeground(Color.WHITE); // Set text color to white
        add(saveGameLabel, BorderLayout.NORTH); // Add label to the top of the layout

        // Panel for components
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS)); // Vertical layout
        mainPanel.setOpaque(false); // Make panel transparent to show background image
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding of 10px

        // Text field for save name
        saveNameField = new JTextField();
        saveNameField.setPreferredSize(new Dimension(200, 25)); // Shorter text field
        saveNameField.setMaximumSize(new Dimension(200, 25)); // Set maximum size
        saveNameField.setAlignmentX(Component.CENTER_ALIGNMENT); // Center horizontally
        mainPanel.add(saveNameField); // Add text field to main panel
        mainPanel.add(Box.createVerticalStrut(10)); // Add vertical padding

        // Save button
        JButton saveButton = UILibrary.createButton("Save", this::saveGame);
        mainPanel.add(saveButton); // Add Save button to main panel
        mainPanel.add(Box.createVerticalStrut(10)); // Add vertical padding

        // Cancel button
        JButton cancelButton = UILibrary.createButton("Cancel", this::cancel);
        mainPanel.add(cancelButton); // Add Cancel button to main panel
        mainPanel.add(Box.createVerticalStrut(10)); // Add vertical padding

        // Add main panel to the frame
        add(mainPanel, BorderLayout.CENTER); // Centered in the layout

        // Center the window
        setLocationRelativeTo(null);

        // Set visible
        setVisible(true);
    }

    private void saveGame() {
        String saveName = saveNameField.getText();
        if (!saveName.isEmpty()) {
            int chanceSpell = 0;
            int expansionSpell = 0;
            int overwhelmingSpell = 0;
            int canonSpell = 0;
            for (Map.Entry<SpellType, Boolean> entry : SessionManager.getInstance().getPlayer().getSpellContainer().getSpellMap().entrySet()) {
                if (entry.getKey() == SpellType.CHANCE && entry.getValue()) {
                    chanceSpell++;
                } else if (entry.getKey() == SpellType.EXPANSION && entry.getValue()) {
                    expansionSpell++;
                } else if (entry.getKey() == SpellType.OVERWHELMING && entry.getValue()) {
                    overwhelmingSpell++;
                } else if (entry.getKey() == SpellType.CANON && entry.getValue()) {
                    canonSpell++;
                }
            }
            try {
                String numSpells = chanceSpell + ", " + expansionSpell + ", " + overwhelmingSpell + ", " + canonSpell;
                if (userManager.saveGame(saveName, barManager.getBarriers(), scoreManager.getScore(), sesManager.getPlayer().getChancesLeft(), numSpells)) {
                    JOptionPane.showMessageDialog(null, "Game saved successfully!");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to save game!");
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please enter a valid save name!");
        }
    }

    private void cancel() {
        dispose();
    }
}

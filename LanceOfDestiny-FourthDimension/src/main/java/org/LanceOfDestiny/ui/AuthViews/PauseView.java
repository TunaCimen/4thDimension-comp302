package org.LanceOfDestiny.ui.AuthViews;

import org.LanceOfDestiny.domain.events.Events;
import org.LanceOfDestiny.ui.Window;
import org.LanceOfDestiny.ui.WindowManager;
import org.LanceOfDestiny.ui.Windows;

import javax.swing.*;
import java.awt.*;

public class PauseView extends JFrame implements Window {
    private final WindowManager wm;
    public PauseView() {
        wm = WindowManager.getInstance();
    }

    @Override
    public void createAndShowUI() {

        setSize(300, 250); // Increased height for accommodating bigger buttons

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout()); // Use BorderLayout for better arrangement

        // PAUSED label
        JLabel pausedLabel = new JLabel("PAUSED", SwingConstants.CENTER);
        pausedLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Set font style and size
        add(pausedLabel, BorderLayout.NORTH); // Add label to the top of the layout

        // Panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1, 10, 10)); // 4 rows, 1 column, with gaps

        // Save button
        JButton saveButton = new JButton("Save");
        saveButton.setPreferredSize(new Dimension(140, 60)); // Larger size for Save button
        saveButton.addActionListener(e -> {
            wm.showWindow(Windows.SaveView);
        });
        buttonPanel.add(saveButton); // Add Save button to the panel

        // Load button
        JButton loadButton = new JButton("Load");
        loadButton.setPreferredSize(new Dimension(140, 60)); // Larger size for Load button
        loadButton.addActionListener(e -> {
            wm.showWindow(Windows.LoadView);
        });
        buttonPanel.add(loadButton); // Add Load button to the panel

        // Help button
        JButton helpButton = new JButton("Help");
        helpButton.setPreferredSize(new Dimension(140, 60)); // Larger size for Help button
        //helpButton.addActionListener(e -> showHelp());
        buttonPanel.add(helpButton); // Add Help button to the panel

        // Resume button
        JButton resumeButton = new JButton("Resume");
        resumeButton.setPreferredSize(new Dimension(140, 60)); // Larger size for Resume button
        resumeButton.addActionListener(e -> {
            this.dispose();
            Events.ResumeGame.invoke();
        }); // Close the window on click
        buttonPanel.add(resumeButton); // Add Resume button to the panel

        // Add button panel to the frame
        add(buttonPanel, BorderLayout.CENTER); // Centered in the layout

        // Center the window
        setLocationRelativeTo(null);

        // Set visible
        setVisible(true);
    }
}

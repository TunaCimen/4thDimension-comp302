package org.LanceOfDestiny.ui.AuthViews;


import org.LanceOfDestiny.domain.events.Events;
import org.LanceOfDestiny.ui.Window;

import javax.swing.*;
import java.awt.*;

public class PauseView extends JFrame implements Window {

    public PauseView(){

    }
    @Override
    public void createAndShowUI() {
        setSize(300, 200); // Set the size of the window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout()); // Use BorderLayout for better arrangement

        // PAUSED label
        JLabel pausedLabel = new JLabel("PAUSED", SwingConstants.CENTER);
        pausedLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Set font style and size
        add(pausedLabel, BorderLayout.NORTH); // Add label to the top of the layout

        // Panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout()); // FlowLayout for the buttons

        // Help button
        JButton helpButton = new JButton("Help");
        //helpButton.addActionListener(e -> showHelp());
        buttonPanel.add(helpButton); // Add Help button to the panel

        // Resume button
        JButton resumeButton = new JButton("Resume");
        resumeButton.addActionListener(e -> {

            this.dispose();
            Events.ResumeGame.invoke();

        }); // Close the window on click
        buttonPanel.add(resumeButton); // Add Resume button to the panel

        // Add button panel to the frame
        add(buttonPanel, BorderLayout.SOUTH);

        // Center the window
        setLocationRelativeTo(null);

        // Set visible
        setVisible(true);
    }
}

package org.LanceOfDestiny.ui.AuthViews;

import org.LanceOfDestiny.domain.events.Events;
import org.LanceOfDestiny.ui.Window;
import org.LanceOfDestiny.ui.Windows;

import javax.swing.*;
import java.awt.*;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import javax.swing.*;
import java.awt.*;
import java.util.List;

public class LoadView extends JFrame implements Window {
    private List<String> savedNames;
    private final org.LanceOfDestiny.domain.AuthModels.LogInController userManager;

    public LoadView() {
        this.userManager = org.LanceOfDestiny.domain.AuthModels.LogInController.getInstance();
        this.savedNames = new ArrayList<>();
    }

    public void createAndShowUI() {
        try{
            this.savedNames = userManager.loadSavedNames();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        setTitle("Load View");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel(new GridLayout(savedNames.size(), 1));

        // Set a fixed size for the buttons
        Dimension buttonSize = new Dimension(200, 30);

        for (String name : savedNames) {
            JButton button = new JButton(name);
            button.setPreferredSize(buttonSize);
            button.setMaximumSize(buttonSize);
            button.setMinimumSize(buttonSize);
            button.addActionListener(e -> {
                // Handle button click event here
                System.out.println("Button clicked: " + name);
            });
            buttonPanel.add(button);
        }

        JScrollPane scrollPane = new JScrollPane(buttonPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane, BorderLayout.CENTER);

        setPreferredSize(new Dimension(400, 300)); // Set preferred size
        setResizable(false); // Set resizable to false
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
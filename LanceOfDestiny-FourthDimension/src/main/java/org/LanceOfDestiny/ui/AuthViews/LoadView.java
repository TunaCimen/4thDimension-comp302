package org.LanceOfDestiny.ui.AuthViews;

import org.LanceOfDestiny.domain.barriers.Barrier;
import org.LanceOfDestiny.domain.events.Events;
import org.LanceOfDestiny.domain.managers.BarrierManager;
import org.LanceOfDestiny.domain.managers.ScoreManager;
import org.LanceOfDestiny.domain.managers.SessionManager;
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
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
                BarrierManager.getInstance().barriers = (ArrayList<Barrier>) LoadView.this.userManager.loadBarriers(name);
                try {
                    ScoreManager.getInstance().setScore(LoadView.this.userManager.loadUserInfo(name).get(0));
                    SessionManager.getInstance().getPlayer().setChancesLeft(LoadView.this.userManager.loadUserInfo(name).get(1));
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
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
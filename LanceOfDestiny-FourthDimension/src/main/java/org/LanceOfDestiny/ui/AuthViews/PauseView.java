package org.LanceOfDestiny.ui.AuthViews;

import org.LanceOfDestiny.domain.managers.SessionManager;
import org.LanceOfDestiny.ui.GameViews.GameView;
import org.LanceOfDestiny.ui.Window;
import org.LanceOfDestiny.ui.WindowManager;
import org.LanceOfDestiny.ui.Windows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class PauseView extends JFrame implements Window {
    private final WindowManager windowManager;

    public PauseView() {
        windowManager = WindowManager.getInstance();
        configureWindow();
        addComponents();
    }

    private void configureWindow() {
        setSize(300, 250);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private void addComponents() {
        add(createLabel("PAUSED", new Font("Arial", Font.BOLD, 24)), BorderLayout.NORTH);
        add(createButtonPanel(), BorderLayout.CENTER);
    }

    @Override
    public void createAndShowUI() {
        setVisible(true);
    }

    private JLabel createLabel(String text, Font font) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(font);
        return label;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));

        panel.add(createButton("Resume", e -> handleResume()));
        panel.add(createButton("Save", e -> windowManager.showWindow(Windows.SaveView)));
        panel.add(createButton("Load", e -> windowManager.showWindow(Windows.LoadView)));
        panel.add(createButton("Help", e -> {})); // Assuming a method 'showHelp()' to be implemented.

        return panel;
    }

    private JButton createButton(String text, ActionListener listener) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(140, 60));
        button.addActionListener(listener);
        return button;
    }

    private void handleResume() {
        dispose();
        GameView gameView = GameView.getInstance(SessionManager.getInstance());
        gameView.startGame();
        gameView.setPauseButtonVisibility(true);
        gameView.revalidate();
        gameView.repaint();
    }
}

package org.LanceOfDestiny.ui.GameViews;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.EventSystem.Events;
import org.LanceOfDestiny.domain.managers.InputManager;
import org.LanceOfDestiny.domain.managers.SessionManager;
import org.LanceOfDestiny.ui.Window;

import javax.swing.*;
import java.awt.*;

public class BuildView extends JFrame implements Window {
    private final SessionManager sessionManager;
    private JButton startGameButton, pauseGameButton, resumeGameButton;
    private JLabel chanceBar;

    public BuildView(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
        createAndShowUI();
    }

    @Override
    public void createAndShowUI() {
        configureWindow();
        configureButtons();
        configurePanels();
        sessionManager.initializeSession();
        setVisible(true);
    }

    private void configureWindow() {
        setFocusable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
        setDefaultLookAndFeelDecorated(true);
        setResizable(false);
        addKeyListener(InputManager.getInstance());
    }

    private void configureButtons() {
        startGameButton = new JButton("Start Game");
        pauseGameButton = new JButton("Pause Game");
        resumeGameButton = new JButton("Resume Game");
        pauseGameButton.setVisible(false);
        resumeGameButton.setVisible(false);

        startGameButton.addActionListener(e -> handleStartGame());
        pauseGameButton.addActionListener(e -> handlePauseGame());
        resumeGameButton.addActionListener(e -> handleResumeGame());
    }

    private void configurePanels() {
        JPanel pausePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        pausePanel.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, 50));
        pausePanel.add(startGameButton);
        pausePanel.add(pauseGameButton);
        pausePanel.add(resumeGameButton);

        chanceBar = new JLabel("<3  " + sessionManager.getPlayer().getChances());
        Events.UpdateChance.addListener(e -> updateChanceBar());
        pausePanel.add(chanceBar);

        add(sessionManager.getDrawCanvas(), BorderLayout.CENTER);
        add(pausePanel, BorderLayout.NORTH);
    }

    private void handleStartGame() {
        pauseGameButton.setVisible(true);
        resumeGameButton.setVisible(false);
        startGameButton.setVisible(false);
        sessionManager.getLoopExecutor().start();
    }

    private void handlePauseGame() {
        System.out.println(sessionManager.getLoopExecutor().getSecondsPassed());
        resumeGameButton.setVisible(true);
        Events.PauseGame.invoke();
    }

    private void handleResumeGame() {
        Events.ResumeGame.invoke();
        pauseGameButton.setVisible(true);
        requestFocusInWindow();
    }

    private void updateChanceBar() {
        SwingUtilities.invokeLater(() ->
                chanceBar.setText("<3  " + sessionManager.getPlayer().getChances())
        );
    }
}

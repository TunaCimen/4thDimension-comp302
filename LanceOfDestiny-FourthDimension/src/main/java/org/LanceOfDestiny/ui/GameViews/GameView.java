package org.LanceOfDestiny.ui.GameViews;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.barriers.BarrierTypes;
import org.LanceOfDestiny.domain.events.Events;
import org.LanceOfDestiny.domain.managers.*;
import org.LanceOfDestiny.ui.Window;
import org.LanceOfDestiny.ui.*;

import javax.swing.*;
import java.awt.*;


public class GameView extends JFrame implements Window {


    private static final String STATUS_START = "START";
    private static final String STATUS_GAME = "GAME";
    private static final String STATUS_END = "END";
    final Dimension maximumSizeButton = new Dimension(150, 45);
    JButton buttonPlay;
    JButton buttonPause;
    HealthBar healthBarDisplay;

    SpellInventory spellInventory;

    JPanel cardPanel;
    ScoreBar scoreBar;
    private SessionManager sessionManager;
    private JComboBox<String> comboBoxAddBarrierType;
    private CardLayout cardLayout;

    public GameView() {
        ScoreManager scoreManager = ScoreManager.getInstance();
        this.sessionManager = SessionManager.getInstance();
        this.scoreBar = new ScoreBar(scoreManager::getScore);
        this.sessionManager.initializeSession();
        this.cardLayout = new CardLayout();
        this.cardPanel = new JPanel(cardLayout);
        add(cardPanel, BorderLayout.CENTER);
        setLayout(cardLayout);
        initializeComponents();
        decorateCardPanel();
        subscribeMethods();
    }

    private void decorateCardPanel() {
        JPanel startPanel = createStartPanel();
        cardPanel.add(startPanel, STATUS_START);
        JPanel parentPanel = createParentPanel();
        cardPanel.add(parentPanel, STATUS_GAME);
        JPanel loseGamePanel = createEndGamePanel();
        cardPanel.add(loseGamePanel, STATUS_END);
    }

    private void subscribeMethods() {
        Events.StartGame.addRunnableListener(this::startGame);
        Events.Reset.addRunnableListener(this::handleNewGame);
        Events.ResumeGame.addRunnableListener(this::requestFocusInWindow);
        Events.LoadGame.addRunnableListener(Events.CanvasUpdateEvent::invoke);
        Events.LoadGame.addRunnableListener(() -> {
            healthBarDisplay.setHealth(
                    SessionManager.getInstance().getPlayer().getChancesLeft());
        });
        Events.LoadGame.addRunnableListener(() -> scoreBar.updateScore());
        Events.EndGame.addRunnableListener(() -> {
            showPanel(STATUS_END);
        });
        Events.BuildDoneEvent.addRunnableListener(() -> {
            this.setEnabled(true);
            this.requestFocusInWindow(true);
        });

        Events.PauseGame.addRunnableListener(() -> {
            buttonPlay.setEnabled(true);
        });
        Events.PauseGame.addRunnableListener(() -> {
            WindowManager.getInstance().showWindow(Windows.PauseView);
        });


    }

    private JPanel createEndGamePanel() {
        JPanel losePanel = new JPanel();
        losePanel.setLayout(new BoxLayout(losePanel, BoxLayout.Y_AXIS));
        JLabel endLabel = createLabel("");
        Events.EndGame.addListener(e -> endLabel.setText((String) e));
        JButton newGameButton = createNewGameButton();
        JLabel scoreLabel = new JLabel();
        scoreLabel.setFont(scoreBar.getFont());
        scoreBar.addPropertyChangeListener(e -> {
            scoreLabel.setText(scoreBar.getText());
        });
        newGameButton.addActionListener(e -> {
            showPanel(STATUS_START);
        });
        endLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        endLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        newGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        losePanel.add(Box.createRigidArea(new Dimension(0, Constants.SCREEN_HEIGHT / 2 - 100)));
        losePanel.add(endLabel);
        losePanel.add(scoreLabel);
        losePanel.add(newGameButton);
        return losePanel;
    }

    private void initializeComponents() {
        setFocusable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
        setDefaultLookAndFeelDecorated(true);
        addKeyListener(InputManager.getInstance());
        setResizable(false);
    }

    public void startGame() {
        scoreBar.updateScore();
        healthBarDisplay.setHealth(SessionManager.getInstance().getPlayer().getChancesLeft());
        comboBoxAddBarrierType.setVisible(false);
        buttonPlay.setEnabled(false);
        buttonPause.setEnabled(true);
        comboBoxAddBarrierType.setVisible(false);

    }

    public void showPanel(String cardName) {
        cardLayout.show(cardPanel, cardName);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Impact", Font.BOLD, 50));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    private JButton createLoadButton() {
        JButton loadButton = new JButton("LOAD");
        loadButton.setMaximumSize(maximumSizeButton);
        loadButton.setFont(new Font("Monospaced", Font.BOLD, 15));
        loadButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadButton.addActionListener(e -> {
            showPanel(STATUS_GAME);
            WindowManager.getInstance().showWindow(Windows.LoadView);
        });
        return loadButton;
    }


    private JPanel createStartPanel() {
        JPanel startPanel = new JPanel();
        JButton newGameButton = createNewGameButton();
        startPanel.setLayout(new BorderLayout());
        startPanel.setLayout(new BoxLayout(startPanel, BoxLayout.Y_AXIS));
        startPanel.add(Box.createRigidArea(new Dimension(0, Constants.SCREEN_HEIGHT / 2 - 100)));
        startPanel.add(createLabel("Lance Of Destiny"));
        startPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        startPanel.add(newGameButton);
        startPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        startPanel.add(createLoadButton());
        return startPanel;

    }

    private JButton createNewGameButton() {
        JButton newGameButton = new JButton("NEW GAME");
        newGameButton.setFont(new Font("Monospaced", Font.BOLD, 15));
        newGameButton.setMaximumSize(maximumSizeButton);
        newGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        newGameButton.addActionListener(e -> {
            Events.Reset.invoke();
        });
        return newGameButton;
    }

    private void handleNewGame() {
        buttonPause.setEnabled(true);
        buttonPlay.setEnabled(true);
        comboBoxAddBarrierType.setVisible(true);
        sessionManager.setStatus(Status.EditMode);
        cardLayout.show(cardPanel, STATUS_GAME);
        WindowManager.getInstance().showWindow(Windows.BuildViewMini);
        this.setEnabled(false);
    }

    private JPanel createControlPanel() {
        initComboBox();
        healthBarDisplay = new HealthBar(Constants.DEFAULT_CHANCES);
        spellInventory = new SpellInventory();
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel,BoxLayout.X_AXIS));
        controlPanel.setFocusable(false);
        controlPanel.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, 50));
        controlPanel.add(startButton());
        controlPanel.add(pauseButton());
        controlPanel.add(comboBoxAddBarrierType);
        controlPanel.add(healthBarDisplay);
        controlPanel.add(spellInventory);
        controlPanel.add(scoreBar);
        controlPanel.add(Box.createRigidArea(new Dimension(25,10)));
        return controlPanel;
    }

    private JButton startButton() {
        buttonPlay = new JButton("START");
        buttonPlay.setFocusable(false);
        buttonPlay.setFont(new Font("Monospaced", Font.BOLD, 16));
        buttonPlay.addActionListener(e -> {
            Events.StartGame.invoke();
        });
        return buttonPlay;
    }


    private JButton pauseButton() {
        buttonPause = new JButton("Pause Game");
        buttonPause.setFocusable(false);
        buttonPause.setFont(new Font("Monospaced", Font.BOLD, 16));
        buttonPause.setEnabled(false);
        buttonPause.addActionListener(e -> {
            Events.PauseGame.invoke();
        });
        return buttonPause;
    }

    private JPanel createParentPanel() {
        JPanel parentPanel = new JPanel(new BorderLayout());
        parentPanel.add(sessionManager.getDrawCanvas(), BorderLayout.CENTER);
        parentPanel.add(createControlPanel(), BorderLayout.NORTH);
        return parentPanel;
    }

    private void initComboBox() {
        comboBoxAddBarrierType = new JComboBox<>(new String[]{
                BarrierTypes.SIMPLE.toString(),
                BarrierTypes.REINFORCED.toString(),
                BarrierTypes.EXPLOSIVE.toString(),
                BarrierTypes.REWARDING.toString()
        });
        comboBoxAddBarrierType.setFont(new Font("Monospaced", Font.BOLD, 12));
        BarrierTypes currentType = BarrierManager.getInstance().getSelectedBarrierType();
        comboBoxAddBarrierType.setMaximumSize(new Dimension(75,50));
        if (currentType == null) currentType = BarrierTypes.SIMPLE;
        comboBoxAddBarrierType.setSelectedItem(currentType.toString());
        comboBoxAddBarrierType.addActionListener(e -> {
            changeSelectedBarrier();
        });
    }

    private void changeSelectedBarrier() {
        BarrierTypes selectedType = BarrierTypes.valueOf((String) comboBoxAddBarrierType.getSelectedItem());
        BarrierManager.getInstance().setSelectedBarrierType(selectedType);
        //Debugging
        System.out.println("Selected Barrier is: " + selectedType);
    }

    @Override
    public void createAndShowUI() {
        setVisible(true);
    }
}
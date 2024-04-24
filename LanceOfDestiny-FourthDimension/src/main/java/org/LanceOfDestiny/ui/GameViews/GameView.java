package org.LanceOfDestiny.ui.GameViews;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.barriers.BarrierTypes;
import org.LanceOfDestiny.domain.events.Events;
import org.LanceOfDestiny.domain.managers.*;
import org.LanceOfDestiny.ui.*;
import org.LanceOfDestiny.ui.Window;

import javax.swing.*;
import java.awt.*;

public class GameView extends JFrame implements Window {
    private static GameView instance = null;
    private SessionManager sessionManager;


    JButton buttonPlay;
    JButton buttonPause;
    JButton buttonBuild;
    JButton buttonSave;

    HealthBar healthBarDisplay;

    private static final String STATUS_START = "START";
    private static final String STATUS_GAME = "GAME";
    private static String STATUS;


    private JComboBox<String> comboBoxAddBarrierType;

    private CardLayout cardLayout;

    JPanel cardPanel;
    ScoreBar scoreBar;

    public GameView() {
        this.sessionManager = SessionManager.getInstance();
        initializeComponents();
        cardLayout = new CardLayout();
        setLayout(cardLayout);
        cardPanel = new JPanel(cardLayout);
        add(cardPanel,BorderLayout.CENTER);
        scoreBar = new ScoreBar();
        Events.ResumeGame.addRunnableListener(this::startGame);
        Events.LoadGame.addRunnableListener(()->sessionManager.getDrawCanvas().repaint());
        Events.LoadGame.addRunnableListener(()->{
            healthBarDisplay.setHealth(
                    SessionManager.getInstance().getPlayer().getChancesLeft());
        });
        Events.LoadGame.addRunnableListener(()->scoreBar.updateScore());
    }


    public static GameView getInstance() {
        if (instance == null) {
            instance = new GameView();
            STATUS = STATUS_START;
        }
        return instance;
    }

    private void initializeComponents() {
        setFocusable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
        setDefaultLookAndFeelDecorated(true);
        Events.ResumeGame.addRunnableListener(this::requestFocusInWindow);
        addKeyListener(InputManager.getInstance());
        setResizable(false);
    }



    public void startGame() {
        scoreBar.updateScore();
        healthBarDisplay.setHealth(SessionManager.getInstance().getPlayer().getChancesLeft());
        comboBoxAddBarrierType.setVisible(false);
        buttonPlay.setEnabled(false);
        buttonPause.setEnabled(true);
        buttonSave.setVisible(false);
        comboBoxAddBarrierType.setVisible(false);
        sessionManager.setStatus(Status.RunningMode);
        sessionManager.getLoopExecutor().start();
    }


    public void showPanel(String cardName){
        cardLayout.show(cardPanel, cardName);
    }

    private void createStartPanel(){
        final Dimension maximumSizeButton = new Dimension(150, 45);
        JPanel startPanel = new JPanel();
        startPanel.setLayout(new BorderLayout());
        startPanel.setLayout(new BoxLayout(startPanel, BoxLayout.Y_AXIS));
        startPanel.add(Box.createRigidArea(new Dimension(0, Constants.SCREEN_HEIGHT/2 -100)));
        JButton newGameButton = new JButton("NEW GAME");
        newGameButton.setFont( new Font("Monospaced", Font.BOLD, 15));
        newGameButton.setMaximumSize(maximumSizeButton);
        newGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        newGameButton.addActionListener(e->{
            buttonPause.setEnabled(true);
            buttonPlay.setEnabled(true);
            WindowManager.getInstance().showWindow(Windows.BuildViewMini);
            System.out.println(sessionManager.getLoopExecutor().getSecondsPassed());
            cardLayout.show(cardPanel,STATUS_GAME);
            STATUS = STATUS_GAME;
            this.setEnabled(false);
            Events.PauseGame.invoke();
        });
        JButton loadButton = new JButton("LOAD");
        loadButton.setMaximumSize(maximumSizeButton);
        loadButton.setFont(new Font("Monospaced", Font.BOLD, 15));
        JLabel label = new JLabel("LANCE OF DESTINY");
        label.setFont(new Font("Impact", Font.BOLD, 50));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadButton.addActionListener(e->{
            showPanel(STATUS_GAME);
            sessionManager.initializeSession();
            WindowManager.getInstance().showWindow(Windows.LoadView);
        });
        startPanel.add(label);
        startPanel.add(Box.createRigidArea(new Dimension(0,15)));
        startPanel.add(newGameButton);
        startPanel.add(Box.createRigidArea(new Dimension(0,10)));
        startPanel.add(loadButton);
        cardPanel.add(startPanel,STATUS_START);
    }

    private JPanel createControlPanel(){
        initComboBox();
        healthBarDisplay = new HealthBar(SessionManager.getInstance().getPlayer().getChancesLeft());
        SpellInventory spellInventory = new SpellInventory();
        JPanel controlPanel = new JPanel(new GridLayout(1, 5, 20, 20));
        controlPanel.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, 50));
        controlPanel.add(startButton());
        controlPanel.add(pauseButton());
        controlPanel.add(saveButton());
        controlPanel.add(comboBoxAddBarrierType);//Right now useless?
        controlPanel.add(healthBarDisplay);
        controlPanel.add(spellInventory);
        controlPanel.add(scoreBar);
        return controlPanel;
    }

    private JButton startButton(){
        buttonPlay = new JButton("START");
        buttonPlay.setFont(new Font("Monospaced", Font.BOLD, 16));
        buttonPlay.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("SPACE"), "none");
        buttonPlay.addActionListener(e->Events.ResumeGame.invoke());
        return buttonPlay;
    }

    private JButton pauseButton(){
        buttonPause = new JButton("Pause Game");
        buttonPause.setFont(new Font("Monospce", Font.BOLD, 16));
        buttonPause.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("SPACE"), "none");
        buttonPause.setEnabled(false);
        buttonPause.addActionListener(e -> {
            sessionManager.getLoopExecutor().stop();
            buttonPlay.setEnabled(true);
            WindowManager.getInstance().showWindow(Windows.PauseView);
            System.out.println(sessionManager.getLoopExecutor().getSecondsPassed());
            sessionManager.setStatus(Status.PausedMode);
            Events.PauseGame.invoke();
        });
        return buttonPause;
    }

    private JButton saveButton(){
        buttonSave = new JButton("Save Build");
        buttonSave.setFont(new Font("Monospce", Font.BOLD, 16));
        buttonSave.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("SPACE"), "none");
        buttonSave.addActionListener(e -> {
            sessionManager.getLoopExecutor().stop();
            WindowManager.getInstance().showWindow(Windows.SaveView);
            System.out.println(sessionManager.getLoopExecutor().getSecondsPassed());
            sessionManager.setStatus(Status.PausedMode);
            Events.PauseGame.invoke();
        });
        return buttonSave;
    }

    private void createParentPanel(){
        JPanel parentPanel = new JPanel(new BorderLayout());
        parentPanel.add(sessionManager.getDrawCanvas(), BorderLayout.CENTER);
        parentPanel.add(createControlPanel(), BorderLayout.NORTH);
        cardPanel.add(parentPanel, STATUS_GAME);
    }

    private void initComboBox(){
        comboBoxAddBarrierType = new JComboBox<>(new String[]{
                BarrierTypes.SIMPLE.toString(),
                BarrierTypes.REINFORCED.toString(),
                BarrierTypes.EXPLOSIVE.toString(),
                BarrierTypes.REWARDING.toString()
        });
        comboBoxAddBarrierType.setFont(new Font("Monospaced", Font.BOLD, 12));
        comboBoxAddBarrierType.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("SPACE"), "none");

        // Set the initial selection based on BarrierManager
        BarrierTypes currentType = BarrierManager.getInstance().getSelectedBarrierType();
        if (currentType == null) currentType = BarrierTypes.SIMPLE;
        comboBoxAddBarrierType.setSelectedItem(currentType.toString());

        comboBoxAddBarrierType.addActionListener(e -> {
            BarrierTypes selectedType = BarrierTypes.valueOf((String) comboBoxAddBarrierType.getSelectedItem());
            BarrierManager.getInstance().setSelectedBarrierType(selectedType);
            //Debugging
            System.out.println("Selected Barrier is: " + selectedType);
        });
    }


    public void setUpComponents(){
        createParentPanel();
        createStartPanel();
        showPanel(STATUS);
    }

    @Override
    public void createAndShowUI() {
        this.setEnabled(true);
        this.requestFocusInWindow(true);
        sessionManager.initializePlayer();
        setUpComponents();
        setVisible(true);
    }
}
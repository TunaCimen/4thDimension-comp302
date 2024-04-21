package org.LanceOfDestiny.ui.GameViews;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.barriers.BarrierTypes;
import org.LanceOfDestiny.domain.events.Events;
import org.LanceOfDestiny.domain.managers.*;
import org.LanceOfDestiny.domain.sprite.ImageLibrary;
import org.LanceOfDestiny.ui.*;
import org.LanceOfDestiny.ui.Window;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.BoxView;
import java.awt.*;

public class GameView extends JFrame implements Window {
    private static GameView instance = null;
    private SessionManager sessionManager;


    JButton buttonPlay;
    JButton buttonPause;
    JButton buttonBuild;
    JButton buttonSave;

    private static final String STATUS_START = "START";
    private static final String STATUS_GAME = "GAME";
    private static String STATUS;


    private JComboBox<String> comboBoxAddBarrierType;

    private CardLayout cardLayout;

    JPanel cardPanel;

    private GameView(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
        initializeComponents();
        cardLayout = new CardLayout();
        setLayout(cardLayout);
        cardPanel = new JPanel(cardLayout);
        add(cardPanel,BorderLayout.CENTER);

    }


    public static GameView getInstance(SessionManager sessionManager) {
        if (instance == null) {
            instance = new GameView(sessionManager);
            STATUS = STATUS_START;
        } else {
            instance.setSessionManager(sessionManager);
        }
        return instance;
    }

    private void setSessionManager(SessionManager newSessionManager) {
        this.sessionManager = newSessionManager;
        reinitializeUI();
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

    public void reinitializeUI() {
        createAndShowUI();
    }

    public void setPlayButtonVisibility(boolean isVisible) {
        buttonPlay.setVisible(isVisible);

    }
    public void setPauseButtonVisibility(boolean isVisible) {
        buttonPause.setVisible(isVisible);

    }
    public void setBuildButtonVisibility(boolean isVisible) {
        buttonBuild.setVisible(isVisible);

    }
    public void setSaveButtonVisibility(boolean isVisible) {
        buttonSave.setVisible(isVisible);

    }
    public void setPlayButtonEnabled(boolean isEnabled) {
        buttonPlay.setEnabled(isEnabled);

    }
    public void setPauseButtonEnabled(boolean isEnabled) {
        buttonPause.setEnabled(isEnabled);

    }
    public void setBuildButtonEnabled(boolean isEnabled) {
        buttonBuild.setEnabled(isEnabled);

    }

    public void startGame() {
        comboBoxAddBarrierType.setVisible(false);
        buttonPlay.setEnabled(false);
        buttonPause.setEnabled(true);
        buttonSave.setVisible(false);
        comboBoxAddBarrierType.setVisible(false);
        sessionManager.setStatus(Status.RunningMode);
        Events.ResumeGame.invoke();
        sessionManager.getLoopExecutor().start();
    }

    public void showPanel(String cardName){
        cardLayout.show(cardPanel, cardName);
    }

    public void addDrawCanvas(){

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
        startPanel.add(label);
        startPanel.add(Box.createRigidArea(new Dimension(0,15)));
        startPanel.add(newGameButton);
        startPanel.add(Box.createRigidArea(new Dimension(0,10)));
        startPanel.add(loadButton);
        cardPanel.add(startPanel,STATUS_START);
    }

    private JPanel createControlPanel(){
        initComboBox();
        HealthBar healthBarDisplay = new HealthBar(3);
        SpellInventory spellInventory = new SpellInventory();
        JPanel controlPanel = new JPanel(new GridLayout(1, 5, 20, 20));
        controlPanel.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, 50));
        controlPanel.add(startButton());
        controlPanel.add(pauseButton());
        controlPanel.add(saveButton());
        controlPanel.add(comboBoxAddBarrierType);//Right now useless?
        controlPanel.add(healthBarDisplay);
        controlPanel.add(spellInventory);
        controlPanel.add(scoreBar());
        return controlPanel;
    }

    private JButton startButton(){
        buttonPlay = new JButton("START");
        buttonPlay.setFont(new Font("Monospaced", Font.BOLD, 16));
        buttonPlay.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("SPACE"), "none");
        buttonPlay.addActionListener(e -> {startGame();});
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


    private JLabel scoreBar(){
        JLabel scoreBar = new JLabel( "SCORE:  " + String.valueOf(ScoreManager.getInstance().getScore()));
        scoreBar.setFont(new Font("Impact", Font.BOLD, 24));
        scoreBar.setPreferredSize(new Dimension(100,30));
        Events.UpdateScore.addListener(e-> scoreBar.setText("Score:  " + ScoreManager.getInstance().getScore()));
        return scoreBar;
    }

    private JLabel chanceBar(){
        ImageIcon healthBar = new ImageIcon(ImageLibrary.Heart.getImage());
        JLabel chanceBar = new JLabel( "<3:  "+ String.valueOf(sessionManager.getPlayer().getChancesLeft()));
        chanceBar.setIcon(healthBar);
        Events.UpdateChance.addListener(e-> chanceBar.setText("<3:  " + String.valueOf(sessionManager.getPlayer().getChancesLeft())));
        return chanceBar;
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


    @Override
    public void createAndShowUI() {
        this.setEnabled(true);
        this.requestFocusInWindow(true);
        sessionManager.initializePlayer();
        createParentPanel();
        createStartPanel();
        showPanel(STATUS);
        setVisible(true);
    }
}
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


    private SessionManager sessionManager;


    JButton buttonPlay;
    JButton buttonPause;
    final Dimension maximumSizeButton = new Dimension(150, 45);
    JButton buttonBuild;
    JButton buttonSave;

    HealthBar healthBarDisplay;

    private static final String STATUS_START = "START";
    private static final String STATUS_GAME = "GAME";

    private static final String STATUS_END = "END";

    private JComboBox<String> comboBoxAddBarrierType;

    private CardLayout cardLayout;

    JPanel cardPanel;
    ScoreBar scoreBar;

    public GameView() {
        this.sessionManager = SessionManager.getInstance();
        sessionManager.initializeSession();
        initializeComponents();
        cardLayout = new CardLayout();
        setLayout(cardLayout);
        cardPanel = new JPanel(cardLayout);
        add(cardPanel,BorderLayout.CENTER);
        scoreBar = new ScoreBar();

        JPanel startPanel = createStartPanel();
        cardPanel.add(startPanel,STATUS_START);
        JPanel parentPanel = createParentPanel();
        cardPanel.add(parentPanel, STATUS_GAME);
        JPanel loseGamePanel = createEndGamePanel();
        cardPanel.add(loseGamePanel, STATUS_END);

        //Event subscriptions.
        Events.ResumeGame.addRunnableListener(this::startGame);
        Events.LoadGame.addRunnableListener(Events.CanvasUpdateEvent::invoke);
        Events.LoadGame.addRunnableListener(()->{
            healthBarDisplay.setHealth(
                    SessionManager.getInstance().getPlayer().getChancesLeft());
        });
        Events.LoadGame.addRunnableListener(()->scoreBar.updateScore());
        Events.EndGame.addRunnableListener(()->{
            showPanel(STATUS_END);
        });
        Events.BuildDoneEvent.addRunnableListener(()->{
            this.setEnabled(true);
            this.requestFocusInWindow(true);
        });
    }

    private JPanel createEndGamePanel() {

        JPanel losePanel = new JPanel();
        losePanel.setLayout(new BoxLayout(losePanel, BoxLayout.Y_AXIS));
        JLabel endLabel = createLabel("");
        Events.EndGame.addListener(e->endLabel.setText((String)e));
        JButton newGameButton = createNewGameButton();
        JLabel scoreLabel = new JLabel();
        scoreLabel.setFont(scoreBar.getFont());
        scoreBar.addPropertyChangeListener(e->{
            scoreLabel.setText(scoreBar.getText());
        });
        newGameButton.addActionListener(e->{
            showPanel(STATUS_START);
        });
        endLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        endLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        newGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        losePanel.add(Box.createRigidArea(new Dimension(0,Constants.SCREEN_HEIGHT/2 - 100)));
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



    private JLabel createLabel(String text){
        JLabel label = new JLabel(text);
        label.setFont(new Font("Impact", Font.BOLD, 50));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    private JButton createLoadButton(){
        JButton loadButton = new JButton("LOAD");
        loadButton.setMaximumSize(maximumSizeButton);
        loadButton.setFont(new Font("Monospaced", Font.BOLD, 15));
        loadButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadButton.addActionListener(e->{
            showPanel(STATUS_GAME);
            WindowManager.getInstance().showWindow(Windows.LoadView);
        });
        return loadButton;
    }


    private JPanel createStartPanel(){
        final Dimension maximumSizeButton = new Dimension(150, 45);
        JPanel startPanel = new JPanel();
        startPanel.setLayout(new BorderLayout());
        startPanel.setLayout(new BoxLayout(startPanel, BoxLayout.Y_AXIS));
        startPanel.add(Box.createRigidArea(new Dimension(0, Constants.SCREEN_HEIGHT/2 -100)));
        JButton newGameButton = createNewGameButton();
        startPanel.add(createLabel("Lance Of Destiny"));
        startPanel.add(Box.createRigidArea(new Dimension(0,15)));
        startPanel.add(newGameButton);
        startPanel.add(Box.createRigidArea(new Dimension(0,10)));
        startPanel.add(createLoadButton());
        return startPanel;

    }

    private JButton createNewGameButton() {
        JButton newGameButton = new JButton("NEW GAME");
        newGameButton.setFont( new Font("Monospaced", Font.BOLD, 15));
        newGameButton.setMaximumSize(maximumSizeButton);
        newGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        newGameButton.addActionListener(e->{
            buttonPause.setEnabled(true);
            buttonPlay.setEnabled(true);
            WindowManager.getInstance().showWindow(Windows.BuildViewMini);
            Events.Reset.invoke();
            cardLayout.show(cardPanel,STATUS_GAME);
            this.setEnabled(false);
        });
        return newGameButton;
    }

    private JPanel createControlPanel(){
        initComboBox();
        healthBarDisplay = new HealthBar(Constants.DEFAULT_CHANCES);
        SpellInventory spellInventory = new SpellInventory();
        JPanel controlPanel = new JPanel(new GridLayout(1, 5, 20, 20));
        controlPanel.setFocusable(false);
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
        buttonPlay.setFocusable(false);
        buttonPlay.setFont(new Font("Monospaced", Font.BOLD, 16));
        buttonPlay.addActionListener(e->Events.ResumeGame.invoke());
        return buttonPlay;
    }

    private JButton pauseButton(){
        buttonPause = new JButton("Pause Game");
        buttonPause.setFocusable(false);
        buttonPause.setFont(new Font("Monospaced", Font.BOLD, 16));
        buttonPause.setEnabled(false);
        buttonPause.addActionListener(e -> {
            sessionManager.getLoopExecutor().stop();
            System.out.println("Passed Time = " + sessionManager.getLoopExecutor().getSecondsPassed());
            buttonPlay.setEnabled(true);
            WindowManager.getInstance().showWindow(Windows.PauseView);
            sessionManager.setStatus(Status.PausedMode);
            Events.PauseGame.invoke();
        });
        return buttonPause;
    }

    private JButton saveButton(){
        buttonSave = new JButton("Save Build");
        buttonSave.setFocusable(false);
        buttonSave.setFont(new Font("Monospaced", Font.BOLD, 16));
        buttonSave.addActionListener(e -> {
            sessionManager.getLoopExecutor().stop();
            WindowManager.getInstance().showWindow(Windows.SaveView);
            sessionManager.setStatus(Status.PausedMode);
            Events.PauseGame.invoke();
        });
        return buttonSave;
    }


    private JPanel createParentPanel(){

        JPanel parentPanel = new JPanel(new BorderLayout());
        parentPanel.add(sessionManager.getDrawCanvas(), BorderLayout.CENTER);
        parentPanel.add(createControlPanel(), BorderLayout.NORTH);
        return parentPanel;

    }

    private void initComboBox(){
        comboBoxAddBarrierType = new JComboBox<>(new String[]{
                BarrierTypes.SIMPLE.toString(),
                BarrierTypes.REINFORCED.toString(),
                BarrierTypes.EXPLOSIVE.toString(),
                BarrierTypes.REWARDING.toString()
        });
        comboBoxAddBarrierType.setFont(new Font("Monospaced", Font.BOLD, 12));
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
        //this.setEnabled(true);
        //this.requestFocusInWindow(true);
        //sessionManager.initializePlayer();
        setVisible(true);
    }
}
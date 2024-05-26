package org.LanceOfDestiny.ui.GameViews;

import org.LanceOfDestiny.Animation.CountdownAnimation;
import org.LanceOfDestiny.Animation.LinearInterpolation;
import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.barriers.BarrierTypes;
import org.LanceOfDestiny.domain.events.Event;
import org.LanceOfDestiny.domain.managers.*;
import org.LanceOfDestiny.ui.UIElements.*;
import org.LanceOfDestiny.ui.UIUtilities.Window;
import org.LanceOfDestiny.ui.UIUtilities.WindowManager;
import org.LanceOfDestiny.ui.UIUtilities.Windows;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class GameView extends JFrame implements Window {

    private static final String STATUS_START = "START";
    private static final String STATUS_GAME = "GAME";
    private static final String STATUS_END = "END";
    private static final String STATUS_INIT = "INIT";
    private static final String STATUS_MULTI = "MULTI";

    final Dimension maximumSizeButton = new Dimension(150, 45);
    JButton buttonPlay;
    JButton buttonPause;

    JLabel ipLabel;
    JButton hostButton;
    HealthBar healthBarDisplay;

    TextUI countdown;
    SpellInventory spellInventory;
    JPanel cardPanel;

    AbstractBar scoreBar, scoreBarOther, barrierBarOther, chanceBarOther;
    private SessionManager sessionManager;

    //ScoreBar scoreBar, scoreBarOther; //Uncomment if there is fault with abstract bar.

    private JComboBox<String> comboBoxAddBarrierType;
    private CardLayout cardLayout;

    // New panel for enemy status
    private JPanel enemyStatusPanel;

    public GameView() {
        ScoreManager scoreManager = ScoreManager.getInstance();
        this.sessionManager = SessionManager.getInstance();
        this.scoreBar = new ScoreBar(scoreManager::getScore, true);
        this.scoreBarOther = new ScoreBar(Event.ReceiveScoreUpdate, false);
        this.barrierBarOther = new BarrierBar(Event.ReceiveBarrierCountUpdate, false);
        this.chanceBarOther = new ChanceBar(Event.ReceiveChanceUpdate, false);
        this.sessionManager.initializeSession();
        this.cardLayout = new CardLayout();
        this.cardPanel = new JPanel(cardLayout);
        this.countdown = new TextUI("5"
                ,Color.orange,new Font("IMPACT", Font.PLAIN, 20)
                , 100f,Constants.SCREEN_WIDTH/2,Constants.SCREEN_HEIGHT/2,100);
        add(cardPanel, BorderLayout.CENTER);
        setLayout(cardLayout);
        initializeComponents();
        decorateCardPanel();
        subscribeMethods();
    }

    private void decorateCardPanel() {
        JPanel initPanel = new InitPanel();
        cardPanel.add(initPanel, STATUS_INIT);
        JPanel startPanel = new StartPanel();
        cardPanel.add(startPanel, STATUS_START);
        JPanel parentPanel = createParentPanel();
        cardPanel.add(parentPanel, STATUS_GAME);
        JPanel loseGamePanel = new EndGamePanel();
        cardPanel.add(loseGamePanel, STATUS_END);
        JPanel multiPanel = new MultiplayerPanel();
        cardPanel.add(multiPanel,STATUS_MULTI);
    }

    private void subscribeMethods() {
        Event.StartGame.addRunnableListener(this::startGame);
        Event.Reset.addRunnableListener(this::handleNewGame);
        Event.ResumeGame.addRunnableListener(this::requestFocusInWindow);
        Event.LoadGame.addRunnableListener(Event.CanvasUpdateEvent::invoke);
        Event.LoadGame.addRunnableListener(() -> {
            healthBarDisplay.setHealth(
                    SessionManager.getInstance().getPlayer().getChancesLeft());
        });
        Event.LoadGame.addRunnableListener(() -> {
            showPanel(STATUS_GAME);
            scoreBar.updateValue();
        });
        Event.EndGame.addRunnableListener(() -> {
            showPanel(STATUS_END);
        });
        Event.BuildDoneEvent.addRunnableListener(() -> {
            if(sessionManager.getGameMode() == SessionManager.GameMode.MULTIPLAYER){
                System.out.println("Waiting For other player to connect!!!");
                this.setEnabled(true);
                this.buttonPlay.setFocusable(false);
                return;
            }
            this.setEnabled(true);
            this.requestFocusInWindow(true);

        });
        Event.OtherPlayerJoined.addRunnableListener(()->{
            this.setEnabled(true);
            this.buttonPlay.setEnabled(true);
            this.hostButton.setVisible(false);
            this.requestFocusInWindow(true);
            SessionManager.getInstance().getDrawCanvas().foregroundList.clear();
        });

        Event.JoinedTheHost.addRunnableListener(()->{
            this.setEnabled(true);
            this.buttonPlay.setEnabled(false);
            this.requestFocusInWindow(true);
            ((JFrame)Windows.BuildViewMini.getWindow()).dispose();
            hostButton.setVisible(false);
            hostButton.getParent().remove(hostButton);
        });
        Event.PauseGame.addRunnableListener(() -> {
            buttonPlay.setEnabled(true);
        });
        Event.PauseGame.addRunnableListener(() -> {
            WindowManager.getInstance().showWindow(Windows.PauseView);
        });
        Event.SingleplayerSelected.addRunnableListener(()->showPanel(STATUS_START));
        Event.MultiplayerSelected.addRunnableListener(()->showPanel(STATUS_MULTI));
        Event.ShowInitGame.addRunnableListener(()->showPanel(STATUS_INIT));

        Event.StartCountDown.addRunnableListener(()->{
            SessionManager.getInstance().getDrawCanvas().foregroundList.add(countdown);
        });

        countdown.setAnimationBehaviourOnEvent(new CountdownAnimation(5,countdown::setText,()->{
            Event.StartGame.invoke();
            if(SessionManager.getInstance().getGameMode() == SessionManager.GameMode.MULTIPLAYER) {
                Event.SendGameStarted.invoke();
            }
            SessionManager.getInstance().getDrawCanvas().foregroundList.remove(countdown);
        }), Event.StartCounting);

        Event.ReceiveScoreUpdate.addRunnableListener(System.out::println);
        Event.ReceiveBarrierCountUpdate.addRunnableListener(System.out::println);
    }

    private void initializeComponents() {
        setFocusable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
        setDefaultLookAndFeelDecorated(true);
        addKeyListener(InputManager.getInstance());
        setResizable(false);

        // Initialize enemy status panel
        enemyStatusPanel = createEnemyStatusPanel();
        enemyStatusPanel.setVisible(false);
    }

    public void startGame() {
        scoreBar.updateValue();
        healthBarDisplay.setHealth(SessionManager.getInstance().getPlayer().getChancesLeft());
        comboBoxAddBarrierType.setVisible(false);
        buttonPlay.setEnabled(false);
        buttonPause.setEnabled(true);
        comboBoxAddBarrierType.setVisible(false);
    }

    public void showPanel(String cardName) {
        cardLayout.show(cardPanel, cardName);
    }

    private void handleNewGame() {
        cardLayout.show(cardPanel, STATUS_GAME);
        buttonPause.setEnabled(true);
        buttonPlay.setEnabled(true);
        this.setEnabled(false);
        comboBoxAddBarrierType.setVisible(true);
        sessionManager.setStatus(Status.EditMode);
        WindowManager.getInstance().showWindow(Windows.BuildViewMini);
        if(SessionManager.getInstance().getGameMode().equals(SessionManager.GameMode.MULTIPLAYER)){
            hostButton.setVisible(true);
            ipLabel.setVisible(true);
            buttonPlay.setEnabled(false);
            enemyStatusPanel.setVisible(true);
        } else {
            enemyStatusPanel.setVisible(false);
        }
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
        ipLabel = new JLabel();
        Event.SendIPAddress.addListener((e)->ipLabel.setText((String)e));
        hostButton  = new JButton("Host");
        hostButton.addActionListener(e->{
            Event.TryHostingSession.invoke();
            comboBoxAddBarrierType.setVisible(false);
            SessionManager.getInstance().getDrawCanvas().removeMouseListener();
            System.out.println("Added the foreground item");

            TextUI fading = new TextUI("WAITING FOR OTHER PLAYER"
                    ,Color.orange,new Font("IMPACT", Font.PLAIN, 20)
                    , 50f,Constants.SCREEN_WIDTH/2,Constants.SCREEN_HEIGHT/2,1);
            fading.setAnimationBehaviour(new LinearInterpolation(48,50, fading::getOpacity,fading::setOpacity));
            SessionManager.getInstance()
                    .getDrawCanvas()
                    .foregroundList.add(fading);
            Event.CanvasUpdateEvent.invoke();
        });

        controlPanel.add(hostButton);
        controlPanel.add(ipLabel);
        hostButton.setVisible(false);
        ipLabel.setVisible(false);
        controlPanel.add(Box.createRigidArea(new Dimension(25,10)));

        // Add enemy status panel to the control panel
        controlPanel.add(enemyStatusPanel);

        return controlPanel;
    }

    private JButton startButton() {
        buttonPlay = new JButton("START");
        buttonPlay.setFocusable(false);
        buttonPlay.setFont(new Font("Monospaced", Font.BOLD, 16));
        buttonPlay.addActionListener(e -> {
            Event.StartCountDown.invoke();
        });
        return buttonPlay;
    }

    private JButton pauseButton() {
        buttonPause = new JButton("Pause Game");
        buttonPause.setFocusable(false);
        buttonPause.setFont(new Font("Monospaced", Font.BOLD, 16));
        buttonPause.setEnabled(false);
        buttonPause.addActionListener(e -> {
            Event.PauseGame.invoke();
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

    private JPanel createEnemyStatusPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2),
                "Enemy",
                TitledBorder.CENTER,
                TitledBorder.TOP,
                new Font("Monospaced", Font.BOLD, 16),
                Color.RED
        ));

        JPanel statusPanel = new JPanel();
        statusPanel.setLayout(new GridLayout(1, 3, 10, 0)); // 1 row, 3 columns, with a 10-pixel gap between components
        statusPanel.setBackground(Color.LIGHT_GRAY);

        scoreBarOther.setBackground(Color.LIGHT_GRAY);
        scoreBarOther.setForeground(Color.BLACK);

        barrierBarOther.setBackground(Color.LIGHT_GRAY);
        barrierBarOther.setForeground(Color.BLACK);

        chanceBarOther.setBackground(Color.LIGHT_GRAY);
        chanceBarOther.setForeground(Color.BLACK);

        statusPanel.add(scoreBarOther);
        statusPanel.add(barrierBarOther);
        statusPanel.add(chanceBarOther);

        panel.add(statusPanel, BorderLayout.CENTER);

        return panel;
    }



    @Override
    public void createAndShowUI() {
        setVisible(true);
    }
}

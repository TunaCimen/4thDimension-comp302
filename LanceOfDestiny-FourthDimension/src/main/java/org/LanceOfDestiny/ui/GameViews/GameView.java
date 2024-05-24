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
    ScoreBar scoreBar, scoreBarOther;
    private final SessionManager sessionManager;
    private JComboBox<String> comboBoxAddBarrierType;
    private CardLayout cardLayout;

    public GameView() {
        ScoreManager scoreManager = ScoreManager.getInstance();
        this.sessionManager = SessionManager.getInstance();
        this.scoreBar = new ScoreBar(scoreManager::getScore, true);
        this.scoreBarOther = new ScoreBar(Event.ReceiveScoreUpdate, false);
        this.sessionManager.initializeSession();
        this.cardLayout = new CardLayout();
        this.cardPanel = new JPanel(cardLayout);
        this.countdown = new TextUI("5"
                ,Color.orange,new Font("IMPACT", Font.PLAIN, 20)
                , 75f,Constants.SCREEN_WIDTH/2,Constants.SCREEN_HEIGHT/2,1);
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
        //Events.Load.addRunnableListener(()->showPanel(STATUS_GAME));
        Event.LoadGame.addRunnableListener(() -> {
            showPanel(STATUS_GAME);
            scoreBar.updateScore();
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
        //System.out.println(SessionManager.getInstance().getYmir().retTwoSpellNames());
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
                    , 50f,Constants.SCREEN_WIDTH/2-200,Constants.SCREEN_HEIGHT/2,1);
            fading.setAnimationBehaviour(new LinearInterpolation(48,50, fading::getOpacity,fading::setOpacity));
            SessionManager.getInstance()
                    .getDrawCanvas()
                    .foregroundList.add(fading);
            Event.CanvasUpdateEvent.invoke();
        });
        controlPanel.add(scoreBarOther);
        controlPanel.add(hostButton);
        controlPanel.add(ipLabel);
        hostButton.setVisible(false);
        ipLabel.setVisible(false);
        controlPanel.add(Box.createRigidArea(new Dimension(25,10)));
        return controlPanel;
    }

    private JButton startButton() {
        buttonPlay = new JButton("START");
        buttonPlay.setFocusable(false);
        buttonPlay.setFont(new Font("Monospaced", Font.BOLD, 16));
        buttonPlay.addActionListener(e -> {
            if (!BuildViewMiniPanel.validateAndShowError(BarrierManager.getInstance().getBarrierCounts())) {
                return;
            }

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

    @Override
    public void createAndShowUI() {
        setVisible(true);
    }
}
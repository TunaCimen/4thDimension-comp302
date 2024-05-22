package org.LanceOfDestiny.ui.AuthViews;

import org.LanceOfDestiny.domain.behaviours.Behaviour;
import org.LanceOfDestiny.domain.behaviours.GameObject;
import org.LanceOfDestiny.domain.events.Events;
import org.LanceOfDestiny.domain.managers.BarrierManager;
import org.LanceOfDestiny.domain.managers.ScoreManager;
import org.LanceOfDestiny.domain.managers.SessionManager;
import org.LanceOfDestiny.domain.spells.*;
import org.LanceOfDestiny.ui.UIUtilities.Window;

import javax.swing.*;
import java.awt.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
                try {
                    //TODO: reset needed
                    System.out.println("Before clearing");
                    BarrierManager.displayBarrierInfo();
                    GameObject.displayGameObjects();
                    BarrierManager.getInstance().deleteAllBarriers();
                    RewardBoxFactory.getInstance().removeRewardBoxes();
                    List<Behaviour> list = GameObject.getGameObjects();
                    System.out.println("After clearing");

                    BarrierManager.displayBarrierInfo();
                    GameObject.displayGameObjects();
                    LoadView.this.userManager.loadBarriers(name);
                    ScoreManager.getInstance().setScore(Integer.parseInt(LoadView.this.userManager.loadUserInfo(name).get(0)));
                    SessionManager.getInstance().getPlayer().setChancesLeft(Integer.parseInt(LoadView.this.userManager.loadUserInfo(name).get(1)));
                    SessionManager.getInstance().setTimePassed(Integer.parseInt(LoadView.this.userManager.loadUserInfo(name).get(6)));
                    System.out.println(LoadView.this.userManager.loadUserInfo(name).get(6));
                    SessionManager.getInstance().getPlayer().resetSpells();
                    for(int i=0;i<Integer.parseInt(LoadView.this.userManager.loadUserInfo(name).get(3));i++){
                        Events.GainSpell.invoke(SpellType.EXPANSION);
                    }
                    for(int i=0;i<Integer.parseInt(LoadView.this.userManager.loadUserInfo(name).get(4));i++){
                        Events.GainSpell.invoke(SpellType.OVERWHELMING);
                    }
                    for(int i=0;i<Integer.parseInt(LoadView.this.userManager.loadUserInfo(name).get(5));i++){
                        Events.GainSpell.invoke(SpellType.CANON);
                    }
                    if(!Objects.equals(LoadView.this.userManager.loadUserInfo(name).get(7), "def")){
                        SessionManager.getInstance().getYmir().updateLastTwoAbilitiesFromLoad(LoadView.this.userManager.loadUserInfo(name).get(7),LoadView.this.userManager.loadUserInfo(name).get(8));
                    }
                    System.out.println(SessionManager.getInstance().getPlayer().getSpellContainer().getSpellMap());
                    JOptionPane.showMessageDialog(null, "Game loaded successfully!");
                    System.out.println("After loading");
                    BarrierManager.displayBarrierInfo();
                    GameObject.displayGameObjects();
                    Events.LoadGame.invoke();
                    this.dispose();
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
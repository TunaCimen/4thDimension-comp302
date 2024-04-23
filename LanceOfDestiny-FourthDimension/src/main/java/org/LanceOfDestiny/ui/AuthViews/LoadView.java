package org.LanceOfDestiny.ui.AuthViews;

import org.LanceOfDestiny.domain.barriers.Barrier;
import org.LanceOfDestiny.domain.behaviours.Behaviour;
import org.LanceOfDestiny.domain.behaviours.GameObject;
import org.LanceOfDestiny.domain.managers.BarrierManager;
import org.LanceOfDestiny.domain.managers.ScoreManager;
import org.LanceOfDestiny.domain.managers.SessionManager;
import org.LanceOfDestiny.domain.spells.*;
import org.LanceOfDestiny.ui.Window;

import javax.swing.*;
import java.awt.*;

import java.sql.SQLException;
import java.util.ArrayList;
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
                try {
                    BarrierManager.getInstance().removeAllBarriers();
                    RewardBoxFactory.getInstance().removeRewardBoxes();
                    System.out.println("Barrier Count: " + BarrierManager.getInstance().barriers.size());
                    List<Behaviour> list = GameObject.getGameObjects();
                    GameObject.displayGameObjects();
                    System.out.println("GameObject Count" + GameObject.getGameObjects().size());
                    BarrierManager.getInstance().barriers = (ArrayList<Barrier>) LoadView.this.userManager.loadBarriers(name);
                    ScoreManager.getInstance().setScore(Integer.parseInt(LoadView.this.userManager.loadUserInfo(name).get(0)));
                    SessionManager.getInstance().getPlayer().setChancesLeft(Integer.parseInt(LoadView.this.userManager.loadUserInfo(name).get(1)));
                    SessionManager.getInstance().getPlayer().resetSpells();
                    for(int i=0;i<Integer.parseInt(LoadView.this.userManager.loadUserInfo(name).get(2));i++){
                        SessionManager.getInstance().getPlayer().getSpellContainer().addSpell(SpellFactory.createSpell(SpellType.CHANCE));
                    }
                    for(int i=0;i<Integer.parseInt(LoadView.this.userManager.loadUserInfo(name).get(3));i++){
                        SessionManager.getInstance().getPlayer().getSpellContainer().addSpell(new ExpansionSpell().getSpellType());
                    }
                    for(int i=0;i<Integer.parseInt(LoadView.this.userManager.loadUserInfo(name).get(4));i++){
                        SessionManager.getInstance().getPlayer().getSpellContainer().addSpell(new OverwhelmingFireBallSpell().getSpellType());
                    }
                    for(int i=0;i<Integer.parseInt(LoadView.this.userManager.loadUserInfo(name).get(5));i++){
                        SessionManager.getInstance().getPlayer().getSpellContainer().addSpell(new CanonSpell().getSpellType());
                    }
                    System.out.println(SessionManager.getInstance().getPlayer().getSpellContainer().getSpells());
                    JOptionPane.showMessageDialog(null, "Game loaded successfully!");
                    System.out.println("Barrier Count After: " + BarrierManager.getInstance().barriers.size());
                    System.out.println("GameObject Count After" + GameObject.getGameObjects().size());
                    GameObject.displayGameObjects();
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
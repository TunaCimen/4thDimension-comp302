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
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
        try {
            this.savedNames = userManager.loadSavedNames();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        setTitle("Load View");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(450, 380); // Set initial window size
        setResizable(false); // Make the frame not resizable
        setLocationRelativeTo(null); // Center the frame on the screen

        // Set a custom content pane to draw the gradient background
        JPanel contentPane = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int width = getWidth();
                int height = getHeight();
                Color color1 = Color.decode("#00008B"); // Dark Green
                Color color2 = Color.decode("#800080"); // Dark Red
                GradientPaint gp = new GradientPaint(0, 0, color1, width, height, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, width, height);
            }
        };
        setContentPane(contentPane);

        // Create the button panel to display saved game buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS)); // Vertical box layout
        buttonPanel.setOpaque(false); // Make panel transparent to show gradient background

        // Set a fixed size for the buttons
        Dimension buttonSize = new Dimension(200, 40);

        for (String name : savedNames) {
            JButton button = new JButton(name);
            button.setPreferredSize(buttonSize);
            button.setMaximumSize(buttonSize);
            button.setMinimumSize(buttonSize);
            button.setForeground(Color.WHITE); // White text color
            button.setFocusPainted(true); // Remove focus border
            button.setBorderPainted(true); // Remove border
            button.setAlignmentX(Component.CENTER_ALIGNMENT); // Center horizontally
            button.setBorder(new EmptyBorder(10, 10, 10, 10));
            button.setFont(button.getFont().deriveFont(Font.BOLD, 18f)); // Set text to bold
            button.addActionListener(e -> {
                try {
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
                    LoadView.this.dispose();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            });
            buttonPanel.add(Box.createRigidArea(new Dimension(0, 5))); // Add some space between buttons
            buttonPanel.add(button);
            addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    dispose();
                }
            });
        }

        // Create a scroll pane for the button panel
        JScrollPane scrollPane = new JScrollPane(buttonPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); // Always show vertical scroll bar
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);

        // Add the panels to the frame
        contentPane.add(scrollPane, BorderLayout.CENTER); // Use a scroll pane for file panel

        this.setVisible(true);
    }
}

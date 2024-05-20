package org.LanceOfDestiny.ui.GameViews;

//This panel will be displayed on gameview screen to build game in a more aesthetic way

import org.LanceOfDestiny.domain.events.Events;
import org.LanceOfDestiny.domain.managers.BarrierManager;
import org.LanceOfDestiny.domain.managers.SessionManager;
import org.LanceOfDestiny.ui.CustomViews.CustomDialog;
import org.LanceOfDestiny.ui.Window;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BuildViewMiniPanel extends JFrame implements Window {
    private final SessionManager sessionManager;

    private JTextField textFieldBarrierSimple;
    private JTextField textFieldBarrierReinforced;
    private JTextField textFieldBarrierExplosive;
    private JTextField textFieldBarrierRewarding;
    private Color buildButtonColor = Color.decode("#90caf9");
    private Color testButtonColor = Color.decode("#ffa726");

    public BuildViewMiniPanel(SessionManager instance) {
        this.sessionManager = instance;
    }

    @Override
    public void createAndShowUI() {
        setDefaultLookAndFeelDecorated(true);
        setSize(350, 250);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());


        // BUILD label
        JLabel buildLabel = new JLabel("BUILD YOUR GAME", SwingConstants.CENTER);
        buildLabel.setFont(new Font("Impact", Font.BOLD, 24));
        buildLabel.setForeground(Color.BLACK);
        add(buildLabel, BorderLayout.NORTH);


        //Build panel
        JPanel userInputPanel = new JPanel(new GridLayout(5, 2, 5, 5)); // 5 rows, 2 columns
        // Create an empty border
        Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);// 10 pixels padding on each side
        // Set the border to the panel
        userInputPanel.setBorder(padding);

        // Barrier 1
        JLabel labelBarrier1 = new JLabel("Simple Barrier :");
        textFieldBarrierSimple = new JTextField(10);
        userInputPanel.add(labelBarrier1);
        userInputPanel.add(textFieldBarrierSimple);

        // Barrier 2
        JLabel labelBarrier2 = new JLabel("Reinforced Barrier :");
        textFieldBarrierReinforced = new JTextField(10);
        userInputPanel.add(labelBarrier2);
        userInputPanel.add(textFieldBarrierReinforced);

        // Barrier 3
        JLabel labelBarrier3 = new JLabel("Explosive Barrier :");
        textFieldBarrierExplosive = new JTextField(10);
        userInputPanel.add(labelBarrier3);
        userInputPanel.add(textFieldBarrierExplosive);

        // Barrier 4
        JLabel labelBarrier4 = new JLabel("Rewarding Barrier :");
        textFieldBarrierRewarding = new JTextField(10);
        userInputPanel.add(labelBarrier4);
        userInputPanel.add(textFieldBarrierRewarding);

        // Rounded border with padding for text fields
        Border roundedBorder = BorderFactory.createLineBorder(new Color(150, 150, 150), 2, true);
        Insets textInsets = new Insets(5, 10, 5, 10); // top, left, bottom, right padding


        // Modifying each label and text field for a flatter look
        JLabel[] labels = {labelBarrier1, labelBarrier2, labelBarrier3, labelBarrier4};
        JTextField[] textFields = {textFieldBarrierSimple, textFieldBarrierReinforced,
                textFieldBarrierExplosive, textFieldBarrierRewarding};
        for (int i = 0; i < labels.length; i++) {
            labels[i].setFont(new Font("Monospaced", Font.BOLD, 14));
            labels[i].setForeground(Color.BLACK);
            labels[i].setHorizontalAlignment(JLabel.RIGHT);
            textFields[i].setFont(new Font("Monospaced", Font.BOLD, 14));
            textFields[i].setHorizontalAlignment(JTextField.CENTER);
            textFields[i].setBorder(BorderFactory.createCompoundBorder(
                    roundedBorder, BorderFactory.createEmptyBorder(textInsets.top, textInsets.left, textInsets.bottom, textInsets.right)));
        }



        // Build Game Button
        JButton buttonBuild = new JButton("Build");
        buttonBuild.setFont(new Font("Monospaced", Font.BOLD, 14));
        buttonBuild.setForeground(Color.BLACK);
        buttonBuild.setBackground(buildButtonColor);
        buttonBuild.setOpaque(true);
        buttonBuild.setBorderPainted(false);
        buttonBuild.setFocusPainted(false);
        buttonBuild.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonBuild.setBorder(roundedBorder);
        userInputPanel.add(buttonBuild);
        buttonBuild.addActionListener(e -> {

            int numOfSimple = Integer.parseInt(textFieldBarrierSimple.getText());
            int numOfReinforced = Integer.parseInt(textFieldBarrierReinforced.getText());
            int numOfExplosive = Integer.parseInt(textFieldBarrierExplosive.getText());
            int numOfRewarding = Integer.parseInt(textFieldBarrierRewarding.getText());

            // debug the numbers with system.out.println
            System.out.println("Simple: " + numOfSimple);
            System.out.println("Reinforced: " + numOfReinforced);
            System.out.println("Explosive: " + numOfExplosive);
            System.out.println("Rewarding: " + numOfRewarding);

            String validationError = BarrierManager.getInstance().validateBarrierCounts(numOfSimple, numOfReinforced, numOfExplosive, numOfRewarding);

            if (validationError != null) {
                CustomDialog.showErrorDialog(validationError);
                return;  // Exit if validation fails
            }


            // Set barrier numbers in session manager's builder
            this.sessionManager.getBarrierBuilder().setNumOfSimple(numOfSimple);
            this.sessionManager.getBarrierBuilder().setNumOfReinforced(numOfReinforced);
            this.sessionManager.getBarrierBuilder().setNumOfExplosive(numOfExplosive);
            this.sessionManager.getBarrierBuilder().setNumOfRewarding(numOfRewarding);

            // Hide the build panel and close it
            userInputPanel.setVisible(false);
            this.dispose();

            // Ensure the game canvas is visible and properly updated
            Events.BuildDoneEvent.invoke();
        });
        buttonBuild.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                buttonBuild.setBackground(buttonBuild.getBackground().darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                buttonBuild.setBackground(buildButtonColor);
            }
        });


        // Test Game Button
        JButton buttonTest = new JButton("Test");
        buttonTest.setFont(new Font("Monospaced", Font.BOLD, 14));
        buttonTest.setForeground(Color.BLACK);
        buttonTest.setBackground(testButtonColor);
        buttonTest.setOpaque(true);
        buttonTest.setBorderPainted(false);
        buttonTest.setFocusPainted(false);
        buttonTest.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonTest.setBorder(roundedBorder);
        userInputPanel.add(buttonTest);

        buttonTest.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                buttonTest.setBackground(buttonTest.getBackground().darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                buttonTest.setBackground(testButtonColor);
            }
        });


        // Add button panel to the frame
        add(userInputPanel, BorderLayout.CENTER); // Centered in the layout

        // Center the window
        setLocationRelativeTo(null);

        // Set visible
        setVisible(true);
    }




}

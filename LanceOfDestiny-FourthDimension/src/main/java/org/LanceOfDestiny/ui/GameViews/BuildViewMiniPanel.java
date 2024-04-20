package org.LanceOfDestiny.ui.GameViews;

//This panel will be displayed on gameview screen to build game in a more aesthetic way

import org.LanceOfDestiny.domain.events.Events;
import org.LanceOfDestiny.domain.managers.SessionManager;
import org.LanceOfDestiny.ui.DrawCanvas;
import org.LanceOfDestiny.ui.Window;
import org.LanceOfDestiny.ui.WindowManager;
import org.LanceOfDestiny.ui.Windows;

import javax.swing.*;
import java.awt.*;

public class BuildViewMiniPanel extends JFrame implements Window {
    private final WindowManager wm;
    private final SessionManager sessionManager;

    private JTextField textFieldBarrierSimple;
    private JTextField textFieldBarrierReinforced;
    private JTextField textFieldBarrierExplosive;
    private JTextField textFieldBarrierRewarding;

    public BuildViewMiniPanel(SessionManager instance) {
        wm = WindowManager.getInstance();
        this.sessionManager = instance;

    }

    @Override
    public void createAndShowUI() {

        setSize(300, 250);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        // BUILD label
        JLabel buildLabel = new JLabel("BUILD YOUR GAME", SwingConstants.CENTER);
        buildLabel.setFont(new Font("Impact", Font.BOLD, 24));
        add(buildLabel, BorderLayout.NORTH);


        //Build panel
        JPanel userInputPanel = new JPanel(new GridLayout(5, 2, 10, 10)); // 5 rows, 2 columns


        // Barrier 1
        JLabel labelBarrier1 = new JLabel("Simple Barrier :");
        textFieldBarrierSimple = new JTextField(10);
        userInputPanel.add(labelBarrier1);
        userInputPanel.add(textFieldBarrierSimple);

        // Barrier 2
        JLabel labelBarrier2 = new JLabel("Reinforced Barrier ");
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

        // Play Game Button
        JButton buttonPlay = new JButton("Play Game");
        userInputPanel.add(buttonPlay);
        buttonPlay.addActionListener(e -> {

            int numOfSimple = Integer.parseInt(textFieldBarrierSimple.getText());
            int numOfReinforced = Integer.parseInt(textFieldBarrierReinforced.getText());
            int numOfExplosive = Integer.parseInt(textFieldBarrierExplosive.getText());
            int numOfRewarding = Integer.parseInt(textFieldBarrierRewarding.getText());

            // debug the numbers with system.out.println
            System.out.println("Simple: " + numOfSimple);
            System.out.println("Reinforced: " + numOfReinforced);
            System.out.println("Explosive: " + numOfExplosive);
            System.out.println("Rewarding: " + numOfRewarding);

            if (numOfSimple < 75 || numOfReinforced < 10 || numOfExplosive < 5 || numOfRewarding < 10) {
                JOptionPane.showMessageDialog(null, "Minimum required barriers not met:\n"
                        + "Simple: 75\n"
                        + "Reinforced: 10\n"
                        + "Explosive: 5\n"
                        + "Rewarding: 10");
                return;
            }

            this.sessionManager.getBuilder().setNumOfSimple(numOfSimple);
            this.sessionManager.getBuilder().setNumOfExplosive(numOfExplosive);
            this.sessionManager.getBuilder().setNumOfRewarding(numOfRewarding);
            this.sessionManager.getBuilder().setNumOfReinforced(numOfReinforced);

            userInputPanel.setVisible(false);
            this.dispose();

            //TODO: bu kısımda build panelde play game dediğimizde game objelerini renderlamasını sağlamalıyız
            Events.ResumeGame.invoke();
//            this.sessionManager.getDrawCanvas().repaint();
//            this.sessionManager.getDrawCanvas().revalidate();
//            GameView.getInstance(sessionManager).add(this.sessionManager.getDrawCanvas(), BorderLayout.CENTER);
//            sessionManager.initializeSession();
//            sessionManager.getLoopExecutor().start();

//            DrawCanvas.revalidate();

            // gameview.showcanvas
            //    public void ShowCanvas(){
            //        add(sessionManager.getDrawCanvas(), BorderLayout.CENTER);
            //    }



        });





        // Add button panel to the frame
        add(userInputPanel, BorderLayout.CENTER); // Centered in the layout

        // Center the window
        setLocationRelativeTo(null);

        // Set visible
        setVisible(true);
    }




}

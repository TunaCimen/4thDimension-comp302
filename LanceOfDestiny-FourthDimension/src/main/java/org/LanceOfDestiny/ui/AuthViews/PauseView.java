package org.LanceOfDestiny.ui.AuthViews;

import org.LanceOfDestiny.domain.events.Event;
import org.LanceOfDestiny.domain.sprite.ImageLibrary;
import org.LanceOfDestiny.ui.UIUtilities.GradientPanel;
import org.LanceOfDestiny.ui.UIUtilities.Window;
import org.LanceOfDestiny.ui.UIUtilities.WindowManager;
import org.LanceOfDestiny.ui.UIUtilities.Windows;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class PauseView extends JFrame implements Window {
    private final WindowManager windowManager;
    private static final Dimension maximumSizeButton = new Dimension(140, 60);

    public PauseView() {
        windowManager = WindowManager.getInstance();
        configureWindow();
        addComponents();
        Event.ResumeGame.addRunnableListener(this::dispose);
        Event.EndGame.addListener(c->dispose());
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        // Calculate the new location of the frame
        int frameWidth = getSize().width;
        int frameHeight = getSize().height;
        int x = (screenWidth - frameWidth) / 2;
        int y = (screenHeight - frameHeight) / 2;

        // Set the location of the frame
        setLocation(x, y);
        this.setAlwaysOnTop(true);
    }

    private void configureWindow() {
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private void addComponents() {
        GradientPanel backgroundPanel = new GradientPanel(Color.WHITE, Color.GRAY);
        backgroundPanel.setLayout(new BorderLayout());

        backgroundPanel.add(createLabel("PAUSED", new Font("Arial", Font.BOLD, 24)), BorderLayout.NORTH);
        backgroundPanel.add(createButtonPanel(), BorderLayout.CENTER);

        setContentPane(backgroundPanel);
    }

    @Override
    public void createAndShowUI() {
        setVisible(true);
    }

    private JLabel createLabel(String text, Font font) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(font);
        return label;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);  // Ensure the panel is transparent so the gradient shows through
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(createButton("Resume", this::handleResume));
        panel.add(Box.createVerticalStrut(10));
        panel.add(createButton("Save", () -> windowManager.showWindow(Windows.SaveView)));
        panel.add(Box.createVerticalStrut(10));
        panel.add(createButton("Load", () -> windowManager.showWindow(Windows.LoadView)));
        panel.add(Box.createVerticalStrut(10));
        panel.add(createButton("MainScreen", () -> {
            Event.ShowInitGame.invoke();
            dispose();
        }));
        panel.add(Box.createVerticalStrut(10));
        panel.add(createButton("Help", this::showHelpScreen)); // Assuming a method 'showHelp()' to be implemented.

        return panel;
    }

    public static JButton createButton(String buttonText, Runnable r) {
        JButton newButton = new JButton(buttonText);
        newButton.setFont(new Font("Monospaced", Font.BOLD, 15));
        newButton.setMaximumSize(maximumSizeButton);
        newButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        newButton.addActionListener(e -> r.run());
        return newButton;
    }

    private void handleResume() {
        Event.ResumeGame.invoke();
        Event.SendResumeUpdate.invoke();
    }

    private void showHelpScreen() {
        JDialog helpDialog = new JDialog(this, "Help Screen", true);
        helpDialog.setSize(new Dimension(1280, 720)); // Manageable size
        helpDialog.setResizable(false);
        helpDialog.setLocationRelativeTo(this);

        try {
            BufferedImage helpImage = ImageLibrary.HelpScreen.getImage();
            // Scale the image to fit the dialog
            Image scaledImage = helpImage.getScaledInstance(helpDialog.getWidth(), helpDialog.getHeight(), Image.SCALE_SMOOTH);
            ImageIcon helpImageIcon = new ImageIcon(scaledImage);
            JLabel helpLabel = new JLabel(helpImageIcon);
            helpDialog.add(helpLabel);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading help image.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        helpDialog.setVisible(true);
    }


}

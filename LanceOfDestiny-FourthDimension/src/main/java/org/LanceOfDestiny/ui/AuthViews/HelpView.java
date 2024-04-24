package org.LanceOfDestiny.ui.AuthViews;

import javax.swing.*;
import java.awt.*;
import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.ui.Window;
import org.LanceOfDestiny.domain.sprite.ImageLibrary;
import org.LanceOfDestiny.domain.sprite.ImageOperations;

public class HelpView extends JFrame implements Window {

    private static HelpView instance = null;
    private JLabel backgroundLabel;
    private Image backgroundImage;

    private HelpView() {
        initializeComponents();
    }

    public static HelpView getInstance() {
        if (instance == null) {
            instance = new HelpView();
        }
        return instance;
    }

    private void initializeComponents() {
        setFocusable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
        setResizable(false);

        backgroundLabel = new JLabel();
        add(backgroundLabel, BorderLayout.CENTER);
    }

    public void setBackgroundImage(Image image) {
        ImageIcon imageIcon = new ImageIcon(image);
        Image scaledImage = imageIcon.getImage().getScaledInstance(
                Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, Image.SCALE_SMOOTH);
        backgroundLabel.setIcon(new ImageIcon(scaledImage));
    }

    public void setUpComponents() {
        int width = Constants.SCREEN_WIDTH;
        int height = Constants.SCREEN_HEIGHT;

        // Load your image and resize
        this.backgroundImage = new ImageIcon(
                ImageOperations.resizeImage(ImageLibrary.HelpViewBackground.getImage(), width, height)
        ).getImage();

        setBackgroundImage(backgroundImage);
    }

    public void createAndShowUI() {
        setUpComponents();
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HelpView helpView = HelpView.getInstance();
            helpView.createAndShowUI();
        });
    }
}

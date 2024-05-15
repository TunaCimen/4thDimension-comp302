package org.LanceOfDestiny.ui;

import org.LanceOfDestiny.domain.events.Events;

import javax.swing.*;
import java.awt.*;

public class UILibrary {

    final static Dimension maximumSizeButton = new Dimension(150, 45);

    public static JButton createButton(String buttonText, Runnable r) {
        JButton newButton = new JButton(buttonText);
        newButton.setFont(new Font("Monospaced", Font.BOLD, 15));
        newButton.setMaximumSize(maximumSizeButton);
        newButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        newButton.addActionListener(e -> {
            r.run();
        });
        return newButton;
    }

    public static JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Impact", Font.BOLD, 50));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }
}

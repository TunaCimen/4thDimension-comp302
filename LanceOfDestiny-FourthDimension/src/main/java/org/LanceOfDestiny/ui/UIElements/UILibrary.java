package org.LanceOfDestiny.ui.UIElements;

import org.LanceOfDestiny.domain.events.Events;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

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
        label.setForeground(Color.white);
        label.setFont(new Font("Impact", Font.BOLD, 75));
        label.setOpaque(false);
        //label.setBackground(Color.BLUE);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        //label.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1)); // Add a white border
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }


}

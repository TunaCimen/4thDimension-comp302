package org.LanceOfDestiny.ui.CustomViews;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.Icon;
import java.awt.*;

public class CustomDialog {

    public static void showErrorDialog(String errorMessage) {
        String title = "Validation Error";
        Icon errorIcon = UIManager.getIcon("OptionPane.errorIcon");

        // Improved HTML message style
        String htmlMessage = "<html><body style='width: 300px; padding: 20px; font-family: Arial, sans-serif;'>"
                + "<h2 style='color: #D8000C; font-size: 18px; margin-bottom: 10px;'>Error!</h2>"
                + "<p style='font-size: 14px; color: black;'>"  // Changed color to black
                + errorMessage.replace("\n", "<br>")
                + "</p></body></html>";


        // Customize button text
        Object[] options = {"Understood"};

        // Customize the look and feel
        UIManager.put("OptionPane.background", new Color(255, 255, 255));
        UIManager.put("Panel.background", new Color(255, 255, 255));
        UIManager.put("OptionPane.buttonFont", new Font("Arial", Font.BOLD, 12));
        UIManager.put("OptionPane.messageFont", new Font("Arial", Font.PLAIN, 14));
        UIManager.put("OptionPane.buttonColor", new Color(220, 220, 220));

        // Show the option pane
        JOptionPane.showOptionDialog(null, htmlMessage, title,
                JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE,
                errorIcon, options, options[0]);
    }
}

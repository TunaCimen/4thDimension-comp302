package org.LanceOfDestiny.ui.CustomViews;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.Icon;
import java.awt.*;

public class CustomDialog {

    public static void showErrorDialog(String errorMessage) {
        String title = "Validation Error";
        Icon errorIcon = UIManager.getIcon("OptionPane.errorIcon");

        String htmlMessage = "<html><body style='width: 200px; padding: 10px;'>" +
                "<h2>Error!</h2><p style='font-size: 12px;'>" +
                errorMessage.replace("\n", "<br>") +
                "</p></body></html>";

        // Customize button text
        Object[] options = {"Understood"};

        // Customize the look and feel
        UIManager.put("OptionPane.background", new Color(255, 245, 245));
        UIManager.put("Panel.background", new Color(255, 245, 245));
        UIManager.put("OptionPane.buttonFont", new Font("Monospaced", Font.BOLD, 12));
        UIManager.put("OptionPane.messageFont", new Font("Dialog", Font.PLAIN, 14));
        UIManager.put("OptionPane.buttonColor", Color.decode("#ce93d8"));

        // Show the option pane
        JOptionPane.showOptionDialog(null, htmlMessage, title,
                JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE,
                errorIcon, options, options[0]);
    }
}

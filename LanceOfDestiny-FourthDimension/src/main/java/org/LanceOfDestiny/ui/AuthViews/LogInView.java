package org.LanceOfDestiny.ui.AuthViews;
import org.LanceOfDestiny.domain.AuthModels.LogInController;
import org.LanceOfDestiny.ui.Window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * These classes are demo specific and subject to change.
 */
public class LogInView implements Window {
    private LogInController userManager;

    public LogInView(LogInController userManager) {

        this.userManager = userManager;
    }


    public void setUserManager(LogInController userManager){
        this.userManager = userManager;
    }

    public void createAndShowUI() {
        final JFrame window = new JFrame("Welcome");
        window.setDefaultCloseOperation(2);
        window.setSize(400, 250);
        window.setLocationRelativeTo((Component)null);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10));
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setHorizontalAlignment(4);
        panel.add(usernameLabel);
        final JTextField usernameField = new JTextField();
        panel.add(usernameField);
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setHorizontalAlignment(4);
        panel.add(passwordLabel);
        final JPasswordField passwordField = new JPasswordField();
        panel.add(passwordField);
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String enteredUsername = usernameField.getText();
                String enteredPassword = new String(passwordField.getPassword());

                try {
                    if (LogInView.this.userManager.loginUser(enteredUsername, enteredPassword)) {
                        JOptionPane.showMessageDialog(window, "Login successful!");
                    } else {
                        JOptionPane.showMessageDialog(window, "Invalid username or password.");
                    }
                } catch (IOException var5) {
                    var5.printStackTrace();
                    JOptionPane.showMessageDialog(window, "Error occurred. Please try again later.");
                }

            }
        });
        panel.add(loginButton);
        JButton signupButton = new JButton("Signup");
        signupButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                try {
                    if (LogInView.this.userManager.addUser(username, password)) {
                        JOptionPane.showMessageDialog(window, "Signup successful!");
                    } else {
                        JOptionPane.showMessageDialog(window, "Error occurred. Please try again.");
                    }
                } catch (IOException var5) {
                    var5.printStackTrace();
                    JOptionPane.showMessageDialog(window, "Error occurred. Please try again later.");
                }

            }
        });
        panel.add(signupButton);
        window.add(panel);
        window.setVisible(true);
    }

}
package org.LanceOfDestiny;
import org.LanceOfDestiny.ui.WindowManager;
import org.LanceOfDestiny.ui.Windows;

import javax.swing.*;
import java.sql.*;

public class LanceOfDestiny {
    private static LanceOfDestiny instance;
    private final JFrame mainFrame = new JFrame();
    public static void main(String[] args) throws SQLException {
        WindowManager wm = WindowManager.getInstance();
       // wm.showWindow(Windows.Login);

    }
    public static LanceOfDestiny getInstance(){
        if(instance == null){
            instance = new LanceOfDestiny();
        }
        return instance;
    }
    public JFrame getMainFrame() {
        return mainFrame;
    }
}

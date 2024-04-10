package org.LanceOfDestiny;
import org.LanceOfDestiny.ui.WindowManager;
import org.LanceOfDestiny.ui.Windows;

import java.sql.*;

public class LanceOfDestiny {
    public static void main(String[] args) throws SQLException {
        WindowManager wm = WindowManager.getInstance();
        wm.showWindow(Windows.Login);


    }
}

package org.LanceOfDestiny;
import org.LanceOfDestiny.domain.events.Events;
import org.LanceOfDestiny.ui.WindowManager;
import org.LanceOfDestiny.ui.Windows;

import java.sql.*;

public class LanceOfDestiny {
    private static LanceOfDestiny instance;

    public static void main(String[] args) throws SQLException {

        WindowManager wm = WindowManager.getInstance();
        wm.showWindow(Windows.Login);
    }
}

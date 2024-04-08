package org.LanceOfDestiny;
import org.LanceOfDestiny.ui.WindowManager;
import org.LanceOfDestiny.ui.Windows;

import java.sql.Connection;
import java.sql.DriverManager;

public class LanceOfDestiny {
    public static void main(String[] args) {
        WindowManager wm = WindowManager.getInstance();
        wm.showWindow(Windows.Login);

    }
}

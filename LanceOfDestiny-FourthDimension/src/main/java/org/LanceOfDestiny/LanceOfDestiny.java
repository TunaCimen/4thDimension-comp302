package org.LanceOfDestiny;
import org.LanceOfDestiny.ui.WindowManager;
import org.LanceOfDestiny.ui.Windows;

import java.sql.*;

public class LanceOfDestiny {
    public static void main(String[] args) throws SQLException {
        WindowManager wm = WindowManager.getInstance();
        wm.showWindow(Windows.Login);
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1:3306/Users302",
                "root",
                "Whitewolf2206"
        );
        Statement statement = connection.createStatement();
        ResultSet resultset = statement.executeQuery("SELECT * FROM USERS");
        while(resultset.next()) {
            System.out.println(resultset.getString("username"));
        }

    }
}

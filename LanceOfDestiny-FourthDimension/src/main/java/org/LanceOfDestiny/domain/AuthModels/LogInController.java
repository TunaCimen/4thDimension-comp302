package org.LanceOfDestiny.domain.AuthModels;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import org.LanceOfDestiny.database.DatabaseController;
import java.io.IOException;
import java.sql.SQLException;

public class LogInController {
    private static LogInController instance;
    private final DatabaseController dbController;

    public static LogInController getInstance()  {
        try {
            if (instance == null) {
                instance = new LogInController();
            }
            return instance;
        } catch (SQLException e) {
            // Log the exception or perform error handling
            e.printStackTrace(); // Or handle the exception in a meaningful wa
        }
        return null;
    }


    private LogInController() throws SQLException {
        this.dbController = new DatabaseController();
    }

    public boolean addUser(String username, String password) throws SQLException {
        return dbController.addUser(username, password);
    }

    public boolean loginUser(String username, String password) throws SQLException {
        return dbController.loginUser(username, password);
    }
}

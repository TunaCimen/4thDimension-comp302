package org.LanceOfDestiny.domain.AuthModels;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import org.LanceOfDestiny.database.DatabaseController;
import java.io.IOException;

public class LogInController {
    private static final String FILE_NAME = "user_data.txt";

    private static LogInController instance;
    private DatabaseController dbController;

    public static LogInController getInstance(){
        if(instance == null){
            instance = new LogInController();
        }
        return instance;
    }

    private LogInController() {
        this.dbController = new DatabaseController();
    }

    public boolean addUser(String username, String password) throws IOException {
        return dbController.addUser(username, password);
    }

    public boolean loginUser(String username, String password) throws IOException {
        return dbController.loginUser(username, password);
    }
}

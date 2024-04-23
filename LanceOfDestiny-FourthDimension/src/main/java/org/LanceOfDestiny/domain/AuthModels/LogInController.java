package org.LanceOfDestiny.domain.AuthModels;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import org.LanceOfDestiny.database.DatabaseController;
import org.LanceOfDestiny.domain.barriers.Barrier;

import java.sql.SQLException;
import java.util.List;

public class LogInController {
    private static LogInController instance;
    private  DatabaseController dbController;

    private String username;

    public static LogInController getInstance()  {
        try {
            if (instance == null) {
                instance = new LogInController();
            }
            return instance;
        } catch (SQLException e) {
            e.printStackTrace();
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
        this.username = username;
        return dbController.loginUser(username, password);
    }
    public boolean saveGame(String saveName, List<Barrier> blist,int score, int chances, String numberofSpells) throws SQLException {
        return dbController.saveGame(this.username,saveName, blist,score,chances,numberofSpells);
    }
    public List<Barrier> loadBarriers(String saveName){
        return dbController.loadBarriers(this.username,saveName);
    }
    public List<String> loadUserInfo(String saveName) throws SQLException {
        return dbController.loadUserInfo(this.username,saveName);
    }
    public List<String> loadSavedNames(){
        return dbController.loadSavedNames(this.username);
    }

    public String getUsername() {
        return username;
    }
}

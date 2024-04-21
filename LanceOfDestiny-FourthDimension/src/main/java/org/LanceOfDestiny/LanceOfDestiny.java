package org.LanceOfDestiny;
import org.LanceOfDestiny.domain.GameMap;
import org.LanceOfDestiny.ui.WindowManager;
import org.LanceOfDestiny.ui.Windows;

import javax.swing.*;
import java.sql.*;

public class LanceOfDestiny {
    private static LanceOfDestiny instance;
    private final JFrame mainFrame = new JFrame();
    private static GameMap gameMap;

    public static void main(String[] args) throws SQLException {
        gameMap = GameMap.getInstance();
        WindowManager wm = WindowManager.getInstance();
        wm.showWindow(Windows.Login);
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

    public GameMap getGameMap() {
        if(gameMap == null){
            gameMap = GameMap.getInstance();
        }
        return gameMap;
    }

    public void setGameMap(GameMap gameMap) {
        LanceOfDestiny.gameMap = gameMap;
    }
}

package main.java.org.LanceOfDestiny.UI;

import main.java.org.LanceOfDestiny.login.FirstScreen;

import javax.swing.*;

public class WindowManager {

    private JFrame frame;
    private static WindowManager instance;



    private WindowManager(){

    }

    public void showWindow(Windows window){
        window.window.createAndShowUI();
    }

    public static WindowManager getInstance(){
        if(instance == null){
            instance = new WindowManager();
        }
        return instance;
    }

    public JFrame getFrame() {
        return frame;
    }
}

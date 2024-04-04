package main.java.org.LanceOfDestiny.UI;

import javax.swing.*;

public class WindowCreator {

    private JFrame frame;
    private static WindowCreator instance;

    private WindowCreator(){

    }

    public static WindowCreator getInstance(){
        if(instance == null){
            instance = new WindowCreator();
        }
        return instance;
    }

    public JFrame getFrame() {
        return frame;
    }
}

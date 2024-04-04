package main.java.org.LanceOfDestiny.UI;

import javax.swing.*;
import java.awt.*;

public class WindowCreater {

    private JFrame frame;
    private static WindowCreater instance;

    private WindowCreater(){

    }

    public static WindowCreater getInstance(){
        if(instance == null){
            instance = new WindowCreater();
        }
        return instance;
    }

    public JFrame getFrame() {
        return frame;
    }
}

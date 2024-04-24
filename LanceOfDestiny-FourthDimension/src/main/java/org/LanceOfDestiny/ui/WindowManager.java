package org.LanceOfDestiny.ui;

import javax.swing.*;

public class WindowManager {

    private JFrame frame;
    private static WindowManager instance;

    private WindowManager(){

    }

    public void showWindow(Windows windowType) {
        Window window = windowType.getWindow(); // Create and Retrieve the window instance
        window.createAndShowUI(); //
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

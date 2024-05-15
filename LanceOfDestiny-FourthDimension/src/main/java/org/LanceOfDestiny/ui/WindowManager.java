package org.LanceOfDestiny.ui;

import org.LanceOfDestiny.domain.events.Events;

import javax.swing.*;

public class WindowManager {

    private static WindowManager instance;

    private WindowManager(){
    }

    public void showWindow(Windows windowType) {
        windowType.getWindow().createAndShowUI();
    }

    public static WindowManager getInstance(){
        if(instance == null){
            instance = new WindowManager();
        }
        return instance;
    }

}

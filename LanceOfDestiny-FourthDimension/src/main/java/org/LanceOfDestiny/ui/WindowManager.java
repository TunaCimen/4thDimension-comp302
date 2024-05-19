package org.LanceOfDestiny.ui;

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

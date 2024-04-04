package main.java.org.LanceOfDestiny;

import main.java.org.LanceOfDestiny.UI.WindowCreater;
import main.java.org.LanceOfDestiny.login.FirstScreen;
import main.java.org.LanceOfDestiny.login.Model;

public class Main {
    public static void main(String[] args) {
        Model userManager = new Model();
        FirstScreen userInterface = new FirstScreen(userManager);
        userInterface.createAndShowUI();
    }
}

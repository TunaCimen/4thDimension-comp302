package main.java.org.LanceOfDestiny;
import main.java.org.LanceOfDestiny.UI.WindowManager;
import main.java.org.LanceOfDestiny.UI.Windows;
public class Main {
    public static void main(String[] args) {
        WindowManager wm = WindowManager.getInstance();
        wm.showWindow(Windows.Login);
    }
}

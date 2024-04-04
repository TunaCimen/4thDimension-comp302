package main.java.org.LanceOfDestiny.UI;

import main.java.org.LanceOfDestiny.login.FirstScreen;
import main.java.org.LanceOfDestiny.login.Model;

import javax.swing.*;

public enum Windows{
        Login("Login", new FirstScreen(Model.getInstance()));

        final String val;
        final Window window;

        Windows(String val, Window window){
        this.val = val;
        this.window = window;
        }
        }

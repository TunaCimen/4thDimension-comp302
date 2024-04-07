package org.LanceOfDestiny.ui;

import org.LanceOfDestiny.domain.login.FirstScreen;
import org.LanceOfDestiny.domain.login.Model;

public enum Windows{
        Login("Login", new FirstScreen(Model.getInstance()));

        final String val;
        final Window window;

        Windows(String val, Window window){
        this.val = val;
        this.window = window;
        }
        }

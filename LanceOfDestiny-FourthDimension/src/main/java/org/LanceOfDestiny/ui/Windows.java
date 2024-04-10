package org.LanceOfDestiny.ui;

import org.LanceOfDestiny.domain.AuthModels.LogInController;
import org.LanceOfDestiny.ui.AuthViews.LogInView;

public enum Windows{
        Login("Login", new LogInView(LogInController.getInstance()));

        final String val;
        final Window window;

        Windows(String val, Window window){
        this.val = val;
        this.window = window;
        }
}

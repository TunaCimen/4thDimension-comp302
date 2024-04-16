package org.LanceOfDestiny.ui;

import org.LanceOfDestiny.domain.managers.SessionManager;
import org.LanceOfDestiny.ui.AuthViews.PhysicsTestView;
import org.LanceOfDestiny.ui.GameViews.GameView;

public enum Windows{
        //Login(new LogInView(LogInController.getInstance())),
        PhysicsTest(new PhysicsTestView()),
        GameView(new GameView(SessionManager.getInstance()));

        final Window window;

        Windows(Window window){
        this.window = window;
        }
}

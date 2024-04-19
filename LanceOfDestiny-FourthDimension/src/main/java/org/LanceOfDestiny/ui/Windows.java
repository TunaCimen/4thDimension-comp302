package org.LanceOfDestiny.ui;

import org.LanceOfDestiny.domain.managers.SessionManager;
import org.LanceOfDestiny.ui.AuthViews.*;
import org.LanceOfDestiny.ui.GameViews.BuildView;
import org.LanceOfDestiny.ui.GameViews.GameView;

public enum Windows{
        Login(new LogInView(org.LanceOfDestiny.domain.AuthModels.LogInController.getInstance())),
        PhysicsTest(new PhysicsTestView()),
        BuildView(new BuildView(SessionManager.getInstance())),
        GameView(new GameView(SessionManager.getInstance())),
        PauseView(new PauseView()),
        Signup(new SignUpView(org.LanceOfDestiny.domain.AuthModels.LogInController.getInstance())),
        SaveView(new SaveView()),
        LoadView(new LoadView());

        final Window window;

        Windows(Window window){
        this.window = window;
        }
}

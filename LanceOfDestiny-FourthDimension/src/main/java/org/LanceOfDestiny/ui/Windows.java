package org.LanceOfDestiny.ui;

import org.LanceOfDestiny.domain.AuthModels.LogInController;
import org.LanceOfDestiny.ui.AuthViews.LogInView;
import org.LanceOfDestiny.ui.AuthViews.PhysicsTestView;
import org.LanceOfDestiny.ui.AuthViews.SaveLoadViewDEMO;
import org.LanceOfDestiny.ui.AuthViews.SignUpView;

public enum Windows{
        Login(new LogInView(LogInController.getInstance())),
        Signup(new SignUpView(LogInController.getInstance())),
        PhysicsTest(new PhysicsTestView()),
        DBdemo(new SaveLoadViewDEMO(LogInController.getInstance()));

        final Window window;

        Windows(Window window){
        this.window = window;
        }
}

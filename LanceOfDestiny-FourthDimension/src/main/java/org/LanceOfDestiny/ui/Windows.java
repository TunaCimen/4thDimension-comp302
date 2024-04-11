package org.LanceOfDestiny.ui;

import org.LanceOfDestiny.domain.AuthModels.LogInController;
import org.LanceOfDestiny.ui.AuthViews.LogInView;
import org.LanceOfDestiny.ui.AuthViews.PhysicsTestView;

public enum Windows{
        //Login(new LogInView(LogInController.getInstance())),
        PhysicsTest(new PhysicsTestView());

        final Window window;

        Windows(Window window){
        this.window = window;
        }
}

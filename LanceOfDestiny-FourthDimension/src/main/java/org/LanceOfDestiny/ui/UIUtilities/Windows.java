package org.LanceOfDestiny.ui.UIUtilities;

import org.LanceOfDestiny.domain.authModels.LogInController;
import org.LanceOfDestiny.ui.AuthViews.*;
import org.LanceOfDestiny.ui.GameViews.BuildViewMiniPanel;
import org.LanceOfDestiny.ui.GameViews.GameView;

public enum Windows {
        Login(new LogInView(LogInController.getInstance())),
        PhysicsTest(new PhysicsTestView()),
        GameViewWindow(new GameView()),
        PauseView (new PauseView()),
        Signup(new SignUpView(LogInController.getInstance())),
        SaveView(new SaveView()),
        LoadView(new LoadView()),
        BuildViewMini(new BuildViewMiniPanel());
        private final Window window;
        Windows(Window window){
                this.window = window;
        }

        public Window getWindow(){
                return window;
        }
}

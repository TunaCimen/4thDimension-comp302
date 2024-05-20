package org.LanceOfDestiny.ui.UIUtilities;

import org.LanceOfDestiny.domain.AuthModels.LogInController;
import org.LanceOfDestiny.domain.managers.SessionManager;
import org.LanceOfDestiny.ui.AuthViews.*;
import org.LanceOfDestiny.ui.GameViews.BuildViewMiniPanel;
import org.LanceOfDestiny.ui.GameViews.GameView;
import org.LanceOfDestiny.ui.UIUtilities.Window;

public enum Windows {
        Login(new LogInView(LogInController.getInstance())),
        PhysicsTest(new PhysicsTestView()),
        GameViewWindow(new GameView()),
        PauseView (new PauseView()),
        Signup(new SignUpView(LogInController.getInstance())),
        SaveView(new SaveView()),
        LoadView(new LoadView()),
        BuildViewMini(new BuildViewMiniPanel(SessionManager.getInstance()));
        private final Window window;
        Windows(Window window){
                this.window = window;
        }

        public Window getWindow(){
                return window;
        }
}

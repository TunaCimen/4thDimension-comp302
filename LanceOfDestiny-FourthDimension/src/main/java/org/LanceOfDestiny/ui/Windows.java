package org.LanceOfDestiny.ui;

import org.LanceOfDestiny.domain.managers.SessionManager;
import org.LanceOfDestiny.ui.AuthViews.*;
import org.LanceOfDestiny.ui.GameViews.BuildView;
import org.LanceOfDestiny.ui.GameViews.BuildViewMiniPanel;
import org.LanceOfDestiny.ui.GameViews.GameView;

public enum Windows {
        Login {
                @Override
                Window createWindow() {
                        return new LogInView(org.LanceOfDestiny.domain.AuthModels.LogInController.getInstance());
                }
        },
        PhysicsTest {
                @Override
                Window createWindow() {
                        return new PhysicsTestView();
                }
        },
        BuildView {
                @Override
                Window createWindow() {
                        return new BuildView(SessionManager.getInstance());
                }
        },
        GameViewWindow {
                @Override
                Window createWindow() {
                        return GameView.getInstance(SessionManager.getInstance());
                }
        },
        PauseView {
                @Override
                Window createWindow() {
                        return new PauseView();
                }
        },
        Signup {
                @Override
                Window createWindow() {
                        return new SignUpView(org.LanceOfDestiny.domain.AuthModels.LogInController.getInstance());
                }
        },
        SaveView {
                @Override
                Window createWindow() {
                        return new SaveView();
                }
        },
        LoadView {
                @Override
                Window createWindow() {
                        return new LoadView();
                }
        },
        BuildViewMini {
                @Override
                Window createWindow() {
                        return new BuildViewMiniPanel(SessionManager.getInstance());
                }
        };

        abstract Window createWindow();

        public Window getWindow() {
                return createWindow();
        }
}

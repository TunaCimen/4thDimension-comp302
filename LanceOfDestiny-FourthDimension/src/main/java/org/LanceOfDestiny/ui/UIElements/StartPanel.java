package org.LanceOfDestiny.ui.UIElements;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.events.Events;
import org.LanceOfDestiny.ui.UIUtilities.WindowManager;
import org.LanceOfDestiny.ui.UIUtilities.Windows;

import javax.swing.*;
import java.awt.*;

public class StartPanel extends JPanel {

    public StartPanel() {
        setLayout(new BorderLayout());
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(Box.createRigidArea(new Dimension(0, Constants.SCREEN_HEIGHT / 2 - 100)));
        add(UILibrary.createLabel("Lance Of Destiny"));
        add(Box.createRigidArea(new Dimension(0, 15)));
        add(UILibrary.createButton("NEW_GAME", Events.Reset::invoke));
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(UILibrary.createButton("LOAD", ()->{
            Events.Load.invoke();
            WindowManager.getInstance().showWindow(Windows.LoadView);
        }));
    }

    }

package org.LanceOfDestiny.ui.UIUtilities;

import org.LanceOfDestiny.domain.managers.SessionManager;
import org.LanceOfDestiny.domain.managers.Status;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public abstract class MouseHandler {
    protected JPanel parentPanel;

    public MouseHandler(JPanel panel) {
        this.parentPanel = panel;
        setupMouseListener();
    }

    private void setupMouseListener() {
        MouseAdapter mouseHandler = new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getClickCount() > 1) {
                    // Ignore double clicks or more
                    return;}
                else {
                    if (SessionManager.getInstance().getStatus() == Status.EditMode){
                        handleMousePress(e);
                    }
                    else return;
                }
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SessionManager.getInstance().getStatus() == Status.EditMode){
                    handleMouseClick(e);

                }
                else return;

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if ( e.getClickCount() > 1) {
                    // Ignore double clicks or more
                    return;}
                else {
                    if (SessionManager.getInstance().getStatus() == Status.EditMode){
                        handleMouseRelease(e);
                    }
                    else return;
                }

            }
        };
        parentPanel.addMouseListener(mouseHandler);
    }
    public abstract void handleMousePress(MouseEvent e);

    protected abstract void handleMouseRelease(MouseEvent e);

    protected abstract void handleMouseClick(MouseEvent e);

    protected abstract void handleDoubleClick(int x, int y);

    protected abstract void handleRightClick(int x, int y);
}

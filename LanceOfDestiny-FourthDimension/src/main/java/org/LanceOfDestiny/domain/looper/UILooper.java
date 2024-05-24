package org.LanceOfDestiny.domain.looper;

import javax.swing.*;

public class UILooper extends Looper{

    UIExec uiExec;

    public UILooper(JPanel drawCanvas){
        uiExec = new UIExec(drawCanvas);
    }

    @Override
    protected void routine() throws LoopEndedException {
        execute(uiExec);
    }
}

package org.LanceOfDestiny.ui.Animation;

import org.LanceOfDestiny.domain.behaviours.AnimationBehaviour;
import org.LanceOfDestiny.domain.managers.SessionManager;

import java.util.function.Consumer;

public class CountdownAnimationBehaviour extends AnimationBehaviour {

    int count;
    int timePassed, initTime;

    Consumer<String> setter;

    Runnable cont;

    CountdownAnimationBehaviour(int count, Consumer<String> setter, Runnable cont){
      this.count = count;
      this.setter = setter;
      this.cont = cont;
      timePassed = 0;
      initTime = SessionManager.getInstance().UILoopExecutor.getSecondsPassed();
    }
    CountdownAnimationBehaviour(int count, Consumer<String> setter){
        this.count = count;
        this.setter = setter;
        timePassed = 0;
        initTime = SessionManager.getInstance().UILoopExecutor.getSecondsPassed();
    }

    @Override
    public void onAnimate() {
        int timePassed = SessionManager.getInstance().UILoopExecutor.getSecondsPassed() - initTime;
        if(timePassed<= count){
            setter.accept(Integer.toString(count - timePassed));
        }
        else{
            cont.run();
            killAnimation();
        }


    }




}

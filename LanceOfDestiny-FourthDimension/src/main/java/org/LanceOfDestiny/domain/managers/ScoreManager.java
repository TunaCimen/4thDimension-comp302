package org.LanceOfDestiny.domain.managers;

import org.LanceOfDestiny.domain.events.Event;
import org.LanceOfDestiny.domain.looper.LoopExecutor;

public class ScoreManager {

    private static ScoreManager instance;
    private int score;
    private final LoopExecutor loopExecutor;

    private ScoreManager() {
        this.score = 0;
        loopExecutor = SessionManager.getInstance().getLoopExecutor();
        Event.UpdateScore.addRunnableListener(this::updateScore);
        Event.Reset.addRunnableListener(()->setScore(0));
    }

    public static ScoreManager getInstance() {
        if (instance == null) instance = new ScoreManager();
        return instance;
    }

    private void updateScore() {
        var seconds = loopExecutor.getSecondsPassed();
        if(seconds == 0) seconds = 1;
        setScore(getScore() + 300 / (seconds));
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }


}

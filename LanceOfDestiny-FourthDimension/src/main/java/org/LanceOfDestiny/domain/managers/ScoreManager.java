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

    // newScore = oldScore + 300 / (currentTime - gameStartingTime)
    private void updateScore() {
        setScore(getScore() + 300 / (loopExecutor.getSecondsPassed()));
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }


}

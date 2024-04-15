package org.LanceOfDestiny.domain.managers;

import org.LanceOfDestiny.domain.EventSystem.Events;
import org.LanceOfDestiny.domain.Looper.LoopExecutor;

public class ScoreManager {

    private static ScoreManager instance;
    private int score;
    private final LoopExecutor loopExecutor;

    private ScoreManager() {
        this.score = 0;
        loopExecutor = SessionManager.getInstance().getLoopExecutor();
        Events.UpdateScore.addRunnableListener(this::updateScore);
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

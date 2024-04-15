package org.LanceOfDestiny.domain.managers;

import org.LanceOfDestiny.domain.EventSystem.Events;
import org.LanceOfDestiny.domain.Looper.LoopExecutor;

import java.time.LocalTime;

public class ScoreManager {

    private static ScoreManager instance;
    private int score;
    private LoopExecutor loopExecutor;

    private ScoreManager() {
        this.score = 0;
        Events.UpdateScore.addRunnableListener(this::updateScore);
        SessionManager.getInstance();
    }

    public static ScoreManager getInstance() {
        if(instance == null) instance = new ScoreManager();
        return instance;
    }

    private void updateScore() {
        //newScore = oldScore + 300 / (currentTime - gameStartingTime)
        setScore(getScore() + 300 / (1)); // TODO: replace 1 with gameLooper.getPassedTime();
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }


}

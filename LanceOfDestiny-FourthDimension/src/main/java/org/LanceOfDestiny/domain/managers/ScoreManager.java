package org.LanceOfDestiny.domain.managers;

import org.LanceOfDestiny.domain.EventSystem.Events;

public class ScoreManager {

    private static ScoreManager instance;
    private int score;

    private ScoreManager() {
        this.score = 0;
        Events.UpdateScore.addListener(this::updateScore);
    }

    public static ScoreManager getInstance() {
        if(instance == null) instance = new ScoreManager();
        return instance;
    }

    private void updateScore(Object objectChange) {
        var change = (int)objectChange;
        setScore(getScore() + change);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }


}

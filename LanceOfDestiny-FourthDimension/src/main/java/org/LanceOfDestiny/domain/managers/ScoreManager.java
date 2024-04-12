package org.LanceOfDestiny.domain.managers;

public class ScoreManager {

    private int score;

    public ScoreManager() {
        this.score = 0;
    }

    public void addScore(int score) {
        setScore(getScore() + score);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}

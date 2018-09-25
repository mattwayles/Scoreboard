package com.advancedsportstechnologies.config.model;

public class Match {
    public static final int[][] CORNHOLE_DEFAULTS = new int[][] {{21, 21, 15}, {21, 21, 21}};
    public static final int[][] TRAMPOLINE_VOLLEYBALL_DEFAULTS = new int[][] {{25, 25, 15}, {25, 25, 25}};

    private String type;
    private int currentGame;
    private int[] gameScores;

    public Match(String type) {
        this.type = type;
        this.currentGame = 0;
    }

    public String getType() { return this.type; }

    public int getCurrentGame() { return currentGame; }

    public void setCurrentGame(int game) { this.currentGame = game; }

    public int[] getGameScores() { return this.gameScores; }

    public void setGameScores(int[] scores) {
        this.gameScores = scores;
    }
}

package com.advancedsportstechnologies.config.model;

public class Match {
    public static final int[][] CORNHOLE_DEFAULTS = new int[][] {{21, 21, 15}, {21, 21, 21}};
    public static final int[][] TRAMPOLINE_VOLLEYBALL_DEFAULTS = new int[][] {{25, 25, 15}, {25, 25, 25}};
    public static final int[] BASKETBALL_DEFAULTS = new int[] {3, 6, 8, 10, 12, 15, 20};

    private String type;
    private int currentGame;
    private int currentPeriod;
    private int[] gameScores;
    private int periods;
    private int periodLen;

    public Match(String type) {
        this.type = type;
        this.currentGame = 0;
        this.currentPeriod = 1;
    }

    public String getType() { return this.type; }

    public int getCurrentGame() { return currentGame; }

    public void setCurrentGame(int game) { this.currentGame = game; }

    public int getCurrentPeriod() { return currentPeriod; }

    public void setCurrentPeriod(int period) { this.currentPeriod = period; }

    public int[] getGameScores() { return this.gameScores; }

    public void setGameScores(int[] scores) {
        this.gameScores = scores;
    }

    public int getNumPeriods() { return this.periods; }

    public void setNumPeriods(int periods) { this.periods = periods; }

    public int getPeriodMins() { return this.periodLen; }

    public int getPeriodSeconds() { return this.periodLen * 60; }

    public void setPeriodLen(int len) { this.periodLen = len; }
}

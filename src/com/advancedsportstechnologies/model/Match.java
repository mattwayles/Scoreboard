package com.advancedsportstechnologies.model;

public class Match {
    private int maxGames = 3;
    private int currentGame = 0;
    private Team team1;
    private Team team2;
    private int[] gameScores = new int[] {21, 21, 15};

    protected Match() {
        this.team1 = new Team("Team 1");
        this.team2 = new Team("Team 2");
    }

    public int getGames() { return this.maxGames; }

    public void setGames(int games) { this.maxGames = games; }

    protected int getCurrentGame() { return this.currentGame; }

    public void nextGame() { this.currentGame = this.currentGame + 1; }

    protected Team getTeamOne() { return this.team1; }

    protected Team getTeamTwo() { return this.team2; }

    public int[] getGameScore() { return this.gameScores; }

    protected int getCurrentGameScore() { return this.gameScores[this.currentGame]; }
}

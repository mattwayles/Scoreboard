package com.advancedsportstechnologies.model;

public class Team {
    private String teamName;
    private int score;
    private int gamesWon;

    public Team(String name) {
        this.teamName = name;
        this.score = 0;
        this.gamesWon = 0;
    }

    public String getTeamName() { return this.teamName; }

    public void setTeamName(String name) { this.teamName = name; }

    public int getScore() { return this.score; }

    public void setScore(int score) { this.score = score; }

    public int getGamesWon() { return this.gamesWon; }

    public void setGamesWon(int won) { this.gamesWon = won; }

    public void increaseGamesWon() { this.gamesWon++; }

    public void increaseScore() { this.score++; }

    public void decreaseScore() { this.score--; }
}

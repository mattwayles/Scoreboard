package com.advancedsportstechnologies.model;

public class Team {
    private String teamName;
    private int score;

    Team(String name) {
        this.teamName = name;
        this.score = 0;
    }

    public String getTeamName() { return this.teamName; }

    public void setTeamName(String name) { this.teamName = name; }

    public int getScore() { return this.score; }

    public void increaseScore() { this.score = this.score + 1; }

    public void decreaseScore() { this.score = this.score - 1; }
}

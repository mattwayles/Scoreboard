package main.java.com.socialsportstechnology.games.cornhole.model;

public class Team {
    private String teamName;
    private int score = 0;
    private int gamesWon = 0;

    protected Team() {
        this.teamName = "";
        this.score = 0;
    }

    public Team(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamName() { return teamName; }

    public int getScore() { return score; }

    public void setScore(int score) {
        this.score = score;
    }

    public int getGamesWon() { return gamesWon; }

    public void setGamesWon(int gamesWon) { this.gamesWon = gamesWon; }
}

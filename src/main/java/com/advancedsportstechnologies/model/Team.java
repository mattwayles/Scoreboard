package com.advancedsportstechnologies.model;

import javafx.scene.paint.Paint;

/**
 * A Scoreboard Team. Currently, there are two Teams per match, and they contain a name, color, score, an # of games won
 *
 * The information in a Team is represented on the Scoreboard through a UntimedTeamView
 */
public class Team {
    private Paint color;
    private String teamName;
    private int score = 0;
    private int gamesWon = 0;

    /**
     * Create a new Team with a name and a color
     * @param name  The name of the team
     */
    Team(String name) {
        this.teamName = name;
        this.color = Paint.valueOf("#FFF");
    }

    /**
     * Retrieve the team name
     * @return The team name
     */
    public String getTeamName() { return this.teamName; }

    /**
     * Set a new team name
     * @param name The new team name
     */
    void setTeamName(String name) { this.teamName = name; }
    /**
     * Retrieve the team's score in the current game
     * @return  The team's score in the current game
     */
    public int getScore() { return this.score; }

    /**
     * Set the team's score in the current game
     * @param score The team's score in the current game
     */
    public void setScore(int score) { this.score = score; }

    /**
     * Retrieve the number of games this team has won in the current match
     * @return  The number of games this team has won in the current match
     */
    public int getGamesWon() { return this.gamesWon; }

    /**
     * Set the number of games this team has won in the current match
     * @param won The number of games this team has won in the current match
     */
    public void setGamesWon(int won) { this.gamesWon = won; }

    /**
     * Retrieve the team color
     * @return  The team color
     */
    public Paint getColor() { return this.color; }

    /**
     * Set the team color
     * @param color The team color
     */
    void setColor(Paint color) { this.color = color; }

    /**
     * Increment the number of games this team has won in the current match
     */
    public void increaseGamesWon() { this.gamesWon++; }

    /**
     * Increment the team's score in the current game
     */
    public void increaseScore() { this.score++; }

    /**
     * Decrement the team's score in the current game
     */
    public void decreaseScore() { this.score--; }
}

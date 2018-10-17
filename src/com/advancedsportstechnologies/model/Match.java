package com.advancedsportstechnologies.model;

import com.advancedsportstechnologies.Main;
import com.advancedsportstechnologies.view.GameView;

/**
 * Static Match class contains all of the information for a current match. Games are reset within a match, but a match does not reset until
 * directed by the user
 *
 * Default values exist for each property in case a user does not wish to configure the scoreboard via Bluetooth.
 * Default scoreboard has no concept of 'games'. It simply registers scores from 0-99 and does not display game/match winner screens
 *
 * A Match contains:
 * Two Team objects - These teams have a name, a current score, and a number of games won. Team names sent via Bluetooth.
 * Two team colors - Eventually, this should be sent via Bluetooth and completely customizable.
 * Games To Win - The number of games needed to win a match. Sent via Bluetooth.
 * MaxGames - The number of games in the match. Sent via Bluetooth.
 * Current Game - The current game being played in the match. Must be incremented at the completion of each game.
 * Type - The scoreboard type for this match. Current options are "Standard" (normal scoreboard) or "Switch" (teams switch sides after each game)
 * Connected - Boolean value reflecting the current Bluetooth connection state. Sent via Bluetooth
 * GameScores - An array of win scores for each game in the match. Sent via Bluetooth.
 */
public class Match {
    private static Team team1;
    private static Team team2;
    private static int gamesToWin;
    private static int maxGames = 1;
    private static int currentGame = 0;
    private static String type = "standard";
    private static boolean connected = false;
    private static int[] gameScores = new int[1];

    //TODO: Make the colors customizable via bluetooth
    private static final String TEAM_ONE_COLOR = "#0800ad";
    private static final String TEAM_TWO_COLOR = "#a05500";

    /**
     * Set defaults team names with default team colors
     */
    public static void setTeams() {
        team1 = new Team("Team 1", TEAM_ONE_COLOR);
        team2 = new Team("Team 2", TEAM_TWO_COLOR);
    }

    /**
     * Set teams with names sent via Bluetooth
     * @param team1Name The first team name
     * @param team2Name The second team name
     */
    public static void setTeams(String team1Name, String team2Name) {
        team1 = new Team(team1Name, TEAM_ONE_COLOR);
        team2 = new Team(team2Name, TEAM_TWO_COLOR);
    }

    /**
     * Either start the match if it has not been started yet, or refresh the scoreboard with updated properties
     */
    public static void startOrRefresh() {
        if (Main.getRoot().getChildren() != null) {
            Main.getRoot().getChildren().clear();
            Main.getRoot().getChildren().add(new GameView().getView());
        }
    }

    /**
     * If scoreboard type is 'switch', reverse the teams after each game
     */
    public static void reverseTeams() {
        Team temp = team1;
        team1 = team2;
        team2 = temp;
    }

    /**
     * Retrieve the number of games for this match. Sent via Bluetooth.
     * @return  The number of games for this match.
     */
    public static int getMaxGames() { return maxGames; }

    /**
     * Set the number of games in this match from Bluetooth message
     * @param games The number of games in this Match
     */
    public static void setMaxGames(int games) { maxGames = games; }

    /**
     * Get the current game being played in the match
     * @return The current game being played in the match
     */
    public static int getCurrentGame() { return currentGame; }

    /**
     * Set the current game being played in the match
     * @param game The current game being played in the match
     */
    public static void setCurrentGame(int game) { currentGame = game; }

    /**
     * Increment the match's current game when each game has completed
     */
    public static void nextGame() { currentGame = currentGame + 1; }

    /**
     * Retrieve the Team 1 object
     * @return  The Team 1 object
     */
    public static Team getTeamOne() { return team1; }

    /**
     * Retrieve the Team 2 object
     * @return  The Team 2 object
     */
    public static Team getTeamTwo() { return team2; }

    /**
     * Retrieve the number of games required to win the match. Sent via Bluetooth.
     * @return  The number of games required to win the match.
     */
    public static int getGamesToWin() { return gamesToWin; }

    /**
     * Set the number of games required to win the match from Bluetooth message
     * @param win   The number of games required to win the match
     */
    public static void setGamesToWin(int win) { gamesToWin = win; }

    /**
     * Retrieve the scoreboard type. Sent via Bluetooth
     * @return  The scoreboard type
     */
    public static String getType() { return type; }

    /**
     * Set the scoreboard type for this match from Bluetooth message
     * @param scoreboardType    The scoreboard type for this match
     */
    public static void setType(String scoreboardType) { type = scoreboardType; }

    /**
     * Get the required score to win the current game in this match. Sent via Bluetooth.
     * @return The required score to win the current game in this match.
     */
    public static int getCurrentGameWinScore() { return gameScores[currentGame]; }

    /**
     * Set the required score to win each game in this match from Bluetooth message
     * @param scores    The required scores to win each game in this match.
     */
    public static void setGameScores(int[] scores) { gameScores = scores; }

    /**
     * Determine if Bluetooth connection is currently active
     * @return  Boolean value indicating status of Bluetooth connection
     */
    public static boolean isConnected() { return connected; }

    /**
     * Set Bluetooth connection status
     * @param conn  The Bluetooth connection status
     */
    public static void setConnected(boolean conn) { connected = conn; }
}

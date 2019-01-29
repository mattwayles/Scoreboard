package com.advancedsportstechnologies.model;

import com.advancedsportstechnologies.Main;
import com.advancedsportstechnologies.view.GameView;
import com.advancedsportstechnologies.view.timed.TimedGameView;
import com.advancedsportstechnologies.view.untimed.UntimedGameView;
import javafx.scene.paint.Paint;

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
public abstract class Match {
    private static GameView gameView;
    private static Team team1;
    private static Team team2;
    private static String type = "standard";
    private static String theme = "traditional";
    private static boolean active = true;
    private static boolean connected = false;
    private static String team1Color = "#FFF";
    private static String team2Color = "#FFF";

    /**
     * Set defaults team names with default team colors
     */
    public static void setTeams() {
        setTeamColors();
        team1 = new Team("HOME", team1Color);
        team2 = new Team("AWAY", team2Color);
    }

    /**
     * Set teams with names sent via Bluetooth
     * @param team1Name The first team name
     * @param team2Name The second team name
     */
    public static void setTeams(String team1Name, String team2Name) {
        setTeamColors();
        team1.setTeamName(team1Name);
        team2.setTeamName(team2Name);
    }

    /**
     * Set team colors based off of theme
     */
    private static void setTeamColors() {
        switch (theme) {
            case "dark":
                team1Color = "#FDFFBC";
                team2Color = "#F9CCFF";
                break;
            case "retro":
                team1Color = "#0800ad";
                team2Color = "#a05500";
                break;
            case "traditional":
                team1Color = "#FFF";
                team2Color = "#FFF";
                break;
            case "glow":
                team1Color = "#FFCC00";
                team2Color = "#ff78bb";
                break;
            case "skyzone":
                team1Color = "#002B6C";
                team2Color = "#000";
        }
    }

    /**
     * Either start the match if it has not been started yet, or refresh the scoreboard with updated properties
     */
    public static void start() {
        if (Main.getRoot().getChildren() != null) {
            if (!Match.getType().contains("timed")) {
                gameView = new UntimedGameView();
                Main.getRoot().getChildren().add(gameView.getView());
            }
            else {
                gameView = new TimedGameView();
                Main.getRoot().getChildren().add(gameView.getView());
            }
        }
    }

    public static GameView getGameView() {
        return gameView;
    }

    public static void update() {
        team1.setColor(Paint.valueOf(team1Color));
        team2.setColor(Paint.valueOf(team2Color));
        gameView.update();
    }

    public static void flash() {
        //Scale.play(Main.getRoot().getChildren().get(0), 500, -1, 0);
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
     * Retrieve the Team 1 object
     * @return  The Team 1 object
     */
    public static Team getTeamOne() {
        return team1; }

    /**
     * Retrieve the Team 2 object
     * @return  The Team 2 object
     */
    public static Team getTeamTwo() { return team2; }

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
     * Retrieve the scoreboard theme. Sent via Bluetooth
     * @return The scoreboard theme
     */
    public static String getTheme() { return theme; }

    /**
     * Set the scoreboard theme from Bluetooth message
     * @param scoreboardTheme   The theme for this scoreboard
     */
    public static void setTheme(String scoreboardTheme) {
        theme = scoreboardTheme;
        Main.getScene().getStylesheets().remove(Main.getScene().getStylesheets().size() - 1);
        Main.getScene().getStylesheets().add("css/theme/" + scoreboardTheme + ".css");
    }

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

    /**
     * Determine if Match is currently active. If not, we are on the MatchWinner/MatchTie screen
     * @return  Boolean value indicating status of match
     */
    public static boolean isActive() { return active; }

    /**
     * Set match active status
     * @param current  The match activity status
     */
    public static void setActive(boolean current) { active = current; }
}

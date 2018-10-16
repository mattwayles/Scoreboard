package com.advancedsportstechnologies.model;

import com.advancedsportstechnologies.Main;
import com.advancedsportstechnologies.view.GameView;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;

public class Match {
    private static int maxGames = 1;
    private static int currentGame = 0;
    private static Team team1;
    private static Team team2;
    private static String type = "standard";
    private static int gamesToWin;
    private static int[] gameScores = new int[1];
    private static boolean connected = false;

    private static final String TEAM_ONE_COLOR = "#0800ad";
    private static final String TEAM_TWO_COLOR = "#a05500";

    public static void setTeams() {
        team1 = new Team("Team 1");
        team2 = new Team("Team 2");

        team1.setColor(Paint.valueOf(TEAM_ONE_COLOR));
        team2.setColor(Paint.valueOf(TEAM_TWO_COLOR));
    }

    public static void setTeams(String team1Name, String team2Name) {
        team1 = new Team(team1Name);
        team2 = new Team(team2Name);

        team1.setColor(Paint.valueOf(TEAM_ONE_COLOR));
        team2.setColor(Paint.valueOf(TEAM_TWO_COLOR));
    }

    public static void startOrRefresh() {
        if (Main.getRoot().getChildren() != null) {
            Main.getRoot().getChildren().clear();
            Main.getRoot().getChildren().add(new GameView().getView());
        }
    }

    public static void reverseTeams() {
        Team temp = team1;
        team1 = team2;
        team2 = temp;
    }

    public static int getMaxGames() { return maxGames; }

    public static void setMaxGames(int games) { maxGames = games; }

    public static int getCurrentGame() { return currentGame; }

    public static void setCurrentGame(int game) { currentGame = game; }

    public static void nextGame() { currentGame = currentGame + 1; }

    public static Team getTeamOne() { return team1; }

    public static Team getTeamTwo() { return team2; }

    public static int getGamesToWin() { return gamesToWin; }

    public static void setGamesToWin(int win) { gamesToWin = win; }

    public static int[] getGameScore() { return gameScores; }

    public static String getType() { return type; }

    public static void setType(String scoreboardType) { type = scoreboardType; }

    public static int getCurrentGameScore() { return gameScores[currentGame]; }

    public static void setGameScores(int[] scores) { gameScores = scores; }

    public static boolean isConnected() { return connected; }

    public static void setConnected(boolean conn) { connected = conn; }
}

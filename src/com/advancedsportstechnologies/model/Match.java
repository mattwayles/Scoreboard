package com.advancedsportstechnologies.model;

import com.advancedsportstechnologies.Main;
import com.advancedsportstechnologies.view.GameView;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;

public class Match {
    private static int maxGames = 3;
    private static int currentGame = 0;
    private static Team team1;
    private static Team team2;
    private static int[] gameScores = new int[] {21, 21, 15};

    public static void setTeams() {
        team1 = new Team("Team 1");
        team2 = new Team("Team 2");
    }

    public static void setTeams(String team1Name, String team2Name) {
        team1 = new Team(team1Name);
        team2 = new Team(team2Name);
    }

    public static void start() {
        if (Main.getRoot().getChildren() != null) {
            Main.getRoot().getChildren().clear();
            Main.getRoot().getChildren().add(new GameView().getView());
        }
    }

    public static int getMaxGames() { return maxGames; }

    public static void setMaxGames(int games) { maxGames = games; }

    public static int getCurrentGame() { return currentGame; }

    public static void setCurrentGame(int game) { currentGame = game; }

    public static void nextGame() { currentGame = currentGame + 1; }

    public static Team getTeamOne() { return team1; }

    public static Team getTeamTwo() { return team2; }

    public static int[] getGameScore() { return gameScores; }

    public static int getCurrentGameScore() { return gameScores[currentGame]; }
}

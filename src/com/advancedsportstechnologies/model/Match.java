package com.advancedsportstechnologies.model;

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

    public static void start(StackPane root, Scene scene) {
        if (root.getChildren() != null) {
            root.getChildren().clear();
        }
        root.getChildren().add(new GameView(root, scene).getView());
    }

    public static int getGames() { return maxGames; }

    public static void setGames(int games) { maxGames = games; }

    public static int getCurrentGame() { return currentGame; }

    public static void nextGame() { currentGame = currentGame + 1; }

    public static Team getTeamOne() { return team1; }

    public static Team getTeamTwo() { return team2; }

    public static int[] getGameScore() { return gameScores; }

    public static int getCurrentGameScore() { return gameScores[currentGame]; }
}

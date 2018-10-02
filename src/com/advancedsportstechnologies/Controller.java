package com.advancedsportstechnologies;

import com.advancedsportstechnologies.model.Match;
import com.advancedsportstechnologies.model.Team;
import com.advancedsportstechnologies.view.GameView;
import com.advancedsportstechnologies.view.GameWinnerView;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;

public class Controller {
    public static void checkWinner(StackPane root, Scene scene) {
        Team winningTeam = null;
        Team losingTeam = null;
        if (Match.getTeamOne().getScore() == Match.getCurrentGameScore()) {
            winningTeam = Match.getTeamOne();
            losingTeam = Match.getTeamTwo();
        } else if (Match.getTeamTwo().getScore() == Match.getCurrentGameScore()) {
            winningTeam = Match.getTeamTwo();
            losingTeam = Match.getTeamOne();
        }

        if (winningTeam != null) {
            root.getChildren().clear();
            GameWinnerView gameWinnerView = new GameWinnerView(winningTeam, losingTeam);
            root.getChildren().clear();
            root.getChildren().add(gameWinnerView.getView());
            Thread thread = displayGameWinnerView(root, scene, gameWinnerView);
            thread.start();
        }
    }

    private static Thread displayGameWinnerView(StackPane root, Scene scene, GameWinnerView winnerView) {
        return new Thread(() -> {
            int i = 2;
            while (i >= 0) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                Platform.runLater(() -> {
                    winnerView.decrementSeconds();
                    root.getChildren().set(0, winnerView.getView());
                });
                i--;
            }
            Platform.runLater(() -> {
                Match.getTeamOne().setScore(0);
                Match.getTeamTwo().setScore(0);
                Match.nextGame();
                root.getChildren().set(0, new GameView(root, scene).getView());
            });
        });
    }

}

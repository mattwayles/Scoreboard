package com.advancedsportstechnologies;

import com.advancedsportstechnologies.model.Team;
import com.advancedsportstechnologies.view.GameWinnerView;
import javafx.application.Platform;
import javafx.scene.layout.StackPane;

public class Controller {
    public static void checkWinner(StackPane root, Team winningTeam, Team losingTeam, int gameNum, int winScore) {
        if (winningTeam.getScore() == winScore) {
            root.getChildren().clear();
            GameWinnerView gameWinnerView = new GameWinnerView(winningTeam, losingTeam, gameNum);
            Thread thread = displayGameWinnerView(root, gameWinnerView);

        }
    }

    private static Thread displayGameWinnerView(StackPane root, GameWinnerView winnerView) {
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
                    root.getChildren().add(winnerView.getView());
                });
                i--;
            }
            Platform.runLater(() -> {
                root.getChildren().clear();
                root.getChildren().add()
            });
        });
    }

}

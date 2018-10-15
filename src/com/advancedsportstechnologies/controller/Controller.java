package com.advancedsportstechnologies.controller;

import com.advancedsportstechnologies.Main;
import com.advancedsportstechnologies.model.Match;
import com.advancedsportstechnologies.model.Team;
import com.advancedsportstechnologies.view.GameView;
import com.advancedsportstechnologies.view.GameWinnerView;
import com.advancedsportstechnologies.view.MatchWinnerView;
import com.advancedsportstechnologies.view.TeamView;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class Controller {
    public static boolean checkWinner(TeamView winningTeam, TeamView losingTeam) {
        if (winningTeam.getTeam().getScore() == Match.getCurrentGameScore()) {
            Main.getScene().setOnKeyReleased(null);
            winningTeam.getTeam().increaseGamesWon();
            if (winningTeam.getTeam().getGamesWon() == Match.getMaxGames() - 1) {
                MatchWinnerView winnerView = new MatchWinnerView(winningTeam.getTeam());
                Main.getRoot().getChildren().set(0, winnerView.getView());
            }
            else {
                GameWinnerView gameWinnerView = new GameWinnerView(winningTeam.getTeam(), losingTeam.getTeam());
                Main.getRoot().getChildren().set(0, gameWinnerView.getView());
                Thread thread = displayGameWinnerView(gameWinnerView);
                thread.start();
            }
            return true;
        }
        return false;
    }

    private static Thread displayGameWinnerView(GameWinnerView winnerView) {
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
                    Main.getRoot().getChildren().set(0, winnerView.getView());
                });
                i--;
            }
            Platform.runLater(() -> {
                resetGame();

                GameView newGameView = new GameView();
                if(Match.getType().equals("switch")) {
                    newGameView.reverseTeams();
                }

                Main.getRoot().getChildren().set(0, newGameView.getView());
            });
        });
    }

    private static void resetGame() {
        Match.getTeamOne().setScore(0);
        Match.getTeamTwo().setScore(0);
        Match.nextGame();
    }

    public static void restartScoreboard() {
        Match.getTeamOne().setScore(0);
        Match.getTeamOne().setGamesWon(0);
        Match.getTeamTwo().setScore(0);
        Match.getTeamTwo().setGamesWon(0);
        Match.setCurrentGame(0);
    }

}

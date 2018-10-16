package com.advancedsportstechnologies.controller;

import com.advancedsportstechnologies.Main;
import com.advancedsportstechnologies.model.Match;
import com.advancedsportstechnologies.view.*;
import javafx.application.Platform;

public class Controller {
    public static void checkWinner(TeamView winningTeam, TeamView losingTeam) {
        if (winningTeam.getTeam().getScore() == Match.getCurrentGameScore()) {
            Main.getScene().setOnKeyReleased(null);
            winningTeam.getTeam().increaseGamesWon();
            if (winningTeam.getTeam().getGamesWon() >= Match.getGamesToWin()) {
                MatchWinnerView winnerView = new MatchWinnerView(winningTeam.getTeam());
                Main.getRoot().getChildren().set(0, winnerView.getView());
            }
            else if (Match.getCurrentGame() + 1 == Match.getMaxGames()) {
                MatchTieView tieView = new MatchTieView();
                Main.getRoot().getChildren().set(0, tieView.getView());
            }
            else {
                GameWinnerView gameWinnerView = new GameWinnerView(winningTeam.getTeam(), losingTeam.getTeam());
                Main.getRoot().getChildren().set(0, gameWinnerView.getView());
                Thread thread = displayGameWinnerView(gameWinnerView);
                thread.start();
            }
        }
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

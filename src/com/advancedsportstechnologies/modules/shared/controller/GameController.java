package com.advancedsportstechnologies.modules.shared.controller;

import com.advancedsportstechnologies.config.model.Match;
import com.advancedsportstechnologies.config.view.GameWinnerView;
import com.advancedsportstechnologies.config.view.MainView;
import com.advancedsportstechnologies.config.view.MatchWinnerView;
import com.advancedsportstechnologies.modules.games.cornhole.view.CornholeMatchView;
import com.advancedsportstechnologies.modules.games.trampolinevolleyball.view.VolleyballMatchView;
import com.advancedsportstechnologies.modules.shared.view.TeamView;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Paint;

public class GameController {
    public static boolean changeScore(KeyEvent e, TeamView team1, TeamView team2, int winScore, Match match, MainView view, MainView matchView) {
        boolean winner = false;

        if (e.getCode() == KeyCode.A) {
            team1.setScore(team1.getScore() + 1);
            team1.setScoreLabel(team1.getScore());
        } else if (e.getCode() == KeyCode.S) {
            team2.setScore(team2.getScore() + 1);
            team2.setScoreLabel(team2.getScore());
        } else if (e.getCode() == KeyCode.Z && team1.getScore() > 0) {
            team1.setScore(team1.getScore() - 1);
            team1.setScoreLabel(team1.getScore());
        } else if (e.getCode() == KeyCode.X && team2.getScore() > 0) {
            team2.setScore(team2.getScore() - 1);
            team2.setScoreLabel(team2.getScore());
        }

        if (team1.getScore() == winScore || team2.getScore() == winScore) {
            if (team1.getScore() == winScore) {
                winner(team1, team2, view, matchView, Paint.valueOf("#0800ad"), match);
            }
            else if (team2.getScore() == winScore) {
                winner(team2, team1, view, matchView, Paint.valueOf("#a05500"), match);
            }
            winner = true;
        }

        return winner;
    }

    private static void winner(TeamView winningTeam, TeamView losingTeam, MainView view, MainView matchView, Paint color, Match match) {
        winningTeam.setGamesWon(winningTeam.getGamesWon() + 1);
        match.setCurrentGame(match.getCurrentGame() + 1);
        if (winningTeam.getGamesWon() == 1) {
            winningTeam.getGamesWonImgs().getChildren().remove(0);

            GameWinnerView winnerView = new GameWinnerView(3, winningTeam.getScore(), losingTeam.getScore());
            winnerView.displayGameWinner(winningTeam.getTeamName(), match.getCurrentGame(), color);
            view.setCurrentControl(winnerView);
            view.updateMainView(winnerView.getView());

            Thread thread = new Thread(() -> {
                int i = 2;
                while (i >= 0) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    Platform.runLater(() -> {
                        winnerView.decrementSeconds();
                        winnerView.displayGameWinner(winningTeam.getTeamName(), match.getCurrentGame(), color);
                        view.setCurrentControl(winnerView);
                        view.updateMainView(winnerView.getView());
                    });
                    i--;
                }
                Platform.runLater(() -> {
                    view.setCurrentControl(matchView);
                    switch (match.getType()) {
                        case "Cornhole":
                            CornholeMatchView cornholeMatchView = (CornholeMatchView) matchView;
                            view.updateMainView(cornholeMatchView.getView());
                            break;
                        case "Trampoline Volleyball":
                            VolleyballMatchView volleyballMatchView = (VolleyballMatchView) matchView;
                            view.updateMainView(volleyballMatchView.getView());
                            break;
                    }
                });

            });
            thread.start();
            winningTeam.getGamesWonImgs().getChildren().add(new ImageView(new Image("/img/gameWon.png")));
        }
        else if (winningTeam.getGamesWon() == 2) {
            MatchWinnerView winnerView = new MatchWinnerView();
            winnerView.displayMatchWinner(winningTeam.getTeamName(), match.getCurrentGame(), color);
            view.setCurrentControl(winnerView);
            view.updateMainView(winnerView.getView());
        }
    }
}

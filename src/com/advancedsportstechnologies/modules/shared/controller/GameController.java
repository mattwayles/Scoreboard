package com.advancedsportstechnologies.modules.shared.controller;

import com.advancedsportstechnologies.KeyboardController;
import com.advancedsportstechnologies.KeyboardController;
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
    private static boolean cancelCountdown;

    public static boolean changeScore(KeyEvent e, TeamView team1, TeamView team2, int winScore, MainView view, MainView matchView) {
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
        } else if (e.getCode() == KeyCode.Q) {
            cancelCountdown = true;
            KeyboardController.openTeamSelect();
        }

        if (team1.getScore() == winScore || team2.getScore() == winScore) {
            if (cancelCountdown && e.getCode() != KeyCode.Q) {
                cancelCountdown = false;
            }
            if (team1.getScore() == winScore) {
                winner(team1, team2, view);
            }
            else if (team2.getScore() == winScore) {
                winner(team2, team1, view);
            }
            winner = true;
        }

        return winner;
    }

    private static void winner(TeamView winningTeam, TeamView losingTeam, MainView view) {
        winningTeam.setGamesWon(winningTeam.getGamesWon() + 1);
        KeyboardController.getMatch().setCurrentGame(KeyboardController.getMatch().getCurrentGame() + 1);
        if (winningTeam.getGamesWon() == 1) {
            winningTeam.getGamesWonImgs().getChildren().remove(0);

            GameWinnerView winnerView = new GameWinnerView(3, winningTeam.getScore(), losingTeam.getScore());
            winnerView.displayGameWinner(winningTeam, KeyboardController.getMatch().getCurrentGame());
            //view.setCurrentControl(winnerView);
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
                                winnerView.displayGameWinner(winningTeam, KeyboardController.getMatch().getCurrentGame());
                                if (!cancelCountdown) {
                                    view.updateMainView(winnerView.getView());
                                }
                            });
                            i--;
                        }
                Platform.runLater(() -> {
                    if (!cancelCountdown) {
                        switch (KeyboardController.getMatch().getType()) {
                            case "Cornhole":
                                CornholeMatchView cornholeMatchView = (CornholeMatchView) view.getCurrentControl();
                                cornholeMatchView.resetScores();
                                view.updateMainView(cornholeMatchView.getView());
                                break;
                            case "Trampoline Volleyball":
                                VolleyballMatchView volleyballMatchView = (VolleyballMatchView) view.getCurrentControl();
                                volleyballMatchView.resetScores();
                                view.updateMainView(volleyballMatchView.getView());
                                break;
                        }
                    }
                });

            });
            thread.start();
            winningTeam.getGamesWonImgs().getChildren().add(new ImageView(new Image("/img/gameWon.png")));
        }
        else if (winningTeam.getGamesWon() == 2) {
            MatchWinnerView winnerView = new MatchWinnerView();
            winnerView.displayMatchWinner(winningTeam, KeyboardController.getMatch().getCurrentGame());
            view.setCurrentControl(winnerView);
            view.updateMainView(winnerView.getView());
        }
    }
}

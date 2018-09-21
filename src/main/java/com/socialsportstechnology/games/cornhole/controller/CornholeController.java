package main.java.com.socialsportstechnology.games.cornhole.controller;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.stage.Screen;
import main.java.com.socialsportstechnology.Controller;
import main.java.com.socialsportstechnology.config.model.Match;
import main.java.com.socialsportstechnology.config.view.GameWinnerView;
import main.java.com.socialsportstechnology.config.view.MainView;
import main.java.com.socialsportstechnology.config.view.MatchWinnerView;
import main.java.com.socialsportstechnology.games.cornhole.view.CornholeMatchView;
import main.java.com.socialsportstechnology.games.cornhole.view.TeamView;

public class CornholeController extends Controller {
    public static void changeScore(KeyEvent e, MainView view, Match match) {
        CornholeMatchView cornholeMatchView = (CornholeMatchView) view.getCurrentControl();
        int winScore = match.getGameScores()[match.getCurrentGame()];
        TeamView team1 = cornholeMatchView.getTeam1();
        TeamView team2 = cornholeMatchView.getTeam2();

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
                winner(team1, team2, view, cornholeMatchView, Paint.valueOf("#0800ad"), match);
            }
            else if (team2.getScore() == winScore) {
                winner(team2, team1, view, cornholeMatchView, Paint.valueOf("#a05500"), match);
            }
            resetGame(team1, team2);
        }
    }

    private static void winner(TeamView winningTeam, TeamView losingTeam, MainView view, CornholeMatchView matchView, Paint color, Match match) {
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
                        view.updateMainView(matchView.getView());
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

    public static void easterEgg(MainView view) {
        MatchWinnerView winnerView = new MatchWinnerView();
        winnerView.setView(new HBox(new ImageView(new Image("/img/easteregg.gif",
                Screen.getPrimary().getVisualBounds().getWidth(),
                Screen.getPrimary().getVisualBounds().getHeight(), false, false))));
        view.setCurrentControl(winnerView);
        view.updateMainView(winnerView.getView());
    }

    private static void resetGame(TeamView team1, TeamView team2) {
        team1.setScore(0);
        team1.setScoreLabel(team1.getScore());
        team2.setScore(0);
        team2.setScoreLabel(team2.getScore());
    }
}

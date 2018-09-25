package com.advancedsportstechnologies.modules.shared.controller.dual;

import com.advancedsportstechnologies.Run;
import com.advancedsportstechnologies.config.controller.Controller;
import com.advancedsportstechnologies.config.controller.PiController;
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

public class GameController {
    private static boolean cancelCountdown;
    private static MainView view = Run.debug ? Controller.getView() : PiController.getView();
    public static Match match = Run.debug ? Controller.getMatch() : PiController.getMatch();

    public static boolean changeScore(KeyEvent e, TeamView team1, TeamView team2) {
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

        if (e.getCode() == KeyCode.Q) {
            if (!Controller.resetButtonHeld()) {
                cancelCountdown = true;
                restartMatch(team1, team2);
                Controller.openTeamSelect();
            }
            else {
                restartMatch(team1, team2);
            }
        } else {
            return checkWinner(team1, team2);
        }
        view.setKeyPressTime(0);
        view.getKeysDown().remove(e.getCode());
        return false;
    }

    static boolean checkWinner(TeamView team1, TeamView team2) {
        TeamView winner = null;
        TeamView loser = null;
        if (team1.getScore() == match.getGameScores()[match.getCurrentGame()]) {
            winner = team1;
            loser = team2;
        }
        else if (team2.getScore() == match.getGameScores()[match.getCurrentGame()]) {
            winner = team2;
            loser = team1;
        }
        if (winner != null) {
            cancelCountdown = false;
            winner.setGamesWon(winner.getGamesWon() + 1);
            match.setCurrentGame(match.getCurrentGame() + 1);
            if (winner.getGamesWon() == 1) {
                gameWin(winner, loser);
            }
            else if (winner.getGamesWon() == 2) {
                matchWin(winner);
            }
            return true;
        }
        return false;
    }

    private static void gameWin(TeamView winner, TeamView loser) {
        winner.getGamesWonImgs().getChildren().remove(0);
        GameWinnerView winnerView = new GameWinnerView(3, winner.getScore(), loser.getScore());
        winnerView.displayGameWinner(winner, match.getCurrentGame());
        view.updateMainView(winnerView.getView());
        resetScores(winner, loser);
        Thread thread = createNewThread(winnerView, winner);
        thread.start();

        winner.getGamesWonImgs().getChildren().add(new ImageView(new Image("/img/gameWon.png")));
    }

    private static void matchWin(TeamView winner) {
        MatchWinnerView winnerView = new MatchWinnerView();
        winnerView.displayMatchWinner(winner, match.getCurrentGame());
        view.setCurrentControl(winnerView);
        view.updateMainView(winnerView.getView());
    }

    private static void restartMatch(TeamView team1, TeamView team2) {
        team1.setGamesWon(0);
        team2.setGamesWon(0);
        match.setCurrentGame(0);
    }

    private static Thread createNewThread(GameWinnerView winnerView, TeamView winner) {
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
                    winnerView.displayGameWinner(winner, match.getCurrentGame());
                    if (!cancelCountdown) {
                        view.updateMainView(winnerView.getView());
                    }
                });
                i--;
            }
            Platform.runLater(() -> {
                if (!cancelCountdown) {
                    switch (match.getType()) {
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
    }

    private static void resetScores(TeamView team1, TeamView team2) {
        team1.setScore(0);
        team2.setScore(0);
        team1.setScoreLabel(team1.getScore());
        team2.setScoreLabel(team2.getScore());
    }
}

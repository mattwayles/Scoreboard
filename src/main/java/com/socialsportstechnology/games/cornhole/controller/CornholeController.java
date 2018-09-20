package main.java.com.socialsportstechnology.games.cornhole.controller;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.stage.Screen;
import main.java.com.socialsportstechnology.Controller;
import main.java.com.socialsportstechnology.config.model.Match;
import main.java.com.socialsportstechnology.config.view.MainView;
import main.java.com.socialsportstechnology.config.view.MatchWinnerView;
import main.java.com.socialsportstechnology.games.cornhole.view.CornholeMatchView;
import main.java.com.socialsportstechnology.games.cornhole.view.TeamView;

public class CornholeController extends Controller {
    public static void changeScore(KeyEvent e, MainView view, Match match) {
        CornholeMatchView cornholeMatchView = (CornholeMatchView) view.getCurrentControl();
        int winScore = match.getGameScores()[cornholeMatchView.getCurrentGame()];
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
                winner(team1, view, Paint.valueOf("#0800ad"), match);
            }
            else if (team2.getScore() == winScore) {
                winner(team2, view, Paint.valueOf("#a05500"), match);
            }
            resetGame(team1, team2);
        }
    }

    private static void winner(TeamView team, MainView view, Paint color, Match match) {
            team.setGamesWon(team.getGamesWon() + 1);
            match.setCurrentGame(match.getCurrentGame() + 1);
            if (team.getGamesWon() == 1) {
                team.getGamesWonImgs().getChildren().remove(0);
            }
            else if (team.getGamesWon() == 2) {
                MatchWinnerView winnerView = new MatchWinnerView();
                winnerView.displayMatchWinner(team.getTeamName(), match.getCurrentGame(), color);
                view.setCurrentControl(winnerView);
                view.updateMainView(winnerView.getView());
            }
            team.getGamesWonImgs().getChildren().add(new ImageView(new Image("/img/gameWon.png")));
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

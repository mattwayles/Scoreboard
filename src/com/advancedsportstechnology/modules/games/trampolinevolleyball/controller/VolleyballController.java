package com.advancedsportstechnology.modules.games.trampolinevolleyball.controller;

import com.advancedsportstechnology.Controller;
import com.advancedsportstechnology.config.model.Match;
import com.advancedsportstechnology.config.view.GameWinnerView;
import com.advancedsportstechnology.config.view.MainView;
import com.advancedsportstechnology.config.view.MatchWinnerView;
import com.advancedsportstechnology.modules.games.trampolinevolleyball.view.VolleyballMatchView;
import com.advancedsportstechnology.modules.shared.controller.GameController;
import com.advancedsportstechnology.modules.shared.view.TeamView;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.stage.Screen;

public class VolleyballController extends Controller {

    public static void changeScore(KeyEvent e, MainView view, Match match) {
        VolleyballMatchView volleyballMatchView = (VolleyballMatchView) view.getCurrentControl();
        int winScore = match.getGameScores()[match.getCurrentGame()];
        TeamView team1 = volleyballMatchView.getTeam1();
        TeamView team2 = volleyballMatchView.getTeam2();

        if(GameController.changeScore(e, team1, team2, winScore, match, view, volleyballMatchView)) {
            volleyballMatchView.setTeam1(team2);
            volleyballMatchView.setTeam2(team1);
            volleyballMatchView.reverseTeams();

            resetGame(team1, team2);
        }
    }

    private static void resetGame(TeamView team1, TeamView team2) {
        team1.setScore(0);
        team1.setScoreLabel(team1.getScore());
        team2.setScore(0);
        team2.setScoreLabel(team2.getScore());
    }

    public static void easterEgg(MainView view) {
        MatchWinnerView winnerView = new MatchWinnerView();
        winnerView.setView(new HBox(new ImageView(new Image("/img/easteregg.gif",
                Screen.getPrimary().getVisualBounds().getWidth(),
                Screen.getPrimary().getVisualBounds().getHeight(), false, false))));
        view.setCurrentControl(winnerView);
        view.updateMainView(winnerView.getView());
    }
}

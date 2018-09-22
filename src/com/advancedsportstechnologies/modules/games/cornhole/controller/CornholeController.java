package com.advancedsportstechnologies.modules.games.cornhole.controller;

import com.advancedsportstechnologies.config.model.Match;
import com.advancedsportstechnologies.config.view.MainView;
import com.advancedsportstechnologies.config.view.MatchWinnerView;
import com.advancedsportstechnologies.modules.shared.controller.GameController;
import com.advancedsportstechnologies.modules.shared.view.TeamView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import com.advancedsportstechnologies.Controller;
import com.advancedsportstechnologies.modules.games.cornhole.view.CornholeMatchView;

public class CornholeController extends Controller {
    public static void changeScore(KeyEvent e, MainView view, Match match) {
        CornholeMatchView cornholeMatchView = (CornholeMatchView) view.getCurrentControl();
        int winScore = match.getGameScores()[match.getCurrentGame()];
        TeamView team1 = cornholeMatchView.getTeam1();
        TeamView team2 = cornholeMatchView.getTeam2();

        if(GameController.changeScore(e, team1, team2, winScore, match, view, cornholeMatchView)) {
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

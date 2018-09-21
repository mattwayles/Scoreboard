package com.advancedsportstechnology.modules.games.cornhole.controller;

import com.advancedsportstechnology.config.model.Match;
import com.advancedsportstechnology.config.view.MainView;
import com.advancedsportstechnology.config.view.MatchWinnerView;
import com.advancedsportstechnology.modules.shared.controller.GameController;
import com.advancedsportstechnology.modules.shared.view.TeamView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import com.advancedsportstechnology.Controller;
import com.advancedsportstechnology.modules.games.cornhole.view.CornholeMatchView;

public class CornholeController extends Controller {
    public static void changeScore(KeyEvent e, MainView view, Match match) {
        CornholeMatchView cornholeMatchView = (CornholeMatchView) view.getCurrentControl();
        int winScore = match.getGameScores()[match.getCurrentGame()];
        TeamView team1 = cornholeMatchView.getTeam1();
        TeamView team2 = cornholeMatchView.getTeam2();

        GameController.changeScore(e, team1, team2, winScore, match, view, cornholeMatchView);
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

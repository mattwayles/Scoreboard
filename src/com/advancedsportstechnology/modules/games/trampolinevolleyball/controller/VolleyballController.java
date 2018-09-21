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

        GameController.changeScore(e, team1, team2, winScore, match, view, volleyballMatchView);

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

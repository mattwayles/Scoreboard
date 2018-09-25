package com.advancedsportstechnologies.modules.games.cornhole.controller;

import com.advancedsportstechnologies.KeyboardController;
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
import com.advancedsportstechnologies.modules.games.cornhole.view.CornholeMatchView;

public class CornholeController {
    public static void changeScore(KeyEvent e, MainView view) {
        CornholeMatchView cornholeMatchView = (CornholeMatchView) view.getCurrentControl();
        int winScore = KeyboardController.getMatch().getGameScores()[KeyboardController.getMatch().getCurrentGame()];
        TeamView team1 = cornholeMatchView.getTeam1();
        TeamView team2 = cornholeMatchView.getTeam2();
        GameController.changeScore(e, team1, team2, winScore, view, cornholeMatchView);
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

package com.advancedsportstechnologies.modules.games.trampolinevolleyball.controller;

import com.advancedsportstechnologies.PiController;
import com.advancedsportstechnologies.config.model.Match;
import com.advancedsportstechnologies.config.view.MainView;
import com.advancedsportstechnologies.config.view.MatchWinnerView;
import com.advancedsportstechnologies.modules.games.trampolinevolleyball.view.VolleyballMatchView;
import com.advancedsportstechnologies.modules.shared.controller.GameController;
import com.advancedsportstechnologies.modules.shared.view.TeamView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;

public class VolleyballController {

    public static void changeScore(KeyEvent e, MainView view) {
        VolleyballMatchView volleyballMatchView = (VolleyballMatchView) view.getCurrentControl();
        int winScore = PiController.getMatch().getGameScores()[PiController.getMatch().getCurrentGame()];
        TeamView team1 = volleyballMatchView.getTeam1();
        TeamView team2 = volleyballMatchView.getTeam2();

        if(GameController.changeScore(e, team1, team2, winScore, view, volleyballMatchView)) {
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

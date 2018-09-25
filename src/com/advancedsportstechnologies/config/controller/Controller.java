package com.advancedsportstechnologies.config.controller;

import com.advancedsportstechnologies.Run;
import com.advancedsportstechnologies.config.model.Match;
import com.advancedsportstechnologies.config.view.*;
import com.advancedsportstechnologies.modules.games.cornhole.view.CornholeMatchView;
import com.advancedsportstechnologies.modules.games.trampolinevolleyball.view.VolleyballMatchView;
import com.advancedsportstechnologies.modules.shared.controller.dual.GameController;
import com.advancedsportstechnologies.modules.shared.view.TeamView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;

public class Controller extends Run {
    private static MainView view;
    private static Match match;

    public static Match getMatch() { return match; }
    public static MainView getView() { return view; }
    public void setView(MainView mainView) { view = mainView; }

    static void openGameSelectView() {
        if (match != null) {
            restoreState();
        }
        GameSelectView gameSelectView = new GameSelectView();
        view.setCurrentControl(gameSelectView);
        view.updateConfigView(gameSelectView.getGameSelectView());
    }

    static void openGameFormatSelectView(String matchType) {
        System.out.println("STARTING");
        GameFormatSelectView gameFormatSelectView = new GameFormatSelectView(matchType);
        System.out.println("CREATED GAMEFORMATSELECTVIEW");
        view.setCurrentControl(gameFormatSelectView);
        System.out.println("SET CONTROL");
        view.updateConfigView(gameFormatSelectView.getGameFormatSelectView());
        System.out.println("UPDATED VIEW");
        match = new Match(matchType);
        System.out.println("CREATED NEW MATCH");
        GameController.match = match;
        System.out.println("STARTING");
        PiController.match = match;
    }

    public static void openTeamSelect() {
        openTeamSelect(match.getGameScores());
    }

    public static void openTeamSelect(int[] scores) {
        restoreState();
        match.setGameScores(scores);
        TeamSelectView teamSelectView = new TeamSelectView(match.getType());
        view.setCurrentControl(teamSelectView);
        view.updateConfigView(teamSelectView.getTeamSelectView());
    }

    public static void startMatch(TeamSelectView teamSelect) {
        switch (match.getType()) {
            case "Cornhole":
                CornholeMatchView cornholeMatchView = new CornholeMatchView(teamSelect.getTeam1Select().getValue().toString(),
                        teamSelect.getTeam2Select().getValue().toString());
                view.setCurrentControl(cornholeMatchView);
                view.updateMainView(cornholeMatchView.getView());
                break;
            case "Trampoline Volleyball":
                VolleyballMatchView volleyballMatchView = new VolleyballMatchView(teamSelect.getTeam1Select().getValue().toString(),
                        teamSelect.getTeam2Select().getValue().toString());
                view.setCurrentControl(volleyballMatchView);
                view.updateMainView(volleyballMatchView.getView());
                break;
        }
    }

    private static void restoreState() {
        match.setCurrentGame(0);
        TeamView.resetCount();
    }

    public static void easterEgg(String location) {
        MatchWinnerView winnerView = new MatchWinnerView();
        winnerView.setView(new HBox(new ImageView(new Image(location,
                Screen.getPrimary().getVisualBounds().getWidth(),
                Screen.getPrimary().getVisualBounds().getHeight(), false, false))));
        view.setCurrentControl(winnerView);
        view.updateMainView(winnerView.getView());
    }

    public static void keyPress() {
        Run.getScene().setOnKeyPressed(e -> {
            MainView view = Controller.getView();
            if (view.getKeyPressTime() < 1) {
                view.setKeyPressTime(System.currentTimeMillis());
            } else if (!view.getKeysDown().contains(e.getCode()) && e.getCode() != view.getLastKeyPressed()) {
                view.setLastKeyPressed(e.getCode());
                view.setKeyPressTime(System.currentTimeMillis());
                view.getKeysDown().add(e.getCode());
            }
        });
    }

    private static int count = 0;

    public static boolean resetButtonHeld() {
        count++;
        if (System.currentTimeMillis() - view.getKeyPressTime() >= 3000L) {
            if (Run.debug) {
                Controller.openGameSelectView();
            }
            else {
                PiController.openGameSelectView();
            }
            view.setKeyPressTime(0);
            return true;
        }
        return false;
    }
}

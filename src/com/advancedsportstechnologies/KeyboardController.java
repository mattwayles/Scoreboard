package com.advancedsportstechnologies;

import com.advancedsportstechnologies.config.controller.ConfigController;
import com.advancedsportstechnologies.config.model.Match;
import com.advancedsportstechnologies.config.view.GameFormatSelectView;
import com.advancedsportstechnologies.config.view.MainView;
import com.advancedsportstechnologies.config.view.TeamSelectView;
import com.advancedsportstechnologies.modules.games.cornhole.controller.CornholeController;
import com.advancedsportstechnologies.modules.games.cornhole.view.CornholeMatchView;
import com.advancedsportstechnologies.modules.games.trampolinevolleyball.controller.VolleyballController;
import com.advancedsportstechnologies.modules.games.trampolinevolleyball.view.VolleyballMatchView;
import com.advancedsportstechnologies.modules.shared.view.TeamView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.util.HashSet;
import java.util.Set;

public class KeyboardController extends Run {
    private static MainView view;
    private static Match match;

    public static Match getMatch() { return match; }
    public void setView(MainView mainView) { view = mainView; }

    private static long keyPressTime;
    private static KeyCode lastKeyPressed;
    private static String lastId;
    private static Set<KeyCode> keysDown = new HashSet<>();

    void routeKeyPress(KeyEvent e, MainView view) {
        String id = view.getCurrentControl().getId();
        lastId = id;

        keysDown.add(e.getCode());
        if (!keysDown.contains(e.getCode()) && e.getCode() != lastKeyPressed) {
            lastKeyPressed = e.getCode();
            keyPressTime = System.currentTimeMillis();
        }
        switch (id) {
            case "gameSelect":
                ConfigController.gameSelection(e, view);
                break;
            case "gameFormat":
                ConfigController.formatSelection(e, view);
                break;
            case "teamSelect":
                ConfigController.teamSelection(e, view);
                break;
            case "cornhole":
                CornholeController.changeScore(e, view);
                break;
            case "volleyball":
                VolleyballController.changeScore(e, view);
        }
    }

    void releaseKey(KeyEvent e, MainView view) {
        if (keysDown.contains(KeyCode.A) && keysDown.contains(KeyCode.Z)) {
            if (System.currentTimeMillis() - keyPressTime >= 3000L) {
                if (view.getCurrentControl().getId().equals("matchWinner")) {
                    CornholeController.easterEgg(view);
                }
            }
        } else if (keysDown.contains(KeyCode.Q) || keysDown.contains(KeyCode.W)) {
            String id = view.getCurrentControl().getId();
            if (!id.equals("gameSelect") && !id.equals("gameFormat") && !id.equals("teamSelect") && !lastId.equals("teamSelect")) {
                restoreState();
                if (System.currentTimeMillis() - keyPressTime >= 3000L) {
                    close();
                    start(new Stage());
                }
                else {
                    ConfigController.restartMatch(match, view);
                }
            }
        }
        keyPressTime = System.currentTimeMillis();
        keysDown.remove(e.getCode());
    }

    private static void restoreState() {
        match.setCurrentGame(0);
        TeamView.resetCount();
    }

    public static void openGameFormatSelectView(String matchType) {
        GameFormatSelectView gameFormatSelectView = new GameFormatSelectView(matchType);
        view.setCurrentControl(gameFormatSelectView);
        view.updateConfigView(gameFormatSelectView.getGameFormatSelectView());

        //TODO: Create match with the correct scores based off selections!
        match = new Match(matchType);
    }

    public static void openTeamSelect() {
        openTeamSelect(match.getGameScores());
    }

    public static void openTeamSelect(int[] scores) {
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
}

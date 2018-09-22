package com.advancedsportstechnologies.config.controller;

import com.advancedsportstechnologies.PiController;
import com.advancedsportstechnologies.config.view.GameScoreSelectView;
import com.advancedsportstechnologies.config.view.GameSelectView;
import com.advancedsportstechnologies.config.view.MainView;
import com.advancedsportstechnologies.config.view.TeamSelectView;
import com.advancedsportstechnologies.modules.games.trampolinevolleyball.view.VolleyballMatchView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import com.advancedsportstechnologies.Controller;
import com.advancedsportstechnologies.config.model.Match;
import com.advancedsportstechnologies.modules.games.cornhole.view.CornholeMatchView;

public class ConfigController extends Controller {
    private static final int[] CORNHOLE_DEFAULTS = new int[] {21, 21, 15};
    private static final int[] TRAMPOLINE_VOLLEYBALL_DEFAULTS = new int[] {25, 25, 15};
    private static String matchType;


    private static int gameNum;

    public static void setGameNum(int i) { gameNum = i; }


    public static void gameSelection(KeyEvent e, MainView view) {
        GameSelectView gameSelectView = (GameSelectView) view.getCurrentControl();

        if (e.getCode() == KeyCode.A || e.getCode() == KeyCode.S) {
            gameSelectView.getSelectionBox().getSelectionModel().selectPrevious();
        }
        else if (e.getCode() == KeyCode.Z || e.getCode() == KeyCode.X) {
            gameSelectView.getSelectionBox().getSelectionModel().selectNext();
        }
        else if (e.getCode() == KeyCode.Q || e.getCode() == KeyCode.W) {
            String matchType = gameSelectView.getSelectionBox().getSelectionModel().getSelectedItem().toString();
            PiController.openGameScore(matchType);
        }
    }

    public static void scoreSelection(KeyEvent e, MainView view) {
        GameScoreSelectView gameScoreSelectView = (GameScoreSelectView) view.getCurrentControl();
        int scoreValue = Integer.parseInt(gameScoreSelectView.getSelectionField().textProperty().getValue());

        if (e.getCode() == KeyCode.A || e.getCode() == KeyCode.S) {
            gameScoreSelectView.getSelectionField().textProperty().setValue(String.valueOf(scoreValue + 1));
        }
        else if (e.getCode() == KeyCode.Z || e.getCode() == KeyCode.X) {
            gameScoreSelectView.getSelectionField().textProperty().setValue(String.valueOf(scoreValue - 1));
        }
        else if (e.getCode() == KeyCode.Q || e.getCode() == KeyCode.W) {
            PiController.openTeamSelect(gameScoreSelectView);
        }
    }


    public static void teamSelection(KeyEvent e, MainView view) {
        TeamSelectView teamSelectView = (TeamSelectView) view.getCurrentControl();


        if (e.getCode() == KeyCode.A) {
            teamSelectView.getTeam1Select().getSelectionModel().selectPrevious();
        }
        else if (e.getCode() == KeyCode.Z) {
            teamSelectView.getTeam1Select().getSelectionModel().selectNext();
        }
        else if (e.getCode() == KeyCode.S) {
            teamSelectView.getTeam2Select().getSelectionModel().selectPrevious();
        }
        else if (e.getCode() == KeyCode.X) {
            teamSelectView.getTeam2Select().getSelectionModel().selectNext();
        }
        else if (e.getCode() == KeyCode.Q || e.getCode() == KeyCode.W) {
            startMatch(view, match.getType(), teamSelectView);
        }
    }

    public static void restartMatch(MainView view) {
        TeamSelectView teamSelectView = new TeamSelectView(matchType);
        view.setCurrentControl(teamSelectView);
        view.updateConfigView(teamSelectView.getTeamSelectView());
    }

    private static void openGameScoreSelect(MainView view) {
        GameScoreSelectView gameScoreSelectView = new GameScoreSelectView(gameNum, match.getGameScores()[gameNum]);
        view.setCurrentControl(gameScoreSelectView);
        view.updateConfigView(gameScoreSelectView.getGameScoreSelectView());
        gameNum++;
    }

    private static void startMatch(MainView view, String matchType, TeamSelectView teamSelect) {
        switch (matchType) {
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

    private static int[] getGameScoreDefaults(String matchType) {
        switch (matchType) {
            case "Cornhole":
                return CORNHOLE_DEFAULTS;
            case "Trampoline Volleyball":
                return TRAMPOLINE_VOLLEYBALL_DEFAULTS;
            default:
                return new int[] {21, 21, 15};
        }
    }
}

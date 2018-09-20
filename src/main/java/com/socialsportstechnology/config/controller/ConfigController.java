package main.java.com.socialsportstechnology.config.controller;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import main.java.com.socialsportstechnology.Controller;
import main.java.com.socialsportstechnology.config.model.Match;
import main.java.com.socialsportstechnology.config.view.MainView;
import main.java.com.socialsportstechnology.config.view.GameScoreSelectView;
import main.java.com.socialsportstechnology.config.view.GameSelectView;
import main.java.com.socialsportstechnology.config.view.TeamSelectView;
import main.java.com.socialsportstechnology.games.cornhole.view.CornholeMatchView;

public class ConfigController extends Controller {
    private static final int[] CORNHOLE_DEFAULTS = new int[] {21, 21, 15};
    private static final int[] TRAMPOLINE_VOLLEYBALL_DEFAULTS = new int[] {25, 25, 15};


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
            int[] defaultScores = getGameScoreDefaults(matchType);
            match = new Match(matchType, defaultScores);
            //TODO: Remove when TrampVoll is available:
            if (!matchType.equals("More Games Soon!"))
                openGameScoreSelect(view, gameNum);
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
            match.setGameScore(gameNum - 1, Integer.parseInt(gameScoreSelectView.getSelectionField().textProperty().getValue()));
            if (gameNum < match.getGameScores().length) {
                openGameScoreSelect(view, gameNum);
            }
            else {
                TeamSelectView teamSelectView = new TeamSelectView();
                view.setCurrentControl(teamSelectView);
                view.updateConfigView(teamSelectView.getTeamSelectView());
            }
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
        TeamSelectView teamSelectView = new TeamSelectView();
        view.setCurrentControl(teamSelectView);
        view.updateConfigView(teamSelectView.getTeamSelectView());
    }

    private static void openGameScoreSelect(MainView view, int num) {
        GameScoreSelectView gameScoreSelectView = new GameScoreSelectView(num, match.getGameScores()[num]);
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

package com.advancedsportstechnologies.config.controller;

import com.advancedsportstechnologies.KeyboardController;
import com.advancedsportstechnologies.config.view.GameFormatSelectView;
import com.advancedsportstechnologies.config.view.GameSelectView;
import com.advancedsportstechnologies.config.view.MainView;
import com.advancedsportstechnologies.config.view.TeamSelectView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class ConfigController {
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
            KeyboardController.openGameFormatSelectView(matchType);
        }
    }

    public static void formatSelection(KeyEvent e, MainView view) {
        GameFormatSelectView gameFormatSelectView = (GameFormatSelectView) view.getCurrentControl();

        if (e.getCode() == KeyCode.A || e.getCode() == KeyCode.S) {
            gameFormatSelectView.getSelectionBox().getSelectionModel().selectPrevious();
        }
        else if (e.getCode() == KeyCode.Z || e.getCode() == KeyCode.X) {
            gameFormatSelectView.getSelectionBox().getSelectionModel().selectNext();
        }
        else if (e.getCode() == KeyCode.Q || e.getCode() == KeyCode.W) {
            String format = gameFormatSelectView.getSelectionBox().getSelectionModel().getSelectedItem().toString();
            KeyboardController.openTeamSelect(format);
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
            KeyboardController.startMatch(teamSelectView);
        }
    }

    public static void restartMatch(MainView view) {
        TeamSelectView teamSelectView = new TeamSelectView(KeyboardController.getMatch().getFormat());
        view.setCurrentControl(teamSelectView);
        view.updateConfigView(teamSelectView.getTeamSelectView());
    }
}

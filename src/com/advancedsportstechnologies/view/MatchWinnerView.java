package com.advancedsportstechnologies.view;

import com.advancedsportstechnologies.controller.Controller;
import com.advancedsportstechnologies.Main;
import com.advancedsportstechnologies.controller.PiController;
import com.advancedsportstechnologies.model.Match;
import com.advancedsportstechnologies.model.Team;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;

public class MatchWinnerView {
    private VBox view;
    private Team winningTeam;

    public MatchWinnerView(Team winningTeam) {
        this.winningTeam = winningTeam;
        updateView();
    }

    //TODO: Color of winning team!
    //TODO: Separation of top elements and "Press Start" label!


    private void updateView() {
        String winStr = winningTeam.getTeamName().toLowerCase().endsWith("s") ? " win in " + (Match.getCurrentGame() + 1) + " games!"
                : " wins in " + (Match.getCurrentGame() + 1) + " games!";

        Label teamNameLabel = new Label(winningTeam.getTeamName());
        teamNameLabel.getStyleClass().add("winnerTeamLabel");
        teamNameLabel.setTextFill(winningTeam.getColor());
        Label winStrLabel = new Label(winStr);
        winStrLabel.setTextFill(winningTeam.getColor());
        winStrLabel.getStyleClass().add("winnerLabel");
        Label pressStart = new Label("Press Start for New Game");
        pressStart.getStyleClass().add("pressStartLabel");
        this.view = new VBox(teamNameLabel, winStrLabel, pressStart);
        this.view.getStyleClass().add("winnerView");

        this.setKeyPressListeners();
        if (!Main.debug) {
            PiController.removeEventListeners();
            this.setEventListeners();
        }
    }

    public VBox getView() { return this.view; }

    private void setEventListeners() {
        PiController.reset.addListener((GpioPinListenerDigital) event -> reset());
    }

    private void reset() {
        Platform.runLater(Match::startOrRefresh);
    }


    private void setKeyPressListeners() {
        Main.getScene().setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.Q) {
                Controller.restartScoreboard();
                Match.startOrRefresh();
            }
        });
    }
}

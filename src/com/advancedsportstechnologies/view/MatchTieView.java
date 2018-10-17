package com.advancedsportstechnologies.view;

import com.advancedsportstechnologies.Main;
import com.advancedsportstechnologies.controller.Controller;
import com.advancedsportstechnologies.controller.PiController;
import com.advancedsportstechnologies.model.Match;
import com.advancedsportstechnologies.model.Team;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;

public class MatchTieView {
    private VBox view;

    public MatchTieView() {
        this.setKeyPressListeners();
        if (!Main.debug) {
            PiController.removeEventListeners();
            this.setEventListeners();
        }

        updateView();
    }


    private void updateView() {
        Label matchEndsLabel = new Label("Match ends");
        matchEndsLabel.getStyleClass().add("winnerTeamLabel");
        Label tieStrLabel = new Label("in a tie!");
        tieStrLabel.getStyleClass().add("winnerLabel");
        VBox tieBox = new VBox(matchEndsLabel, tieStrLabel);
        tieBox.getStyleClass().add("center");

        Label pressStart = new Label("Press Start for New Game");
        pressStart.getStyleClass().add("pressStartLabel");
        this.view = new VBox(200, tieBox, pressStart);
        this.view.getStyleClass().add("winnerView");
    }

    public VBox getView() { return this.view; }

    private void setEventListeners() {
        PiController.reset.addListener((GpioPinListenerDigital) event -> reset());
    }

    private void reset() {
        Platform.runLater(() -> {
            Controller.restartScoreboard();
            Match.startOrRefresh();
        });
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

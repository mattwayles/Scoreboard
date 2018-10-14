package com.advancedsportstechnologies.view;

import com.advancedsportstechnologies.controller.Controller;
import com.advancedsportstechnologies.Main;
import com.advancedsportstechnologies.model.Match;
import com.advancedsportstechnologies.model.Team;
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

        Label teamName = new Label(winningTeam.getTeamName());
        teamName.getStyleClass().add("winnerTeamLabel");
        Label winStrLabel = new Label(winStr);
        winStrLabel.getStyleClass().add("winnerLabel");
        Label pressStart = new Label("Press Start for New Game");
        pressStart.getStyleClass().add("pressStartLabel");
        this.view = new VBox(teamName, winStrLabel, pressStart);
        this.view.getStyleClass().add("winnerView");

        this.setKeyPressListeners();
    }

    public VBox getView() { return this.view; }

    private void setKeyPressListeners() {
        Main.getScene().setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.Q) {
                Controller.restartScoreboard();
                Match.start();
            }
        });
    }
}

package com.advancedsportstechnologies.view;

import com.advancedsportstechnologies.Main;
import com.advancedsportstechnologies.controller.PiController;
import com.advancedsportstechnologies.model.Match;
import com.advancedsportstechnologies.model.Team;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class GameWinnerView {
    private VBox view;
    private int seconds = 3;
    private Team winningTeam;
    private Team losingTeam;
    public GameWinnerView(Team winningTeam, Team losingTeam) {
        if (!Main.debug) {
            PiController.removeEventListeners();
        }

        this.winningTeam = winningTeam;
        this.losingTeam = losingTeam;
        updateView();
    }

    private void updateView() {
        String winnerStr = winningTeam.getTeamName().toLowerCase().endsWith("s") ? " win game " + (Match.getCurrentGame() + 1) + "!"
                : " wins game " + (Match.getCurrentGame() + 1) + "!";

        Label teamNameLabel = new Label(winningTeam.getTeamName());
        teamNameLabel.setTextFill(winningTeam.getColor());
        teamNameLabel.getStyleClass().add("winnerTeamLabel");
        Label winnerStrLabel = new Label(winnerStr);
        winnerStrLabel.setTextFill(winningTeam.getColor());
        winnerStrLabel.getStyleClass().add("winnerLabel");
        Label score = new Label(winningTeam.getScore() + " - " + losingTeam.getScore());
        score.getStyleClass().add("splashScoreLabel");
        score.setTextFill(winningTeam.getColor());
        VBox scoreBox = new VBox(teamNameLabel, winnerStrLabel, score);
        scoreBox.getStyleClass().add("center");


        Label countdown = new Label("Next Game in " + seconds);
        countdown.getStyleClass().add("countdownLabel");
        this.view = new VBox(50, scoreBox, countdown);
        this.view.getStyleClass().add("winnerView");
    }

    public VBox getView() { return this.view; }
    public void decrementSeconds() {
        this.seconds = this.seconds - 1;
        updateView();
    }
}

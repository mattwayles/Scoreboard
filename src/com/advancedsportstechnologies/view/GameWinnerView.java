package com.advancedsportstechnologies.view;

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
        this.winningTeam = winningTeam;
        this.losingTeam = losingTeam;
        updateView();
    }

    private void updateView() {
        Label teamName = new Label(winningTeam.getTeamName());
        Label winStr = new Label("wins game " + (Match.getCurrentGame() + 1) + "!");
        Label score = new Label(winningTeam.getScore() + " - " + losingTeam.getScore());
        Label countdown = new Label("Next Game in " + seconds);
        this.view = new VBox(teamName, winStr, score, countdown);
    }

    public VBox getView() { return this.view; }
    public void decrementSeconds() {
        this.seconds = this.seconds - 1;
        updateView();
    }
}

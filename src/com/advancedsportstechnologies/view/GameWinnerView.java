package com.advancedsportstechnologies.view;

import com.advancedsportstechnologies.model.Team;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class GameWinnerView {
    private VBox view;
    private int seconds = 3;
    public GameWinnerView(Team winningTeam, Team losingTeam, int currentGame) {
        Label teamName = new Label(winningTeam.getTeamName());
        Label winStr = new Label("wins game " + currentGame + "!");
        Label score = new Label(winningTeam.getScore() + " - " + losingTeam.getScore());
        this.view = new VBox(teamName, winStr, score);
    }

    public VBox getView() { return this.view; }
    public void decrementSeconds() { this.seconds = this.seconds - 1; }
}

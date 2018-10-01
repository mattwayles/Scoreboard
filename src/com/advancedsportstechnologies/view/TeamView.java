package com.advancedsportstechnologies.view;

import com.advancedsportstechnologies.model.Team;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class TeamView {
    private VBox view;
    private Label teamNameLabel;
    private Label scoreLabel;

    TeamView(Team team) {
        this.teamNameLabel = new Label(team.getTeamName());
        this.scoreLabel = new Label(String.valueOf(team.getScore()));
        this.view = new VBox(teamNameLabel, scoreLabel);
    }

    VBox getView() { return this.view; }

    public Label getTeamNameLabel() { return this.teamNameLabel; }

    Label getScoreLabel() { return this.scoreLabel; }
}

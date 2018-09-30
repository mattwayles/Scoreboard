package com.advancedsportstechnologies.modules.shared.view;

import com.advancedsportstechnologies.Run;
import com.advancedsportstechnologies.modules.shared.view.TeamView;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ViewCreator {
    private static final double HEIGHT = Run.HEIGHT;
    private static final double WIDTH = Run.WIDTH;

    public HBox createView(TeamView team1, TeamView team2) {
        VBox separator = new VBox();
        separator.setPrefHeight(HEIGHT);
        separator.getStyleClass().add("separator");
        HBox view = new HBox(createTeamView(team1), separator, createTeamView(team2));
        view.getStyleClass().add("matchView");
        view.setMaxWidth(WIDTH);

        return view;
    }

    public HBox createTimedView(TeamView team1, TeamView team2, Label timer) {
        VBox topSeparator = new VBox(timer);
        topSeparator.setPrefHeight(HEIGHT / 2 - 20);
        topSeparator.getStyleClass().add("separator");
        VBox bottomSeparator = new VBox(timer);
        bottomSeparator.setPrefHeight(HEIGHT / 2 - 20);
        bottomSeparator.getStyleClass().add("separator");
        VBox middle = new VBox(topSeparator, timer, bottomSeparator);
        HBox view = new HBox(createTeamView(team1), middle, createTeamView(team2));
        view.getStyleClass().add("matchView");
        view.setMaxWidth(WIDTH);

        return view;
    }

    private VBox createTeamView(TeamView team) {
        VBox scoreBox = createScoreBox(team.getScoreLabel());
        return createTeamBox(team.getNameLabel(), team.getGamesWonImgs(), scoreBox);
    }

    private VBox createScoreBox(Label score) {
        VBox box = new VBox(score);
        box.getStyleClass().add("scoreBox");
        return box;
    }

    private VBox createTeamBox(Label teamName, HBox images, VBox scoreBox) {
        VBox box = new VBox(teamName, images, scoreBox);
        box.getStyleClass().add("teamBox");
        box.setPrefWidth(WIDTH / 2);
        box.setMinHeight(HEIGHT);
        return box;
    }
}

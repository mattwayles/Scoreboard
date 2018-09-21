package main.java.com.socialsportstechnology.games.cornhole.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import main.java.com.socialsportstechnology.config.model.Match;

public class CornholeMatchView extends Match {
    private static final String CORNHOLE_ID = "cornhole";
    private static final double HEIGHT = Screen.getPrimary().getVisualBounds().getHeight();
    private static final double WIDTH = Screen.getPrimary().getVisualBounds().getWidth();


    private HBox view;
    private TeamView team1;
    private TeamView team2;


    public CornholeMatchView(String team1Name, String team2Name) {
        this.team1 = new TeamView(team1Name);
        this.team2 = new TeamView(team2Name);
        createCornholeView();
        this.setId(CORNHOLE_ID);
    }

    public HBox getView() { return view; }

    private void createCornholeView() {
        VBox separator = new VBox();
        separator.setPrefHeight(HEIGHT);
        separator.getStyleClass().add("separator");
        this.view = new HBox(createTeamView(team1), separator, createTeamView(team2));
        this.view.getStyleClass().add("cornholeView");
        this.view.setMaxWidth(WIDTH - 20);
    }

    public TeamView getTeam1() { return this.team1; }

    public TeamView getTeam2() { return this.team2; }

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

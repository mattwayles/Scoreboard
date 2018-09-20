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

    private HBox view;
    private TeamView team1;
    private TeamView team2;
    private double screenWidth;
    private double screenHeight;


    public CornholeMatchView(String team1Name, String team2Name) {
        this.team1 = new TeamView(team1Name);
        this.team2 = new TeamView(team2Name);
        createCornholeView();
        this.setId(CORNHOLE_ID);
    }

    public HBox getView() { return view; }

    private void createCornholeView() {
        screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        screenHeight = Screen.getPrimary().getVisualBounds().getHeight();

        VBox separator = new VBox();
        separator.setPrefHeight(screenHeight - 20);
        separator.getStyleClass().add("separator");
        this.view = new HBox(createTeamView(team1), separator, createTeamView(team2));
        //TODO: Move to CSS?
        this.view.setPadding(new Insets(40, 10, 10, 10));
        this.view.setMaxWidth(screenWidth - 20);
    }

    public TeamView getTeam1() { return this.team1; }

    public TeamView getTeam2() { return this.team2; }

    private VBox createTeamView(TeamView team) {
        VBox scoreBox = createScoreBox(team.getScoreLabel());
        return createTeamBox(team.getNameLabel(), team.getGamesWonImgs(), scoreBox);
    }

    private VBox createScoreBox(Label score) {
        VBox box = new VBox(score);
        box.setAlignment(Pos.CENTER);
        box.setPrefHeight(screenHeight);
        return box;
    }

    private VBox createTeamBox(Label teamName, HBox images, VBox scoreBox) {
        VBox box = new VBox(teamName, images, scoreBox);
        //TODO: Move to CSS?
        box.setSpacing(20);
        box.setAlignment(Pos.CENTER);
        box.setPrefWidth(screenWidth / 2);
        return box;
    }
}

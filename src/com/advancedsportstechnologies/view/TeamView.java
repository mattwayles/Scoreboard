package com.advancedsportstechnologies.view;

import com.advancedsportstechnologies.Main;
import com.advancedsportstechnologies.model.Team;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class TeamView {
    private VBox view;
    private Label scoreLabel;
    private Team team;

    TeamView(Team team) {
        this.team = team;
        this.scoreLabel = new Label(String.valueOf(team.getScore()));
        this.scoreLabel.setFont(Main.WIDTH > 1280 ? new Font(500) : new Font(350));
        this.scoreLabel.getStyleClass().add("scoreLabel");
        Label teamNameLabel = new Label(team.getTeamName());
        teamNameLabel.getStyleClass().add("teamNameLabel");
        teamNameLabel.setTextFill(team.getColor());
        teamNameLabel.setFont(Main.WIDTH > 1280 ? new Font(82) : new Font(48));
        ImageView gamesWon = new ImageView(new Image(team.getGamesWon() == 0 ? "/img/placeholder.png" : "/img/gamesWon/gameWon" + this.team.getGamesWon() + ".png"));

        VBox titleBox = new VBox(10, teamNameLabel, gamesWon);
        titleBox.setAlignment(Pos.CENTER);
        this.view = new VBox(80, titleBox, this.scoreLabel);
        this.view.setAlignment(Pos.CENTER);
        this.view.setPrefWidth(Main.WIDTH);
    }

    VBox getView() { return this.view; }

    Label getScoreLabel() { return this.scoreLabel; }

    void setScoreLabel(int score) { this.scoreLabel.textProperty().setValue(String.valueOf(score));}

    public Team getTeam() { return this.team; }
}

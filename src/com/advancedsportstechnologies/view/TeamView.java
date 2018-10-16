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
        Label teamNameLabel = new Label(team.getTeamName());
        teamNameLabel.getStyleClass().add("teamNameLabel");
        teamNameLabel.setTextFill(team.getColor());
        teamNameLabel.setFont(Main.WIDTH > 1280 ? new Font(82) : new Font(60));
        this.scoreLabel = new Label(String.valueOf(team.getScore()));
        this.scoreLabel.setFont(Main.WIDTH > 1280 ? new Font(500) : new Font(350));
        this.scoreLabel.getStyleClass().add("scoreLabel");
        HBox gamesWonBox = new HBox(10);
        if (team.getGamesWon() == 0) {
            gamesWonBox.getChildren().add(new ImageView(new Image("/img/placeholder.png")));
        }
        else {
            gamesWonBox.getChildren().clear();
            gamesWonBox.getChildren().add(new ImageView(new Image("/img/gamesWon/gameWon" + this.team.getGamesWon() + ".png")));
        }

        gamesWonBox.setAlignment(Pos.CENTER);
        this.view = new VBox(teamNameLabel, gamesWonBox, this.scoreLabel);
        this.view.setAlignment(Pos.CENTER);
        this.view.setPrefWidth(Main.WIDTH / 2);
    }

    VBox getView() { return this.view; }

    Label getScoreLabel() { return this.scoreLabel; }

    void setScoreLabel(int score) { this.scoreLabel.textProperty().setValue(String.valueOf(score));}

    public Team getTeam() { return this.team; }
}

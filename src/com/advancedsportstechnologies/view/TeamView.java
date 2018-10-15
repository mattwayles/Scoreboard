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
    private Label teamNameLabel;
    private Label scoreLabel;
    private HBox gamesWonBox;
    private Team team;

    TeamView(Team team) {
        this.team = team;
        this.teamNameLabel = new Label(team.getTeamName());
        this.teamNameLabel.getStyleClass().add("teamNameLabel");
        teamNameLabel.setTextFill(team.getColor());
        teamNameLabel.setFont(Main.WIDTH > 1280 ? new Font(82) : new Font(60));
        this.scoreLabel = new Label(String.valueOf(team.getScore()));
        this.scoreLabel.setFont(Main.WIDTH > 1280 ? new Font(500) : new Font(350));
        this.scoreLabel.getStyleClass().add("scoreLabel");
        this.gamesWonBox = new HBox(10);
        if (team.getGamesWon() == 0) {
            new ImageView(new Image("/img/gameWon.png"));
        }
        else {
            this.gamesWonBox.getChildren().clear();
            for (int i = 0; i < team.getGamesWon(); i++) {
                this.gamesWonBox.getChildren().add(
                        new ImageView(new Image("/img/gameWon.png")));
            }
        }

        this.gamesWonBox.setAlignment(Pos.CENTER);
        this.view = new VBox(this.teamNameLabel, this.gamesWonBox, this.scoreLabel);
        this.view.setAlignment(Pos.CENTER);
        this.view.setPrefWidth(Main.WIDTH / 2);
    }

    VBox getView() { return this.view; }

    public Label getTeamNameLabel() { return this.teamNameLabel; }

    Label getScoreLabel() { return this.scoreLabel; }

    void setScoreLabel(int score) { this.scoreLabel.textProperty().setValue(String.valueOf(score));}

    public HBox getGamesWonBox() { return this.gamesWonBox; }

    public Team getTeam() { return this.team; }
}

package com.advancedsportstechnologies.view;

import com.advancedsportstechnologies.Main;
import com.advancedsportstechnologies.model.Match;
import com.advancedsportstechnologies.model.Team;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Visual representation for a Team object on the scoreboard
 */
public class TeamView {
    private VBox view;
    private Team team;
    private Node scoreLabel;

    /**
     * Create the team view
     * @param team  The Team that this View represents
     */
    TeamView(Team team) {
        this.team = team;

        //Create score label based off Team score
        if (Match.getTheme().equals("glow")) {
            this.scoreLabel = new Text(String.valueOf(team.getScore()));
            this.scoreLabel.setEffect(Match.glow((Color) team.getColor()));
        } else {
            this.scoreLabel = new Label(String.valueOf(team.getScore()));

            Label scoreLabel = (Label) this.scoreLabel;
            scoreLabel.setFont(Main.WIDTH > 1280 ? new Font(450) : new Font(300));
            scoreLabel.setPrefWidth(Main.WIDTH > 1280 ? 600 : 400);
        }
        this.scoreLabel.getStyleClass().add("scoreLabel");

        Label teamNameLabel = new Label(team.getTeamName());
        if (Match.getTheme().equals("glow")) {
            teamNameLabel.setEffect(Match.glow((Color) team.getColor()));
        }
        teamNameLabel.getStyleClass().add("teamNameLabel");
        teamNameLabel.setTextFill(team.getColor());
        teamNameLabel.setFont(Main.WIDTH > 1280 || team.getTeamName().length() <= 8 ? new Font(92) : new Font(56));

        //Import ribbon displaying how many games this team has won
        ImageView gamesWon = new ImageView(new Image(team.getGamesWon() == 0 ? "/img/placeholder.png" : "/img/gamesWon/gameWon" + this.team.getGamesWon() + ".png"));
        gamesWon.getStyleClass().add("gamesWon");

        //Create a box containing team name label and games won image
        VBox titleBox = new VBox(10, teamNameLabel, gamesWon);
        titleBox.setAlignment(Pos.CENTER);

        //Set View with box containing all components
        this.view = new VBox(Main.WIDTH > 1280 ? 20 : 80, titleBox, scoreLabel);
        this.view.setAlignment(Pos.CENTER);
        this.view.setPrefWidth(Main.WIDTH);
    }

    /**
     * Retrieve this TeamView
     * @return  This TeamView
     */
    VBox getView() { return this.view; }

    /**
     * Retrieve the score label
     * @return  This TeamView's score label
     */
    Node getScoreLabel() { return this.scoreLabel; }

    /**
     * Retreive the team represented by this TeamView
     * @return The team represented by this TeamView
     */
    public Team getTeam() { return this.team; }
}

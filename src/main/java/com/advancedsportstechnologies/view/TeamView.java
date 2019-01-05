package com.advancedsportstechnologies.view;

import com.advancedsportstechnologies.Main;
import com.advancedsportstechnologies.model.Match;
import com.advancedsportstechnologies.model.Team;
import com.advancedsportstechnologies.view.texteffects.Glow;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;

/**
 * Visual representation for a Team object on the scoreboard
 */
class TeamView extends TeamView {
    private VBox view;
    private Team team;
    private Node scoreLabel;

    /**
     * Create the team view
     * @param team  The Team that this View represents
     */
    TeamView(Team team) {
        this.team = team;

        //Create score label representing Team score
        if (Match.getTheme().equals("traditional")) { //Scores requiring background boxes
            Label scoreLabel = new Label(String.valueOf(team.getScore()));
            scoreLabel.getStyleClass().add("scoreLabel");
            scoreLabel.setPrefWidth(Main.WIDTH / 3);
            scoreLabel.setFont(new Font(scoreLabel.getFont().getName(), Main.WIDTH / 4));

            this.scoreLabel = scoreLabel;

        } else { //Scores without background boxes
            Text scoreLabel = new Text(String.valueOf(team.getScore()));
            scoreLabel.setFill(team.getColor());
            scoreLabel.getStyleClass().add("scoreText");
            scoreLabel.setFont(new Font(scoreLabel.getFont().getName(), Main.WIDTH / 3.5));
            scoreLabel.setBoundsType(TextBoundsType.LOGICAL_VERTICAL_CENTER);

            this.scoreLabel = scoreLabel;
        }

        //Create team label representing team name
        Label teamNameLabel = new Label(team.getTeamName());
        teamNameLabel.getStyleClass().add("teamNameLabel");
        teamNameLabel.setFont(new Font(teamNameLabel.getFont().getName(), Main.WIDTH / 20));
        teamNameLabel.setTextFill(team.getColor());

        //Add glow effect if Glow theme is active
        if (Match.getTheme().equals("glow")) {
            this.scoreLabel.setEffect(Glow.setGlow((Color) team.getColor()));
            teamNameLabel.setEffect(Glow.setGlow((Color) team.getColor()));
        }

        //Set View with box containing all components
        this.view = new VBox(scoreLabel instanceof Text ? -40 : 20, teamNameLabel, scoreLabel);
        this.view.setPrefWidth(Main.WIDTH);
        this.view.getStyleClass().add("teamViewBox");
    }

    /**
     * Retrieve this UntimedTeamView
     * @return  This UntimedTeamView
     */
    public VBox getView() { return this.view; }

    /**
     * Retrieve the score label
     * @return  This UntimedTeamView's score label
     */
    Node getScoreLabel() { return this.scoreLabel; }

    /**
     * Retreive the team represented by this UntimedTeamView
     * @return The team represented by this UntimedTeamView
     */
    public Team getTeam() { return this.team; }
}

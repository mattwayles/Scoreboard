package com.advancedsportstechnologies.view;

import com.advancedsportstechnologies.Main;
import com.advancedsportstechnologies.model.Match;
import com.advancedsportstechnologies.model.Team;
import com.advancedsportstechnologies.view.texteffects.Glow;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;

/**
 * Visual representation for a Team object on the scoreboard
 */
public class TeamView {
    private VBox view;
    private Team team;
    private Node scoreLabel;
    private Label teamNameLabel;

    /**
     * Create the team view
     * @param team  The Team that this View represents
     */
    TeamView(Team team) {
        this.team = team;

        //Create team name label
        createOrUpdateTeamNameNode();

        //Create score label representing Team score
        createOrUpdateScoreNode();

        //Placeholder for ribbons
        ImageView imageView = new ImageView(new Image("/img/placeholder.png"));

        //Set View with box containing all components
        this.view = new VBox(scoreLabel instanceof Text ? -40 : 20, teamNameLabel, imageView, scoreLabel);
        this.view.setPrefWidth(Main.WIDTH);
        this.view.getStyleClass().add("teamViewBox");
    }

    /**
     * Update this TeamView with a new Team
     * @param team The new team to apply to this TeamView
     */
    void update(Team team) {
        this.team = team;
        update();
    }

    /**
     * Update this TeamView with user input
     */
    void update() {
        //Update team name
        createOrUpdateTeamNameNode();

        //Update score node
        createOrUpdateScoreNode();

        //Update Games Won ribbons
        ImageView imageView = (ImageView) view.getChildren().get(1);
        imageView.setImage(team.getGamesWon() > 0 ?
                new Image("/img/gamesWon/gameWon" + team.getGamesWon() + ".png")
                : new Image("/img/placeholder.png"));

        //Add glow effect if Glow theme is active
        this.scoreLabel.setEffect(Match.getTheme().equals("glow") ? Glow.setGlow((Color) team.getColor()) : null);
        this.teamNameLabel.setEffect(Match.getTheme().equals("glow") ? Glow.setGlow((Color) team.getColor()) : null);

        //Update view
        this.view.getChildren().set(2, scoreLabel);
        this.view.getChildren().set(0, this.getTeamNameLabel());
    }

    /**
     * Create or update the Team Name label
     */
    private void createOrUpdateTeamNameNode() {
        this.teamNameLabel = this.teamNameLabel == null ? new Label(team.getTeamName()) : teamNameLabel;

        //Format Team Name label
        this.teamNameLabel.setTextFill(team.getColor());
        this.teamNameLabel.getStyleClass().add("teamNameLabel");
        this.teamNameLabel.textProperty().setValue(this.team.getTeamName());
        this.teamNameLabel.setFont(new Font(teamNameLabel.getFont().getName(), Main.WIDTH / 20));
    }

    /**
     * Create or update the Score node
     */
    private void createOrUpdateScoreNode() {
        //Scores requiring background boxes
        if (Match.getTheme().equals("traditional")) {
            Label label = scoreLabel instanceof Label ? (Label) scoreLabel : new Label();

            //Format label
            label.textProperty().setValue(String.valueOf(team.getScore()));
            label.getStyleClass().add("scoreLabel");
            label.setPrefWidth(Main.WIDTH / 3);
            label.setFont(new Font(label.getFont().getName(), Main.WIDTH / 4));
            label.setVisible(true);
            this.scoreLabel = label;
        } else { //Scores without background boxes
            Text text = scoreLabel instanceof Text ? (Text) scoreLabel : new Text();

            //Format text
            text.textProperty().setValue(String.valueOf(team.getScore()));
            text.setFill(team.getColor());
            text.getStyleClass().add("scoreText");
            text.setFont(new Font(text.getFont().getName(), Main.WIDTH / 3.5));
            text.setBoundsType(TextBoundsType.LOGICAL_VERTICAL_CENTER);
            text.setVisible(true);
            this.scoreLabel = text;
        }
    }

    /**
     * Retrieve this TeamView
     * @return  This TeamView
     */
    public VBox getView() { return this.view; }

    /**
     * Retrieve the score label
     * @return  This TeamView's score label
     */
    public Node getScoreLabel() { return this.scoreLabel; }

    /**
     * Retrieve the team name label
     * @return  This TeamView's score label
     */
    public Node getTeamNameLabel() { return this.teamNameLabel; }

    /**
     * Retrieve the team represented by this TeamView
     * @return The team represented by this TeamView
     */
    public Team getTeam() { return this.team; }
}

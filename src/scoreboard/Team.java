package scoreboard;

import javafx.scene.control.Label;

class Team {
    private Label teamNameLabel;
    private Label scoreLabel;

    Team(String teamName, Integer score) {
        this.teamNameLabel = new Label(teamName);
        this.scoreLabel = new Label(score.toString());
        this.teamNameLabel.getStyleClass().add("teamNameLabel");
        this.scoreLabel.getStyleClass().add("scoreLabel");
    }

    Label getTeamName() { return teamNameLabel; }

    void setTeamName(String teamName) {
        teamNameLabel.textProperty().setValue(teamName);
    }

    Label getScore() { return scoreLabel; }

    void setScore(Integer score) {
        scoreLabel.textProperty().setValue(score.toString());
    }
}

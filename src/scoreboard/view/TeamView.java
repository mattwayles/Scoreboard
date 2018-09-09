package scoreboard.view;

import javafx.scene.control.Label;
import scoreboard.model.Team;

public class TeamView extends Team {
    private Label nameLabel;
    private Label scoreLabel;
    private Label gamesWonLabel;

    TeamView(Team team) {
        this.nameLabel = new Label(team.getTeamName());
        this.nameLabel.getStyleClass().add("teamNameLabel");
        this.scoreLabel = new Label(String.valueOf(team.getScore()));
        this.scoreLabel.getStyleClass().add("scoreLabel");
        this.gamesWonLabel = new Label(String.valueOf(team.getGamesWon()));
    }

    Label getNameLabel() { return this.nameLabel; }

    public void setNameLabel(String name) { this.nameLabel.textProperty().setValue(name);}

    Label getScoreLabel() { return this.scoreLabel; }

    public void setScoreLabel(int score) { this.scoreLabel.textProperty().setValue(String.valueOf(score));}

    Label getGamesWonLabel() { return this.gamesWonLabel; }

    public void setGamesWonLabel(int gamesWon) { this.gamesWonLabel.textProperty().setValue(String.valueOf(gamesWon));}

}

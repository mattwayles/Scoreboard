package scoreboard.view;

import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import scoreboard.model.Team;

public class Match extends Main {
    HBox view;
    Team team1 = new Team("Team 1", 0);
    Team team2 = new Team("Team 2", 0);
    private Separator separator = new Separator();

    Match() {
        separator.setPrefHeight(HEIGHT);
        separator.setOrientation(Orientation.VERTICAL);
        view = new HBox(createTeamView(team1), separator, createTeamView(team2));
    }

    void updateView() {
        view = new HBox(createTeamView(team1), separator, createTeamView(team2));
    }

    private VBox createTeamView(Team team) {
        VBox scoreBox = createScoreBox(team.getScore());
        return createTeamBox(team.getTeamName(), team.getGamesWon(), scoreBox);
    }

    private VBox createScoreBox(int score) {
        VBox box = new VBox(createLabel(String.valueOf(score), "scoreLabel"));
        box.setAlignment(Pos.CENTER);
        box.setPrefHeight(HEIGHT);
        return box;
    }

    private VBox createTeamBox(String teamName, int gamesWon, VBox scoreBox) {
        VBox box = new VBox(createLabel(teamName, "teamNameLabel"), new Label(String.valueOf(gamesWon)), scoreBox);
        box.setAlignment(Pos.CENTER);
        box.setPrefWidth(WIDTH / 2);
        return box;
    }

    private Label createLabel(String str, String style) {
        Label label = new Label(str);
        label.getStyleClass().add(style);
        return label;
    }
}

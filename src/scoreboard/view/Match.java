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
    TeamView team1 = new TeamView(new Team("Team 1", 0));
    TeamView team2 = new TeamView(new Team("Team 2", 0));
    private Separator separator = new Separator();

    Match() {
        separator.setPrefHeight(HEIGHT);
        separator.setOrientation(Orientation.VERTICAL);
        view = new HBox(createTeamView(team1), separator, createTeamView(team2));
    }

    private VBox createTeamView(TeamView team) {
        VBox scoreBox = createScoreBox(team.getScoreLabel());
        return createTeamBox(team.getNameLabel(), team.getGamesWonLabel(), scoreBox);
    }

    private VBox createScoreBox(Label score) {
        VBox box = new VBox(score);
        box.setAlignment(Pos.CENTER);
        box.setPrefHeight(HEIGHT);
        return box;
    }

    private VBox createTeamBox(Label teamName, Label gamesWon, VBox scoreBox) {
        VBox box = new VBox(teamName, gamesWon, scoreBox);
        box.setAlignment(Pos.CENTER);
        box.setPrefWidth(WIDTH / 2);
        return box;
    }
}

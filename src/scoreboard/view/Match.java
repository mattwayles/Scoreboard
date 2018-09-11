package scoreboard.view;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import scoreboard.model.Team;

import java.util.ArrayList;

public class Match extends Main {
    private HBox view;
    private TeamView team1;
    private TeamView team2;

    Match(String team1Name, String team2Name) {
        this.team1 = new TeamView(team1Name);
        this.team2 = new TeamView(team2Name);
        newGame();
    }

    public void newGame() {
        VBox separator = new VBox();
        separator.setPrefHeight(HEIGHT - 20);
        separator.getStyleClass().add("separator");
        this.view = new HBox(createTeamView(team1), separator, createTeamView(team2));
        //TODO: Move to CSS?
        this.view.setPadding(new Insets(40, 10, 10, 10));
        this.view.setMaxWidth(WIDTH - 20);
    }

//    public void displayGameWinner(String teamName, int games) {
//        //TODO: Move to CSS?
//        Label winner = new Label(teamName + " wins game " + games + "!");
//        Label score = new Label(team1.getScore() + " - " + team2.getScore());
//        winner.getStyleClass().add("winnerLabel");
//        score.getStyleClass().add("winnerLabel");
//        String winningColor = teamName.equals(team1.getTeamName()) ? "#0800ad" : "#a05500";
//        winner.setTextFill(Paint.valueOf(winningColor));
//        score.setTextFill(Paint.valueOf(winningColor));
//        VBox message = new VBox(winner, score);
//        message.setSpacing(100);
//        message.setAlignment(Pos.CENTER);
//        view = new HBox(message);
//        view.setAlignment(Pos.CENTER);
//    }

    public void displayMatchWinner(String teamName, int games) {
        //TODO: Move to CSS?
        Label winner = new Label(teamName + " wins in " + games + " games!");
        winner.getStyleClass().add("winnerLabel");
        String winningColor = teamName.equals(team1.getTeamName()) ? "#0800ad" : "#a05500";
        winner.setTextFill(Paint.valueOf(winningColor));
        Label notice = new Label("Press Start to reset (Q / W until controllers are working)");
        notice.getStyleClass().add("pressStartLabel");
        VBox message = new VBox(winner, notice);
        message.setSpacing(100);
        message.setAlignment(Pos.CENTER);
        view = new HBox(message);
        view.setAlignment(Pos.CENTER);
    }

    HBox getMatchView() { return this.view; }

    public TeamView getTeam1() { return this.team1; }

    public TeamView getTeam2() { return this.team2; }

    private VBox createTeamView(TeamView team) {
        VBox scoreBox = createScoreBox(team.getScoreLabel());
        return createTeamBox(team.getNameLabel(), team.getGamesWonImgs(), scoreBox);
    }

    private VBox createScoreBox(Label score) {
        VBox box = new VBox(score);
        box.setAlignment(Pos.CENTER);
        box.setPrefHeight(HEIGHT);
        return box;
    }

    private VBox createTeamBox(Label teamName, HBox images, VBox scoreBox) {
        VBox box = new VBox(teamName, images, scoreBox);
        //TODO: Move to CSS?
        box.setSpacing(20);
        box.setAlignment(Pos.CENTER);
        box.setPrefWidth(WIDTH / 2);
        return box;
    }
}

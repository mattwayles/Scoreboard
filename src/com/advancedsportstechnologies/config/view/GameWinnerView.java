package com.advancedsportstechnologies.config.view;

import com.advancedsportstechnologies.modules.shared.view.TeamView;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class GameWinnerView extends MainView {
    private static final String GAME_WINNER_ID = "gameWinner";
    private int secondsRemaining;
    private HBox view;
    private int team1Score;
    private int team2Score;

    public GameWinnerView() {
        this(3, 0,0);
    }

    public GameWinnerView(int secs, int team1Score, int team2Score) {
        this.secondsRemaining = secs;
        this.team1Score = team1Score;
        this.team2Score = team2Score;
        this.setId(GAME_WINNER_ID);
    }

    public void displayGameWinner(TeamView team, int games) {
        String winnerStr = team.getTeamName().toLowerCase().endsWith("s") ? " win game " + games + "!"
                : " wins game " + games + "!";
        Label winningTeam = new Label(team.getTeamName());
        winningTeam.getStyleClass().add("winnerLabelTeam");
        winningTeam.setTextFill(team.getColor());
        Label wins = new Label(winnerStr);
        wins.getStyleClass().add("winnerLabel");
        wins.setTextFill(team.getColor());
        VBox winnerBox = new VBox(winningTeam, wins);
        winnerBox.getStyleClass().add("winnerBox");
        Label score = new Label(this.team1Score + " - " + this.team2Score);
        score.getStyleClass().add("splashScoreLabel");
        score.setTextFill(team.getColor());
        Label notice = new Label("Next Game in ");
        notice.getStyleClass().add("nextGameLabel");
        Label countdown = new Label(this.secondsRemaining + "...");
        countdown.getStyleClass().add("countdownLabel");
        HBox nextGameBox = new HBox(notice, countdown);
        nextGameBox.getStyleClass().add("nextGameBox");
        VBox message = new VBox(winnerBox, score, nextGameBox);
        message.getStyleClass().add("winnerMessage");
        this.view = new HBox(message);
        this.view.getStyleClass().add("winnerView");
    }

    public HBox getView() { return view; }

    public void setView(HBox view) { this.view = view; }

    public void decrementSeconds() {
        this.secondsRemaining--;
    }

}

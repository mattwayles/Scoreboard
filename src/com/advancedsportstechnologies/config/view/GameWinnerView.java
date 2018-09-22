package com.advancedsportstechnologies.config.view;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import com.advancedsportstechnologies.config.model.Match;

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

    public void displayGameWinner(String teamName, int games, Paint color) {
        String winnerStr = teamName.toLowerCase().endsWith("s") ? teamName + " win game " + games + "!"
                : teamName + " wins game " + games + "!";
        Label winner = new Label(winnerStr);
        winner.getStyleClass().add("winnerLabel");
        winner.setTextFill(color);
        Label score = new Label(this.team1Score + " - " + this.team2Score);
        score.getStyleClass().add("splashScoreLabel");
        score.setTextFill(color);
        Label notice = new Label("Next Game in ");
        notice.getStyleClass().add("nextGameLabel");
        Label countdown = new Label(this.secondsRemaining + "...");
        countdown.getStyleClass().add("countdownLabel");
        HBox nextGameBox = new HBox(notice, countdown);
        nextGameBox.getStyleClass().add("nextGameBox");
        VBox message = new VBox(winner, score, nextGameBox);
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

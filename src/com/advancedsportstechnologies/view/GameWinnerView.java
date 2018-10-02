package com.advancedsportstechnologies.view;

import com.advancedsportstechnologies.model.Match;
import com.advancedsportstechnologies.model.Team;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class GameWinnerView {
    private VBox view;
    private int seconds = 3;
    private Team winningTeam;
    private Team losingTeam;
    public GameWinnerView(Team winningTeam, Team losingTeam) {
        this.winningTeam = winningTeam;
        this.losingTeam = losingTeam;
        updateView();
    }

    //TODO: Color of winning team!
    //TODO: Separation of top elements and "Next Game" label!

    private void updateView() {
        String winnerStr = winningTeam.getTeamName().toLowerCase().endsWith("s") ? " win game " + (Match.getCurrentGame() + 1) + "!"
                : " wins game " + (Match.getCurrentGame() + 1) + "!";

        Label teamName = new Label(winningTeam.getTeamName());
        teamName.getStyleClass().add("winnerTeamLabel");
        Label winnerStrLabel = new Label(winnerStr);
        winnerStrLabel.getStyleClass().add("winnerLabel");
        Label score = new Label(winningTeam.getScore() + " - " + losingTeam.getScore());
        score.getStyleClass().add("splashScoreLabel");
        Label countdown = new Label("Next Game in " + seconds);
        countdown.getStyleClass().add("countdownLabel");
        this.view = new VBox(teamName, winnerStrLabel, score, countdown);
        this.view.getStyleClass().add("winnerView");
    }

    public VBox getView() { return this.view; }
    public void decrementSeconds() {
        this.seconds = this.seconds - 1;
        updateView();
    }
}

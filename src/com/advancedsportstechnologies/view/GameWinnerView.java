package com.advancedsportstechnologies.view;

import com.advancedsportstechnologies.Main;
import com.advancedsportstechnologies.controller.PiController;
import com.advancedsportstechnologies.model.Match;
import com.advancedsportstechnologies.model.Team;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Display a Game Winner splash screen and count down 3 seconds until the next game starts
 */
public class GameWinnerView {
    private VBox view;
    private int seconds = 3;
    private Team losingTeam;
    private Team winningTeam;

    /**
     * Create a new game winner view. requires winning team and losting team to display the scores
     * @param winningTeam   The team that won the game
     * @param losingTeam    The team that lost the game
     */
    public GameWinnerView(Team winningTeam, Team losingTeam) {
        //Always remove existing listeners before creating new ones
        if (!Main.debug) {
            PiController.removeEventListeners();
        }

        this.winningTeam = winningTeam;
        this.losingTeam = losingTeam;
        updateView();
    }

    /**
     * Create the actual GameWinnerView splash screen
     */
    private void updateView() {
        //Proper grammar, of course ;)
        String winnerStr = winningTeam.getTeamName().toLowerCase().endsWith("s") ? " win game " + (Match.getCurrentGame() + 1) + "!"
                : " wins game " + (Match.getCurrentGame() + 1) + "!";

        //Create label for winning team name
        Label teamNameLabel = new Label(winningTeam.getTeamName());
        teamNameLabel.setTextFill(winningTeam.getColor());
        teamNameLabel.getStyleClass().add("winnerTeamLabel");

        //Create label for 'win(s) game x!"
        Label winnerStrLabel = new Label(winnerStr);
        winnerStrLabel.setTextFill(winningTeam.getColor());
        winnerStrLabel.getStyleClass().add("winnerLabel");

        //Create score label
        Label score = new Label(winningTeam.getScore() + " - " + losingTeam.getScore());
        score.getStyleClass().add("splashScoreLabel");
        score.setTextFill(winningTeam.getColor());

        //Put team name label, 'wins game x', and score label in a VBox
        VBox scoreBox = new VBox(teamNameLabel, winnerStrLabel, score);
        scoreBox.getStyleClass().add("center");

        //Create label for three-second countdown
        Label countdown = new Label("Next Game in " + seconds);
        countdown.getStyleClass().add("countdownLabel");

        //Set the view with all components included
        this.view = new VBox(50, scoreBox, countdown);
        this.view.getStyleClass().add("winnerView");
    }

    /**
     * Retrieve this GameWinnerView Node instance
     * @return this GameWinnerView Node instance
     */
    public VBox getView() { return this.view; }

    /**
     * Decrement the seconds of the countdown
     */
    public void decrementSeconds() {
        this.seconds = this.seconds - 1;
        updateView();
    }
}

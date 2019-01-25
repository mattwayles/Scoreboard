package com.advancedsportstechnologies.view;

import com.advancedsportstechnologies.Main;
import com.advancedsportstechnologies.controller.Controller;
import com.advancedsportstechnologies.controller.PiController;
import com.advancedsportstechnologies.model.Match;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;

/**
 * If Bluetooth users send in an even game number, there may be a tie. This View creates a tie splash screen
 */
public class MatchTieView {
    private VBox view;

    /**
     * Create the Match Tie splash screen
     */
    public MatchTieView() {
        //Match is complete
        Match.setActive(false);

        //Set keyboard listeners
        this.setKeyPressListeners();
        if (!Main.debug) {
            //Always remove GPIO listeners before creating new ones!
            PiController.removeEventListeners();
            this.setEventListeners();
        }

        updateView();
    }

    /**
     * Create the actual MatchTieView splash screen
     */
    private void updateView() {
        //'Game Over' label
        Label gameOverLabel = new Label("Game Over");
        gameOverLabel.getStyleClass().add("gameOverLabel");
        gameOverLabel.setTextFill(Match.getTheme().equals("dark") ? Paint.valueOf("#FFF") : Paint.valueOf("#000"));

        //Box that contains 'Game Over' ' it's a tie!'
        VBox tieBox = new VBox(gameOverLabel);
        if (Match.getTeamOne().getGamesWon() == Match.getTeamTwo().getGamesWon()) {
            //'it's a tie!' label
            Label tieStrLabel = new Label("It's a tie!");
            tieStrLabel.getStyleClass().add("winnerLabel");
            tieStrLabel.setTextFill(Match.getTheme().equals("dark") ? Paint.valueOf("#FFF") : Paint.valueOf("#000"));
            tieBox.getChildren().add(tieStrLabel);
        }
        else {
            Label team1Label = new Label(Match.getTeamOne().getTeamName() + "   " + Match.getTeamOne().getGamesWon());
            team1Label.getStyleClass().add("winnerLabel");
            team1Label.setTextFill(Match.getTeamOne().getColor());
            Label team2Label = new Label(Match.getTeamTwo().getTeamName() + "   " + Match.getTeamTwo().getGamesWon());
            team2Label.getStyleClass().add("winnerLabel");
            team2Label.setTextFill(Match.getTeamTwo().getColor());
            tieBox.getChildren().add(team1Label);
            tieBox.getChildren().add(team2Label);
        }

        tieBox.getStyleClass().add("center");

        //Press Start label
        Label pressStart = new Label("Press Start for New Game");
        pressStart.getStyleClass().add("pressStartLabel");

        //Box that contains all components
        this.view = new VBox(200, tieBox, pressStart);
        this.view.getStyleClass().add("winnerView");
    }

    /**
     * Retrieve this MatchTieView Node instance
     * @return this MatchTieView Node instance
     */
    public VBox getView() { return this.view; }

    /**
     * Sent event listeners for Pi button presses
     */
    private void setEventListeners() {
        PiController.reset.addListener((GpioPinListenerDigital) event -> reset());
    }

    /**
     * Reset the scoreboard if Pi Controller 'reset' button pressed
     */
    private void reset() {
        Platform.runLater(() -> {
            Controller.restartScoreboard();

            //TODO: Fix
            //Match.startOrRefresh();
        });
    }

    /**
     * Set listeners for keyboard button presses
     */
    private void setKeyPressListeners() {
        Main.getScene().setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.Q) {
                Controller.restartScoreboard();

                //TODO: Fix
                //Match.startOrRefresh();
            }
        });
    }
}

package com.advancedsportstechnologies.view;

import com.advancedsportstechnologies.Main;
import com.advancedsportstechnologies.controller.Controller;
import com.advancedsportstechnologies.controller.PiController;
import com.advancedsportstechnologies.model.Match;
import com.advancedsportstechnologies.model.Team;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;

/**
 * If Bluetooth users send in an even game number, there may be a tie. This View creates a tie splash screen
 */
public class MatchTieView {
    private VBox view;

    /**
     * Create the Match Tie splash screen
     */
    public MatchTieView() {
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
        //'Match Ends' label
        Label matchEndsLabel = new Label("Match ends");
        matchEndsLabel.getStyleClass().add("winnerTeamLabel");

        //'in a tie!' label
        Label tieStrLabel = new Label("in a tie!");
        tieStrLabel.getStyleClass().add("winnerLabel");

        //Box that contains 'Match ends' ' in a tie!'
        VBox tieBox = new VBox(matchEndsLabel, tieStrLabel);
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
            Match.startOrRefresh();
        });
    }

    /**
     * Set listeners for keyboard button presses
     */
    private void setKeyPressListeners() {
        Main.getScene().setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.Q) {
                Controller.restartScoreboard();
                Match.startOrRefresh();
            }
        });
    }
}

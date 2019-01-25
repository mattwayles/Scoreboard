package com.advancedsportstechnologies.view;

import com.advancedsportstechnologies.controller.Controller;
import com.advancedsportstechnologies.Main;
import com.advancedsportstechnologies.controller.PiController;
import com.advancedsportstechnologies.model.Match;
import com.advancedsportstechnologies.model.Team;
import com.advancedsportstechnologies.model.UntimedMatch;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;

/**
 * Create a Match Winner View
 */
public class MatchWinnerView {
    private VBox view;
    private Team winningTeam;

    /**
     * Create the MatchWinnerView screen and set listeners
     * @param winningTeam The team that won the Match
     */
    public MatchWinnerView(Team winningTeam) {
        //Match is complete
        Match.setActive(false);

        //Set keyboard listeners
        this.setKeyPressListeners();

        //Always remove GPIO listeners before creating new ones!
        if (!Main.debug) {
            PiController.removeEventListeners();
            this.setEventListeners();
        }

        this.winningTeam = winningTeam;
        updateView();
    }

    /**
     * Create the actual MatchWinnerView splash screen
     */
    private void updateView() {
        //Proper grammar, of course ;)
        String winStr = winningTeam.getTeamName().toLowerCase().endsWith("s") ? " win in " + (UntimedMatch.getCurrentGame() + 1) + " games!"
                : " wins in " + (UntimedMatch.getCurrentGame() + 1) + " games!";

        //Create label for winning team name
        Label teamNameLabel = new Label(winningTeam.getTeamName());
        teamNameLabel.getStyleClass().add("winnerTeamLabel");
        teamNameLabel.setTextFill(winningTeam.getColor());

        //Create label for 'win(s) in x games!"
        Label winStrLabel = new Label(winStr);
        winStrLabel.setTextFill(winningTeam.getColor());
        winStrLabel.getStyleClass().add("winnerLabel");

        //Put team name label, 'wins in x games', and score label in a VBox
        VBox winnerBox = new VBox(teamNameLabel, winStrLabel);
        winnerBox.getStyleClass().add("center");

        //Press Start label
        Label pressStart = new Label("Press Start for New Game");
        pressStart.getStyleClass().add("pressStartLabel");

        //Set the view with all components included
        this.view = new VBox(200, winnerBox, pressStart);
        this.view.getStyleClass().add("winnerView");
    }

    /**
     * Retrieve this MatchWinnerView Node instance
     * @return this MatchWinnerView Node instance
     */
    public VBox getView() { return this.view; }

    /**
     * Sent event listeners for Pi button presses
     */
    private void setEventListeners() {
        PiController.controller2Down.addListener((GpioPinListenerDigital) event -> {
            if (event.getState().isHigh()) {
                Platform.runLater(() -> {
                            Image image = new Image("img/finishHimEasterEgg.gif", Main.WIDTH, Main.HEIGHT, false, false);
                            ImageView imageView = new ImageView(image);
                            this.view = new VBox(imageView);
                            Main.getRoot().getChildren().set(0, this.getView());
                        }
                );
            }
        });
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

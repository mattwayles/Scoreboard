package com.advancedsportstechnologies.config.view;

import com.advancedsportstechnologies.Run;
import com.advancedsportstechnologies.config.controller.Controller;
import com.advancedsportstechnologies.config.controller.PiController;
import com.advancedsportstechnologies.modules.shared.view.TeamView;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.HashSet;
import java.util.Set;

public class MatchWinnerView extends MainView {
    private HBox view;

    public MatchWinnerView() {
        if (!Run.debug) {
            this.setEventListeners();
        } else {
            this.setKeyPressListeners();
        }
    }

    public void displayMatchWinner(TeamView team, int games, String gameType) {
        String winStr = team.getTeamName().toLowerCase().endsWith("s") ? " win in " + games + " games!"
                : " wins game " + games + "!";

        Label winningTeam = new Label(team.getTeamName());
        winningTeam.getStyleClass().add("winnerLabelTeam");
        winningTeam.setTextFill(team.getColor());

        Label winner = new Label(winStr);
        winner.getStyleClass().add("winnerLabel");
        winner.setTextFill(team.getColor());

        VBox winnerBox = new VBox(winningTeam, winner);
        winnerBox.getStyleClass().add("winnerBox");
        Label press = new Label("Press Start for new " + gameType + " match");
        press.getStyleClass().add("pressStartLabel");
        Label hold = new Label("Hold Start to Restart");
        hold.getStyleClass().add("pressStartLabel");
        VBox message = new VBox(winnerBox, press, hold);
        message.getStyleClass().add("winnerMessage");
        this.view = new HBox(message);
        this.view.getStyleClass().add("winnerView");
        this.setId("matchView");
    }

    public HBox getView() { return view; }

    public void setView(HBox view) { this.view = view; }

    private void setEventListeners() {
        PiController.removeEventListeners();
        PiController.reset.addListener((GpioPinListenerDigital) event -> {
            if (event.getState().isLow()) {
                Controller.getView().setKeyPressTime(System.currentTimeMillis());
            }
            else {
                Platform.runLater(() -> {
                    if (!Controller.resetButtonHeld()) {
                        Platform.runLater(PiController::openTeamSelect);
                    }
                    Controller.getView().setKeyPressTime(0);
                    //view.getKeysDown().remove(event.getCode());
                });
            }
        });
    }

    private void setKeyPressListeners() {

        Run.getScene().setOnKeyReleased(e -> {
            MainView view = Controller.getView();
            if (view.getKeysDown().contains(KeyCode.A) && view.getKeysDown().contains(KeyCode.Z)) {
                if (System.currentTimeMillis() - view.getKeyPressTime() >= 3000L) {
                        Controller.easterEgg("img/easterEgg.gif");
                }
            } else if (e.getCode() == KeyCode.Q) {
                if (!Controller.resetButtonHeld()) {
                    Controller.openTeamSelect();
                }
            }
            view.setKeyPressTime(0);
            view.getKeysDown().remove(e.getCode());
        });
    }


}

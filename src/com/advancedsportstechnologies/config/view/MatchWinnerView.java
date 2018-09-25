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

    public void displayMatchWinner(TeamView team, int games) {
        Label winner = new Label(team.getTeamName() + " wins in " + games + " games!");
        winner.getStyleClass().add("winnerLabel");
        winner.setTextFill(team.getColor());
        Label notice = new Label("Press Start to reset (Q / W until controllers are working)");
        notice.getStyleClass().add("pressStartLabel");
        VBox message = new VBox(winner, notice);
        message.getStyleClass().add("winnerMessage");
        this.view = new HBox(message);
        this.view.getStyleClass().add("winnerView");
        this.setId("matchView");
    }

    public HBox getView() { return view; }

    public void setView(HBox view) { this.view = view; }

    private void setEventListeners() {
        PiController.reset.addListener((GpioPinListenerDigital) event -> {
            if (event.getState().isHigh()) {
                Platform.runLater(PiController::openTeamSelect);
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

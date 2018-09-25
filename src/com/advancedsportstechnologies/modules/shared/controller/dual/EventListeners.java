package com.advancedsportstechnologies.modules.shared.controller.dual;

import com.advancedsportstechnologies.Run;
import com.advancedsportstechnologies.config.controller.PiController;
import com.advancedsportstechnologies.modules.shared.view.TeamView;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import javafx.application.Platform;

public class EventListeners {
    public static void setEventListeners(TeamView team1, TeamView team2) {
        PiController.controller1Up.addListener((GpioPinListenerDigital) event -> {
            if (event.getState().isHigh()) {
                Platform.runLater(() -> {
                    team1.setScore(team1.getScore() + 1);
                    team1.setScoreLabel(team1.getScore());
                });
            }
        });
        PiController.controller1Down.addListener((GpioPinListenerDigital) event -> {
            if (event.getState().isHigh() && team1.getScore() > 0) {
                Platform.runLater(() -> {
                    team1.setScore(team1.getScore() - 1);
                    team1.setScoreLabel(team1.getScore());
                    GameController.checkWinner(team1, team2);
                });
            }
        });
        PiController.controller2Up.addListener((GpioPinListenerDigital) event -> {
            if (event.getState().isHigh()) {
                Platform.runLater(() -> {
                    team2.setScore(team2.getScore() + 1);
                    team2.setScoreLabel(team2.getScore());
                });
            }
        });
        PiController.controller2Down.addListener((GpioPinListenerDigital) event -> {
            if (event.getState().isHigh()  && team1.getScore() > 0) {
                Platform.runLater(() -> {
                    team2.setScore(team2.getScore() - 1);
                    team2.setScoreLabel(team2.getScore());
                });
            }
        });
        PiController.reset.addListener((GpioPinListenerDigital) event -> {
            if (event.getState().isHigh()) {
                Platform.runLater(PiController::openTeamSelect);
            }
        });
    }
}

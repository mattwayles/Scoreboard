package com.advancedsportstechnologies.modules.games.cornhole.view;

import com.advancedsportstechnologies.Run;
import com.advancedsportstechnologies.config.controller.Controller;
import com.advancedsportstechnologies.config.controller.PiController;
import com.advancedsportstechnologies.config.view.MainView;
import com.advancedsportstechnologies.config.view.ViewCreator;
import com.advancedsportstechnologies.modules.shared.controller.dual.GameController;
import com.advancedsportstechnologies.modules.shared.view.TeamView;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import javafx.application.Platform;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;

public class CornholeMatchView extends MainView {
    private HBox view;
    private TeamView team1;
    private TeamView team2;


    public CornholeMatchView(String team1Name, String team2Name) {
        this.team1 = new TeamView(team1Name);
        team1.setColor(Paint.valueOf("#0800ad"));
        this.team2 = new TeamView(team2Name);
        team2.setColor(Paint.valueOf("#a05500"));
        createCornholeView();
        if (!Run.debug) {
            setEventListeners(team1, team2);
        } else {
            setKeyPressListeners(team1, team2);
        }
    }

    public HBox getView() { return view; }

    private void createCornholeView() {
        ViewCreator vc = new ViewCreator();
        this.view = vc.createView(this.team1, this.team2);
        this.view.getStyleClass().add("cornholeMatchView");
    }

    public void resetScores() {
        this.team1.setScore(0);
        this.team2.setScore(0);
        this.team1.setScoreLabel(this.team1.getScore());
        this.team2.setScoreLabel(this.team2.getScore());
    }

    private static void setEventListeners(TeamView team1, TeamView team2) {
        PiController.controller1Up.addListener((GpioPinListenerDigital) event -> {
            if (event.getState().isHigh()) {
                Platform.runLater(() -> {
                    team1.setScore(team1.getScore() + 1);
                    team1.setScoreLabel(team1.getScore());
                    GameController.checkWinner(team1, team2);
                });
            }
        });
        PiController.controller1Down.addListener((GpioPinListenerDigital) event -> {
            if (event.getState().isHigh() && team1.getScore() > 0) {
                Platform.runLater(() -> {
                    team1.setScore(team1.getScore() - 1);
                    team1.setScoreLabel(team1.getScore());
                });
            }
        });
        PiController.controller2Up.addListener((GpioPinListenerDigital) event -> {
            if (event.getState().isHigh()) {
                Platform.runLater(() -> {
                    team2.setScore(team2.getScore() + 1);
                    team2.setScoreLabel(team2.getScore());
                    GameController.checkWinner(team1, team2);
                });
            }
        });
        PiController.controller2Down.addListener((GpioPinListenerDigital) event -> {
            if (event.getState().isHigh()) {
                System.out.println("Controller 2 Down LOW");
                Platform.runLater(() -> {
                    if (event.getState().isHigh() && team1.getScore() > 0) {
                        team2.setScore(team2.getScore() - 1);
                        team2.setScoreLabel(team2.getScore());
                    }
                });
            }
        });
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
                });
            }
        });
    }

    private static void setKeyPressListeners(TeamView team1, TeamView team2) {
        Run.getScene().setOnKeyReleased(e -> GameController.changeScore(e, team1, team2));
    }
}

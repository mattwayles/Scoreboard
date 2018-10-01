package com.advancedsportstechnologies.modules.games.basketball.view;

import com.advancedsportstechnologies.Run;
import com.advancedsportstechnologies.config.controller.Controller;
import com.advancedsportstechnologies.config.controller.PiController;
import com.advancedsportstechnologies.config.view.MainView;
import com.advancedsportstechnologies.modules.shared.view.ViewCreator;
import com.advancedsportstechnologies.modules.shared.controller.dual.GameController;
import com.advancedsportstechnologies.modules.shared.model.Timer;
import com.advancedsportstechnologies.modules.shared.view.TeamView;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;

public class BasketballMatchView extends MainView {


    private HBox view;
    private TeamView team1;
    private TeamView team2;
    private Label timer;


    public BasketballMatchView(String team1Name, String team2Name) {
        this.team1 = new TeamView(team1Name);
        team1.setColor(Paint.valueOf("#0800ad"));
        this.team2 = new TeamView(team2Name);
        team2.setColor(Paint.valueOf("#a05500"));
        createBasketballView();
        if (!Run.debug) {
            this.setEventListeners();
        } else {
            this.setKeyPressListeners(team1, team2);
        }
    }

    public HBox getView() { return view; }

    private void createBasketballView() {
        ViewCreator vc = new ViewCreator();
        this.timer = new Label(Controller.getMatch().getPeriodMins() + ":00");
        this.view = vc.createTimedView(this.team1, this.team2, this.timer);
        this.view.getStyleClass().add("basketballMatchView");
    }

    public void startPeriod() {
        Timer.startCountdown(Controller.getMatch().getPeriodSeconds(), this.timer);
    }

    private void setEventListeners() {
        PiController.controller1Up.addListener((GpioPinListenerDigital) event -> {
            if (event.getState().isHigh()) {
                Platform.runLater(() -> {
                    this.team1.setScore(this.team1.getScore() + 1);
                    this.team1.setScoreLabel(team1.getScore());
                });
            }
        });
        PiController.controller1Down.addListener((GpioPinListenerDigital) event -> {
            if (event.getState().isHigh() && team1.getScore() > 0) {
                Platform.runLater(() -> {
                    this.team1.setScore(this.team1.getScore() - 1);
                    this.team1.setScoreLabel(this.team1.getScore());
                });
            }
        });
        PiController.controller2Up.addListener((GpioPinListenerDigital) event -> {
            if (event.getState().isHigh()) {
                Platform.runLater(() -> {
                    this.team2.setScore(this.team2.getScore() + 1);
                    this.team2.setScoreLabel(this.team2.getScore());
                });
            }
        });
        PiController.controller2Down.addListener((GpioPinListenerDigital) event -> {
            if (event.getState().isHigh()  && this.team2.getScore() > 0) {
                Platform.runLater(() -> {
                    this.team2.setScore(this.team2.getScore() - 1);
                    this.team2.setScoreLabel(this.team2.getScore());
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
                        PiController.removeEventListeners();
                        Platform.runLater(Controller::openTeamSelect);
                    }
                    Controller.getView().setKeyPressTime(0);
                });
            }
        });
    }

    private void setKeyPressListeners(TeamView team1, TeamView team2) {
        Run.getScene().setOnKeyReleased(e -> {
            GameController.changeScore(e, team1, team2, true);
        });
    }
}

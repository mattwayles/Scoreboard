package com.advancedsportstechnologies.modules.games.trampolinevolleyball.view;

import com.advancedsportstechnologies.Run;
import com.advancedsportstechnologies.config.controller.Controller;
import com.advancedsportstechnologies.config.controller.PiController;
import com.advancedsportstechnologies.config.view.MainView;
import com.advancedsportstechnologies.modules.shared.view.ViewCreator;
import com.advancedsportstechnologies.modules.shared.controller.dual.GameController;
import com.advancedsportstechnologies.modules.shared.view.TeamView;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import javafx.application.Platform;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;

public class VolleyballMatchView extends MainView {


    private HBox view;
    private TeamView team1;
    private TeamView team2;


    public VolleyballMatchView(String team1Name, String team2Name) {
        this.team1 = new TeamView(team1Name);
        team1.setColor(Paint.valueOf("#0800ad"));
        this.team2 = new TeamView(team2Name);
        team2.setColor(Paint.valueOf("#a05500"));
        createVolleyballView();
        if (!Run.debug) {
            this.setEventListeners();
        } else {
            this.setKeyPressListeners(team1, team2);
        }
    }

    public HBox getView() { return view; }
    public TeamView getTeam1() { return this.team1; }
    public TeamView getTeam2() { return this.team2; }
    private void setTeam1(TeamView team) { this.team1 = team; }
    private void setTeam2(TeamView team) { this.team2 = team; }

    private void createVolleyballView() {
        ViewCreator vc = new ViewCreator();
        this.view = vc.createView(this.team1, this.team2);
        this.view.getStyleClass().add("volleyballMatchView");
    }

    private void reverseTeams() {
        ViewCreator vc = new ViewCreator();
        this.view = vc.createView(this.team1, this.team2);
        this.view.getStyleClass().add("volleyballMatchView");
        this.setKeyPressListeners(team1, team2);
    }

    public void resetScores() {
        this.team1.setScore(0);
        this.team2.setScore(0);
        this.team1.setScoreLabel(this.team1.getScore());
        this.team2.setScoreLabel(this.team2.getScore());
    }

    private void setEventListeners() {
        PiController.controller1Up.addListener((GpioPinListenerDigital) event -> increaseScore(event, this.team1, this.team2));
        PiController.controller1Down.addListener((GpioPinListenerDigital) event -> decreaseScore(event, team1));
        PiController.controller2Up.addListener((GpioPinListenerDigital) event -> increaseScore(event, this.team2, this.team1));
        PiController.controller2Down.addListener((GpioPinListenerDigital) event -> decreaseScore(event, team2));
        PiController.reset.addListener((GpioPinListenerDigital) this::reset);
    }

    private void increaseScore(GpioPinDigitalStateChangeEvent event, TeamView activeTeam, TeamView passiveTeam) {
        if (event.getState().isHigh()) {
            Platform.runLater(() -> {
                activeTeam.setScore(activeTeam.getScore() + 1);
                activeTeam.setScoreLabel(activeTeam.getScore());
                boolean winner = GameController.checkWinner(activeTeam, passiveTeam);
                if (winner) {
                    TeamView temp = team1;
                    this.team1 = this.team2;
                    this.team2 = temp;
                    this.reverseTeams();
                }
            });
        }
    }

    private void decreaseScore(GpioPinDigitalStateChangeEvent event, TeamView activeTeam) {
        if (event.getState().isHigh() && activeTeam.getScore() > 0) {
            Platform.runLater(() -> {
                activeTeam.setScore(activeTeam.getScore() - 1);
                activeTeam.setScoreLabel(activeTeam.getScore());
            });
        }
    }

    private void reset(GpioPinDigitalStateChangeEvent event) {
        if (event.getState().isLow()) {
            Controller.getView().setKeyPressTime(System.currentTimeMillis());
        }
        else {
            Platform.runLater(() -> {
                if (Controller.resetButtonHeld()) {
                    Platform.runLater(PiController::openTeamSelect);
                }
                Controller.getView().setKeyPressTime(0);
            });
        }
    }

    private void setKeyPressListeners(TeamView team1, TeamView team2) {
        Run.getScene().setOnKeyReleased(e -> {
            boolean winner = GameController.changeScore(e, team1, team2);
            if (winner) {
                this.setTeam1(team2);
                this.setTeam2(team1);
                this.reverseTeams();
            }
        });
    }
}

package com.advancedsportstechnologies.modules.games.cornhole.view;

import com.advancedsportstechnologies.Run;
import com.advancedsportstechnologies.config.controller.Controller;
import com.advancedsportstechnologies.config.controller.PiController;
import com.advancedsportstechnologies.config.view.MainView;
import com.advancedsportstechnologies.config.view.ViewCreator;
import com.advancedsportstechnologies.modules.shared.controller.dual.GameController;
import com.advancedsportstechnologies.modules.shared.view.TeamView;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
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

    private void setEventListeners(TeamView team1, TeamView team2) {
        PiController.controller1Up.addListener((GpioPinListenerDigital) event -> increaseScore(event, team1, team2));
        PiController.controller1Down.addListener((GpioPinListenerDigital) event -> decreaseScore(event, team1, team2));
        PiController.controller2Up.addListener((GpioPinListenerDigital) event -> increaseScore(event, team2, team1));
        PiController.controller2Down.addListener((GpioPinListenerDigital) event -> decreaseScore(event, team2, team1));
        PiController.reset.addListener((GpioPinListenerDigital) this::reset);
    }

    private void increaseScore(GpioPinDigitalStateChangeEvent event, TeamView activeTeam, TeamView passiveTeam) {
        if (event.getState().isHigh()) {
            Platform.runLater(() -> {
                activeTeam.setScore(activeTeam.getScore() + 1);
                activeTeam.setScoreLabel(activeTeam.getScore());
                GameController.checkWinner(activeTeam, passiveTeam);
            });
        }
    }

    private void decreaseScore(GpioPinDigitalStateChangeEvent event, TeamView activeTeam, TeamView passiveTeam) {
        if (event.getState().isHigh() && activeTeam.getScore() > 0) {
            Platform.runLater(() -> {
                activeTeam.setScore(activeTeam.getScore() - 1);
                activeTeam.setScoreLabel(activeTeam.getScore());
                GameController.checkWinner(activeTeam, passiveTeam);
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

    private static void setKeyPressListeners(TeamView team1, TeamView team2) {
        Run.getScene().setOnKeyReleased(e -> GameController.changeScore(e, team1, team2));
    }
}

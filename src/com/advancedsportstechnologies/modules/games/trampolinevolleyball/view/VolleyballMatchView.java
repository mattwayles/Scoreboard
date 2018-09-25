package com.advancedsportstechnologies.modules.games.trampolinevolleyball.view;

import com.advancedsportstechnologies.Run;
import com.advancedsportstechnologies.config.controller.Controller;
import com.advancedsportstechnologies.config.view.MainView;
import com.advancedsportstechnologies.config.view.MatchWinnerView;
import com.advancedsportstechnologies.config.view.ViewCreator;
import com.advancedsportstechnologies.modules.shared.controller.dual.EventListeners;
import com.advancedsportstechnologies.modules.shared.controller.dual.GameController;
import com.advancedsportstechnologies.modules.shared.view.TeamView;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.stage.Screen;

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
            EventListeners.setEventListeners(team1, team2);
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

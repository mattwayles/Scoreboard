package com.advancedsportstechnologies.modules.games.cornhole.view;

import com.advancedsportstechnologies.Run;
import com.advancedsportstechnologies.config.view.MainView;
import com.advancedsportstechnologies.modules.shared.controller.dual.EventListeners;
import com.advancedsportstechnologies.modules.shared.controller.dual.GameController;
import com.advancedsportstechnologies.modules.shared.view.TeamView;
import com.advancedsportstechnologies.config.view.ViewCreator;
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
            EventListeners.setEventListeners(team1, team2);
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

    private static void setKeyPressListeners(TeamView team1, TeamView team2) {
        Run.getScene().setOnKeyReleased(e -> GameController.changeScore(e, team1, team2));
    }
}

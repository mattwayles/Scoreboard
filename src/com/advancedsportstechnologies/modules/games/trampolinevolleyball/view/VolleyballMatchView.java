package com.advancedsportstechnologies.modules.games.trampolinevolleyball.view;

import com.advancedsportstechnologies.config.model.Match;
import com.advancedsportstechnologies.config.view.MainView;
import com.advancedsportstechnologies.config.view.ViewCreator;
import com.advancedsportstechnologies.modules.shared.view.TeamView;
import javafx.scene.layout.HBox;

public class VolleyballMatchView extends MainView {
    private static final String VOLLEYBALL_ID = "volleyball";


    private HBox view;
    private TeamView team1;
    private TeamView team2;


    public VolleyballMatchView(String team1Name, String team2Name) {
        this.team1 = new TeamView(team1Name);
        this.team2 = new TeamView(team2Name);
        createVolleyballView();
        this.setId(VOLLEYBALL_ID);
    }

    public HBox getView() { return view; }
    public TeamView getTeam1() { return this.team1; }
    public TeamView getTeam2() { return this.team2; }
    public void setTeam1(TeamView team) { this.team1 = team; }
    public void setTeam2(TeamView team) { this.team2 = team; }

    private void createVolleyballView() {
        ViewCreator vc = new ViewCreator();
        this.view = vc.createView(this.team1, this.team2);
        this.view.getStyleClass().add("volleyballMatchView");
    }

    public void reverseTeams() {
        ViewCreator vc = new ViewCreator();
        this.view = vc.createView(this.team1, this.team2);
        this.view.getStyleClass().add("volleyballMatchView");
    }

    public void resetScores() {
        this.team1.setScore(0);
        this.team2.setScore(0);
        this.team1.setScoreLabel(this.team1.getScore());
        this.team2.setScoreLabel(this.team2.getScore());
    }
}

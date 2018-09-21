package com.advancedsportstechnology.modules.games.trampolinevolleyball.view;

import com.advancedsportstechnology.config.model.Match;
import com.advancedsportstechnology.config.view.ViewCreator;
import com.advancedsportstechnology.modules.shared.view.TeamView;
import javafx.scene.layout.HBox;

public class VolleyballMatchView extends Match {
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

    private void createVolleyballView() {
        ViewCreator vc = new ViewCreator();
        this.view = vc.createView(this.team1, this.team2);
        this.view.getStyleClass().add("volleyballMatchView");
    }
}

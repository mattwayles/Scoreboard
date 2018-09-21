package com.advancedsportstechnology.modules.games.cornhole.view;

import com.advancedsportstechnology.config.model.Match;
import com.advancedsportstechnology.modules.shared.view.TeamView;
import com.advancedsportstechnology.config.view.ViewCreator;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;

public class CornholeMatchView extends Match {
    private static final String CORNHOLE_ID = "cornhole";
    private static final double HEIGHT = Screen.getPrimary().getVisualBounds().getHeight();
    private static final double WIDTH = Screen.getPrimary().getVisualBounds().getWidth();


    private HBox view;
    private TeamView team1;
    private TeamView team2;


    public CornholeMatchView(String team1Name, String team2Name) {
        this.team1 = new TeamView(team1Name);
        this.team2 = new TeamView(team2Name);
        createCornholeView();
        this.setId(CORNHOLE_ID);
    }

    public HBox getView() { return view; }

    private void createCornholeView() {
        ViewCreator vc = new ViewCreator();
        this.view = vc.createView(this.team1, this.team2);
    }

    public TeamView getTeam1() { return this.team1; }

    public TeamView getTeam2() { return this.team2; }
}

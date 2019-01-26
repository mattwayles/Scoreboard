package com.advancedsportstechnologies.view.untimed;

import com.advancedsportstechnologies.model.Team;
import com.advancedsportstechnologies.view.TeamView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Visual representation for a Team object on the scoreboard
 */
public class UntimedTeamView extends TeamView {

    public UntimedTeamView(Team team) {
        super(team);

        //Import ribbon displaying how many games this team has
        ImageView gamesWon = new ImageView(new Image(team.getGamesWon() == 0 ? "/img/placeholder.png" : "/img/gamesWon/gameWon" + team.getGamesWon() + ".png"));
        gamesWon.getStyleClass().add("gamesWon");


        super.getView().getChildren().add(1, gamesWon);
    }
}

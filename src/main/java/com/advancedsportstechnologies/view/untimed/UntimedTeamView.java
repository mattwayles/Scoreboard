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
        ImageView gamesWon = new ImageView("/img/placeholder.png");
        gamesWon.getStyleClass().add("gamesWon");

        super.getView().getChildren().add(1, gamesWon);
    }

    public void addRibbon() {
        ImageView gamesWon = (ImageView) super.getView().getChildren().get(1);
        gamesWon.setImage(new Image("/img/gamesWon/gameWon" + super.getTeam().getGamesWon() + ".png"));
    }
}

package com.advancedsportstechnologies;

import com.advancedsportstechnologies.model.Team;
import com.advancedsportstechnologies.view.GameWinnerView;
import javafx.scene.layout.StackPane;

public class Controller {
    public static void checkWinner(StackPane root, Team winningTeam, Team losingTeam, int gameNum, int winScore) {
        if (winningTeam.getScore() == winScore) {
            root.getChildren().clear();

            root.getChildren().add(new GameWinnerView(winningTeam, losingTeam, gameNum).getView());
        }
    }

}

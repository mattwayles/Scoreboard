package com.advancedsportstechnology.config.view;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import com.advancedsportstechnology.config.model.Match;

public class MatchWinnerView extends Match {
    private static final String MATCH_WINNER_ID = "matchWinner";
    private HBox view;

    public MatchWinnerView() {
        this.setId(MATCH_WINNER_ID);
    }

    public void displayMatchWinner(String teamName, int games, Paint color) {
        Label winner = new Label(teamName + " wins in " + games + " games!");
        winner.getStyleClass().add("winnerLabel");
        winner.setTextFill(color);
        Label notice = new Label("Press Start to reset (Q / W until controllers are working)");
        notice.getStyleClass().add("pressStartLabel");
        VBox message = new VBox(winner, notice);
        message.getStyleClass().add("winnerMessage");
        this.view = new HBox(message);
        this.view.getStyleClass().add("winnerView");
    }

    public HBox getView() { return view; }

    public void setView(HBox view) { this.view = view; }


}

package main.java.com.socialsportstechnology.config.view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import main.java.com.socialsportstechnology.config.model.Match;

public class MatchWinnerView extends Match {
    private static final String MATCH_WINNER_ID = "matchWinner";
    private HBox view;

    public MatchWinnerView() {
        this.setId(MATCH_WINNER_ID);
    }

    public void displayMatchWinner(String teamName, int games, Paint color) {
        //TODO: Move to CSS?
        Label winner = new Label(teamName + " wins in " + games + " games!");
        winner.getStyleClass().add("winnerLabel");
        winner.setTextFill(color);
        Label notice = new Label("Press Start to reset (Q / W until controllers are working)");
        notice.getStyleClass().add("pressStartLabel");
        VBox message = new VBox(winner, notice);
        message.setSpacing(100);
        message.setAlignment(Pos.CENTER);
        this.view = new HBox(message);
        view.setAlignment(Pos.CENTER);
    }

    public HBox getView() { return view; }

    public void setView(HBox view) { this.view = view; }


}
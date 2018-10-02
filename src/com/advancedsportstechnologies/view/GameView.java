package com.advancedsportstechnologies.view;

import com.advancedsportstechnologies.Controller;
import com.advancedsportstechnologies.model.Match;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class GameView {
    private HBox view;
    private TeamView teamView1;
    private TeamView teamView2;

    public GameView(StackPane root, Scene scene) {
        this.teamView1 = new TeamView(Match.getTeamOne());
        this.teamView2 = new TeamView(Match.getTeamTwo());
        this.view = new HBox(teamView1.getView(), teamView2.getView());
        //PiController.setDebounce();
        //this.setEventListeners();
        this.setKeyPressListeners(root, scene);
    }
    public HBox getView() { return this.view; }

    private void setKeyPressListeners(StackPane root, Scene scene) {
        scene.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.A) {
                Match.getTeamOne().increaseScore();
                this.teamView1.getScoreLabel().textProperty().setValue(String.valueOf(Match.getTeamOne().getScore()));
                Controller.checkWinner(root, scene);
            } else if (e.getCode() == KeyCode.S) {
                Match.getTeamTwo().increaseScore();
                this.teamView2.getScoreLabel().textProperty().setValue(String.valueOf(Match.getTeamTwo().getScore()));
                Controller.checkWinner(root, scene);
            } else if (e.getCode() == KeyCode.Z && Match.getTeamOne().getScore() > 0) {
                Match.getTeamOne().decreaseScore();
                this.teamView1.getScoreLabel().textProperty().setValue(String.valueOf(Match.getTeamOne().getScore()));
            } else if (e.getCode() == KeyCode.X && Match.getTeamTwo().getScore() > 0) {
                Match.getTeamTwo().decreaseScore();
                this.teamView2.getScoreLabel().textProperty().setValue(String.valueOf(Match.getTeamTwo().getScore()));
            } else if (e.getCode() == KeyCode.Q) {
                Match.start(root, scene);
            }
        });
    }
}

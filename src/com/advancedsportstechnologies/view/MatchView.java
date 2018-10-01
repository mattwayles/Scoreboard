package com.advancedsportstechnologies.view;

import com.advancedsportstechnologies.Controller;
import com.advancedsportstechnologies.model.Match;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class MatchView extends Match {
    private HBox view;
    private TeamView teamView1;
    private TeamView teamView2;

    public MatchView(StackPane root, Scene scene) {
        this.teamView1 = new TeamView(super.getTeamOne());
        this.teamView2 = new TeamView(super.getTeamTwo());
        this.view = new HBox(teamView1.getView(), teamView2.getView());
        //PiController.setDebounce();
        //this.setEventListeners();
        this.setKeyPressListeners(root, scene);
    }
    public HBox getView() { return this.view; }

    private void setKeyPressListeners(StackPane root, Scene scene) {
        scene.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.A) {
                super.getTeamOne().increaseScore();
                this.teamView1.getScoreLabel().textProperty().setValue(String.valueOf(super.getTeamOne().getScore()));
                Controller.checkWinner(root, super.getTeamOne(), super.getTeamTwo(), super.getCurrentGame(), super.getCurrentGameScore());
            } else if (e.getCode() == KeyCode.S) {
                super.getTeamTwo().increaseScore();
                this.teamView2.getScoreLabel().textProperty().setValue(String.valueOf(super.getTeamTwo().getScore()));
                Controller.checkWinner(root, super.getTeamTwo(), super.getTeamOne(), super.getCurrentGame(), super.getCurrentGameScore());
            } else if (e.getCode() == KeyCode.Z && super.getTeamOne().getScore() > 0) {
                super.getTeamOne().decreaseScore();
                this.teamView1.getScoreLabel().textProperty().setValue(String.valueOf(super.getTeamOne().getScore()));
            } else if (e.getCode() == KeyCode.X && super.getTeamTwo().getScore() > 0) {
                super.getTeamTwo().decreaseScore();
                this.teamView2.getScoreLabel().textProperty().setValue(String.valueOf(super.getTeamTwo().getScore()));
            } else if (e.getCode() == KeyCode.Q) {
                root.getChildren().clear();
                root.getChildren().add(new MatchView(root, scene).getView());
            }
        });
    }
}

package com.advancedsportstechnologies.view;

import com.advancedsportstechnologies.controller.Controller;
import com.advancedsportstechnologies.Main;
import com.advancedsportstechnologies.controller.PiController;
import com.advancedsportstechnologies.model.Match;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class GameView {
    private HBox view;
    private TeamView teamView1;
    private TeamView teamView2;

    public GameView() {
        this.teamView1 = new TeamView(Match.getTeamOne());
        this.teamView2 = new TeamView(Match.getTeamTwo());
        VBox topSeparator = createSeparator(Main.HEIGHT / 3);
        topSeparator.getStyleClass().add("separator");
        VBox bottomSeparator = createSeparator(Main.HEIGHT);
        bottomSeparator.getStyleClass().add("separator");
        ImageView logo = new ImageView(new Image("img/astLogo.png"));
        Label version = new Label(Main.VERSION);
        version.getStyleClass().add("version");
        VBox logoBox = new VBox(logo, version);
        logoBox.getStyleClass().add("logoBox");
        VBox separator = new VBox(topSeparator, logoBox, bottomSeparator);
        separator.getStyleClass().add("center");
        this.view = new HBox(teamView1.getView(), separator, teamView2.getView());
        this.view.getStyleClass().add("gameView");
        this.view.setMaxHeight(Main.HEIGHT);
        this.view.setSpacing(Main.HEIGHT / 7);
        this.setKeyPressListeners();


        if (!Main.debug) {
            PiController.setDebounce();
            this.setEventListeners();
        }

    }
    public HBox getView() { return this.view; }

    private VBox createSeparator(double height) {
        VBox separator = new VBox();
        separator.setMinHeight(height);
        separator.getStyleClass().add("separator");
        return separator;
    }

    private void setKeyPressListeners() {
       Main.getScene().setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.A) {
                Match.getTeamOne().increaseScore();
                this.teamView1.getScoreLabel().textProperty().setValue(String.valueOf(Match.getTeamOne().getScore()));
                Controller.checkWinner(this.teamView1, this.teamView2);
            } else if (e.getCode() == KeyCode.S) {
                Match.getTeamTwo().increaseScore();
                this.teamView2.getScoreLabel().textProperty().setValue(String.valueOf(Match.getTeamTwo().getScore()));
                Controller.checkWinner(this.teamView2, this.teamView1);
            } else if (e.getCode() == KeyCode.Z && Match.getTeamOne().getScore() > 0) {
                Match.getTeamOne().decreaseScore();
                this.teamView1.getScoreLabel().textProperty().setValue(String.valueOf(Match.getTeamOne().getScore()));
            } else if (e.getCode() == KeyCode.X && Match.getTeamTwo().getScore() > 0) {
                Match.getTeamTwo().decreaseScore();
                this.teamView2.getScoreLabel().textProperty().setValue(String.valueOf(Match.getTeamTwo().getScore()));
            } else if (e.getCode() == KeyCode.Q) {
                Match.start();
            }
        });
    }
}

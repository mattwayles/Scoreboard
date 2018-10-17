package com.advancedsportstechnologies.view;

import com.advancedsportstechnologies.controller.Controller;
import com.advancedsportstechnologies.Main;
import com.advancedsportstechnologies.controller.PiController;
import com.advancedsportstechnologies.model.Match;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import javafx.application.Platform;
import javafx.scene.Node;
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
        this.view = new HBox(teamView1.getView(), createSeparator(), teamView2.getView());
        this.view.setMaxHeight(Main.HEIGHT);
        this.setKeyPressListeners();

        if (!Main.debug) {
            PiController.removeEventListeners();
            this.setEventListeners();
        }

    }
    public HBox getView() { return this.view; }

    public void reverseTeams() {
        Match.reverseTeams();
        this.teamView1 = new TeamView(Match.getTeamOne());
        this.teamView2 = new TeamView(Match.getTeamTwo());

        this.view = new HBox(teamView1.getView(), createSeparator(), teamView2.getView());
    }

    private VBox createSeparator() {
        VBox topSeparator = createMiddleLine();
        VBox bottomSeparator = createMiddleLine();
        VBox logoBox = createLogoBox();
        VBox gameBox = createGameBox();
        VBox scoreToWinBox = createScoreToWinBox();
        VBox gamesToWinBox = createGamesToWin();

        VBox infoBox;
        if (Match.isConnected()) {
            ImageView connected = new ImageView(new Image("/img/bt.png"));
            infoBox = new VBox(50, logoBox, gameBox, scoreToWinBox, gamesToWinBox, connected);
            infoBox.getStyleClass().add("infoBox");
        } else {
            infoBox = new VBox(50, logoBox, gameBox, scoreToWinBox, gamesToWinBox);
            infoBox.getStyleClass().add("infoBox");
        }

            //Put it all together
            VBox separator = new VBox(topSeparator, infoBox, bottomSeparator);
            separator.getStyleClass().add("center");

        return separator;
    }


    private VBox createMiddleLine() {
        VBox separator = new VBox();
        separator.setMinHeight(Main.HEIGHT / 3);
        separator.getStyleClass().add("separator");
        return separator;
    }

    private VBox createLogoBox() {
        ImageView logo = new ImageView(new Image("img/astLogo.png"));
        Label version = new Label(Main.VERSION);
        version.getStyleClass().add("smallerText");
        VBox logoBox = new VBox(10, logo, version);
        logoBox.getStyleClass().add("center");

        return logoBox;
    }

    private VBox createGameBox() {
        Label game = new Label("Game");
        game.getStyleClass().add("middleText");
        Label currentGameNum = new Label(String.valueOf(Match.getCurrentGame() + 1));
        currentGameNum.getStyleClass().add("gameStr");
        Label of = new Label(" of ");
        of.getStyleClass().addAll("smallerText", "topPadding");
        Label maxGameNum = new Label(String.valueOf(Match.getMaxGames()));
        maxGameNum.getStyleClass().add("gameStr");
        HBox gameNumBox = new HBox(5, currentGameNum, of, maxGameNum);
        gameNumBox.getStyleClass().add("center");
        VBox gameBox = new VBox(game, gameNumBox);
        gameBox.getStyleClass().add("center");

        return gameBox;
    }

    private VBox createScoreToWinBox() {
        Label scoreToWin = new Label("Score to Win");
        scoreToWin.getStyleClass().addAll("middleText", "smallerLabel");

        Node scoreToWinVal;
        if (Match.getCurrentGameScore() > 0) {
            scoreToWinVal = new Label(String.valueOf(Match.getCurrentGameScore()));
        }
        else {
            scoreToWin.getStyleClass().add("bottomPadding");
            scoreToWinVal = new ImageView(new Image("/img/infinity.png"));
        }
        scoreToWinVal.getStyleClass().add("gameStr");
        VBox scoreToWinBox = new VBox(scoreToWin, scoreToWinVal);
        scoreToWinBox.getStyleClass().add("center");

        return scoreToWinBox;
    }

    private VBox createGamesToWin() {
        Label gamesToWin = new Label("Games to Win");
        gamesToWin.getStyleClass().addAll("middleText", "smallerLabel");
        Node gamesToWinVal;
        if (Match.getGamesToWin() > 0) {
            gamesToWinVal = new Label(String.valueOf(Match.getGamesToWin()));
        }
        else {
            gamesToWin.getStyleClass().add("bottomPadding");
            gamesToWinVal = new ImageView(new Image("/img/infinity.png"));
        }
        gamesToWinVal.getStyleClass().add("gameStr");
        VBox gamesToWinBox = new VBox(gamesToWin, gamesToWinVal);
        gamesToWinBox.getStyleClass().add( "center");

        return gamesToWinBox;
    }


    private void setEventListeners() {
        PiController.controller1Up.addListener((GpioPinListenerDigital) event -> {
            if (event.getState().isHigh()) {
                Platform.runLater(() -> {
                    Match.getTeamOne().increaseScore();
                    this.teamView1.getScoreLabel().textProperty().setValue(String.valueOf(Match.getTeamOne().getScore()));
                    Controller.checkWinner(this.teamView1, this.teamView2);
                });
            }
        });
        PiController.controller1Down.addListener((GpioPinListenerDigital) event -> {
            if (event.getState().isHigh() && Match.getTeamOne().getScore() > 0) {
                Platform.runLater(() -> {
                    Match.getTeamOne().decreaseScore();
                    this.teamView1.getScoreLabel().textProperty().setValue(String.valueOf(Match.getTeamOne().getScore()));
                });
            }
        });
        PiController.controller2Up.addListener((GpioPinListenerDigital) event -> {
            if (event.getState().isHigh()) {
                Platform.runLater(() -> {
                    Match.getTeamTwo().increaseScore();
                    this.teamView2.getScoreLabel().textProperty().setValue(String.valueOf(Match.getTeamTwo().getScore()));
                    Controller.checkWinner(this.teamView2, this.teamView1);
                });
            }
        });
        PiController.controller2Down.addListener((GpioPinListenerDigital) event -> {
            if (event.getState().isHigh() && Match.getTeamTwo().getScore() > 0) {
                Platform.runLater(() -> {
                    Match.getTeamTwo().decreaseScore();
                    this.teamView2.getScoreLabel().textProperty().setValue(String.valueOf(Match.getTeamTwo().getScore()));
                });
            }
        });
        PiController.reset.addListener((GpioPinListenerDigital) event -> reset());
    }

    private void reset() {
        Platform.runLater(() -> {
            Controller.restartScoreboard();
            Match.startOrRefresh();
        });
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
                Controller.restartScoreboard();
                Match.startOrRefresh();
            }
        });
    }
}

package com.advancedsportstechnologies.view;

import com.advancedsportstechnologies.controller.Controller;
import com.advancedsportstechnologies.Main;
import com.advancedsportstechnologies.controller.PiController;
import com.advancedsportstechnologies.model.Match;
import com.advancedsportstechnologies.model.Team;
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
import javafx.scene.paint.Paint;

public class GameView {
    private HBox view;
    private TeamView teamView1;
    private TeamView teamView2;

    public GameView() {
        this.teamView1 = new TeamView(Match.getTeamOne());
        this.teamView2 = new TeamView(Match.getTeamTwo());
        this.view = new HBox(teamView1.getView(), createSeparator(), teamView2.getView());
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

    public void reverseTeams() {
        Match.reverseTeams();
        this.teamView1 = new TeamView(Match.getTeamOne());
        this.teamView2 = new TeamView(Match.getTeamTwo());

        this.view = new HBox(teamView1.getView(), createSeparator(), teamView2.getView());
    }

    private VBox createSeparator() {

        //Middle line
        VBox topSeparator = createSeparator(Main.HEIGHT / 3);
        topSeparator.getStyleClass().add("separator");
        VBox bottomSeparator = createSeparator(Main.HEIGHT / 3);
        bottomSeparator.getStyleClass().add("separator");

        //Logo box
        ImageView logo = new ImageView(new Image("img/astLogo.png"));
        Label version = new Label("Scoreboard " + Main.VERSION);
        version.getStyleClass().add("smallerText");
        VBox logoBox = new VBox(logo, version);
        logoBox.getStyleClass().add("center");

        //GameBox
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

        //ScoreToWinBox
        Label scoreToWin = new Label("Score to Win");
        scoreToWin.getStyleClass().addAll("middleText", "smallerLabel");

        Node scoreToWinVal;
        if (Match.getCurrentGameScore() > 0) {
            scoreToWinVal = new Label(String.valueOf(Match.getCurrentGameScore()));
        }
        else {
            scoreToWinVal = new ImageView(new Image("/img/infinity.png"));
            scoreToWinVal.getStyleClass().add("topMargin");
        }
        scoreToWinVal.getStyleClass().add("gameStr");
        VBox scoreToWinBox = new VBox(scoreToWin, scoreToWinVal);
        scoreToWinBox.getStyleClass().add("center");

        //GamesToWinBox
        Label gamesToWin = new Label("Games to Win");
        gamesToWin.getStyleClass().addAll("middleText", "smallerLabel");
        Node gamesToWinVal;
        if (Match.getGamesToWin() > 0) {
            gamesToWinVal = new Label(String.valueOf(Match.getCurrentGameScore()));
        }
        else {
            gamesToWinVal = new ImageView(new Image("/img/infinity.png"));
        }
        gamesToWinVal.getStyleClass().add("gameStr");
        VBox gamesToWinBox = new VBox(gamesToWin, gamesToWinVal);
        gamesToWinBox.getStyleClass().add( "center");

        //InfoBox
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
            separator.getStyleClass().add("padding");

        return separator;
    }


    private VBox createSeparator(double height) {
        VBox separator = new VBox();
        separator.setMinHeight(height);
        separator.getStyleClass().add("separator");
        return separator;
    }


    private void setEventListeners() {
        PiController.controller1Up.addListener((GpioPinListenerDigital) event -> increaseScore(event, this.teamView1, this.teamView2));
        PiController.controller1Down.addListener((GpioPinListenerDigital) event -> decreaseScore(event, teamView1));
        PiController.controller2Up.addListener((GpioPinListenerDigital) event -> increaseScore(event, this.teamView2, this.teamView1));
        PiController.controller2Down.addListener((GpioPinListenerDigital) event -> decreaseScore(event, teamView2));
        PiController.reset.addListener((GpioPinListenerDigital) this::reset);
    }

    private void increaseScore(GpioPinDigitalStateChangeEvent event, TeamView activeTeam, TeamView passiveTeam) {
        if (event.getState().isHigh()) {
            Platform.runLater(() -> {
                activeTeam.getTeam().setScore(activeTeam.getTeam().getScore() + 1);
                activeTeam.setScoreLabel(activeTeam.getTeam().getScore());
                boolean winner = Controller.checkWinner(activeTeam, passiveTeam);
            });
        }
    }

    private void decreaseScore(GpioPinDigitalStateChangeEvent event, TeamView activeTeam) {
        if (event.getState().isHigh() && activeTeam.getTeam().getScore() > 0) {
            Platform.runLater(() -> {
                activeTeam.getTeam().setScore(activeTeam.getTeam().getScore() - 1);
                activeTeam.setScoreLabel(activeTeam.getTeam().getScore());
            });
        }
    }

    private void reset(GpioPinDigitalStateChangeEvent event) {
        Platform.runLater(Match::startOrRefresh);
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

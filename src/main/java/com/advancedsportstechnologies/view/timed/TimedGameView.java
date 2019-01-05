package com.advancedsportstechnologies.view.timed;

import com.advancedsportstechnologies.Main;
import com.advancedsportstechnologies.controller.Controller;
import com.advancedsportstechnologies.controller.PiController;
import com.advancedsportstechnologies.controller.Timer;
import com.advancedsportstechnologies.model.Match;
import com.advancedsportstechnologies.model.Team;
import com.advancedsportstechnologies.view.texteffects.Blink;
import com.advancedsportstechnologies.view.untimed.UntimedTeamView;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Scoreboard visual representation of a game. Contains two TeamViews separated by a middle line containing an informational panel
 */



//TODO:
    //Create TimedTeamView, removing the "games won" ribbons
    //Add separator with logo
    //Create space between timer and top of scoreboard
    //Buzzer on zero
    //App input


public class TimedGameView {
    private VBox view;
    private TimedTeamView teamView1;
    private TimedTeamView teamView2;

    private final int MAX_SCORE = 99;
    private final int MIN_SCORE = 0;

    /**
     * Create a new visual representation of a Game
     */
    public TimedGameView() {
        //Create visual components from team objects
        this.teamView1 = new TimedTeamView(Match.getTeamOne());
        this.teamView2 = new TimedTeamView(Match.getTeamTwo());
        HBox teamBox = new HBox(teamView1.getView(), teamView2.getView());
        this.view = new VBox(createTimer(), teamBox);
        this.view.setMaxHeight(Main.HEIGHT);

        //Set listeners for keyboard/Pi
        this.setKeyPressListeners();
        if (!Main.debug) {
            PiController.removeEventListeners();
            this.setEventListeners();
        }

    }

    /**
     * Retrieve the View for this object
     * @return  The UntimedGameView's fully-configured View
     */
    public VBox getView() { return this.view; }

    /**
     * When scoreboard type is 'switch', the visual representation of the teams must switch sides after each game
     */
    public void reverseTeams() {
        Match.reverseTeams();
        this.teamView1 = new TimedTeamView(Match.getTeamOne());
        this.teamView2 = new TimedTeamView(Match.getTeamTwo());

        this.view = new VBox(createTimer(), teamView1.getView(), teamView2.getView());
    }

    private HBox createTimer() {

        //Period
        Label periodLabel = new Label("Q1");
        periodLabel.setPrefWidth(Main.WIDTH / 3.5);
        periodLabel.setFont(new Font(periodLabel.getFont().getName(), Main.WIDTH / 12));
        periodLabel.getStyleClass().add("periodLabel");

        //Timer
        Label timerLabel = new Label();
        timerLabel.setPrefWidth(Main.WIDTH / 1.5);
        timerLabel.setFont(new Font(timerLabel.getFont().getName(), Main.WIDTH / 6.5));
        timerLabel.getStyleClass().add("timerLabel");

        //Icons
        HBox iconBox = new HBox(30);
        iconBox.setPrefWidth(Main.WIDTH / 3.5);
        iconBox.getStyleClass().add("iconBox");

        //TODO: Remove
        //if (Match.isConnected()) {
            ImageView connected = new ImageView(new Image("/img/bluetooth/bt_" + Match.getTheme() + ".png"));
            connected.getStyleClass().add("bt_icon");
            iconBox.getChildren().add(connected);
        //}

        //if (Match.getType().equals("switch")) {
            ImageView switchIcon = new ImageView(new Image("/img/switch/switch_" + Match.getTheme() + ".png"));
            iconBox.getChildren().add(switchIcon);
            switchIcon.getStyleClass().add("switch_icon");
       // }

        Timer.startCountdown(3600, timerLabel);




        //TimerBox
        HBox timerBox = new HBox(80, periodLabel, timerLabel, iconBox);
        timerBox.getStyleClass().add("timerBox");

        return timerBox;
    }

    /**
     * Sent event listeners for Pi button presses
     */
    private void setEventListeners() {
        PiController.controller1Up.addListener((GpioPinListenerDigital) event -> {
            if (event.getState().isHigh()) {
                Platform.runLater(() -> increaseScore(Match.getTeamOne(), this.teamView1, this.teamView2));
            }
        });
        PiController.controller1Down.addListener((GpioPinListenerDigital) event -> {
            if (event.getState().isHigh()) {
                Platform.runLater(() -> decreaseScore(Match.getTeamOne(), this.teamView1));
            }
        });
        PiController.controller2Up.addListener((GpioPinListenerDigital) event -> {
            if (event.getState().isHigh()) {
                Platform.runLater(() -> increaseScore(Match.getTeamTwo(), this.teamView2, this.teamView1));
            }
        });
        PiController.controller2Down.addListener((GpioPinListenerDigital) event -> {
            if (event.getState().isHigh()) {
                Platform.runLater(() -> decreaseScore(Match.getTeamTwo(), this.teamView2));
            }
        });
        PiController.reset.addListener((GpioPinListenerDigital) event -> reset());
    }

    /**
     * Reset the scoreboard if Pi Controller 'reset' button pressed
     */
    private void reset() {
        Platform.runLater(() -> {
            if (this.teamView1.getTeam().getScore() == 0 && this.teamView2.getTeam().getScore() == 0) {
                Match.reverseTeams();
            }
            Controller.restartScoreboard();
            Match.startOrRefresh();
        });
    }

    /**
     * Set listeners for keyboard button presses
     */
    private void setKeyPressListeners() {
        Main.getScene().setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.A) {
                increaseScore(Match.getTeamOne(), this.teamView1, this.teamView2);
            } else if (e.getCode() == KeyCode.S) {
                increaseScore(Match.getTeamTwo(), this.teamView2, this.teamView1);
            } else if (e.getCode() == KeyCode.Z) {
                decreaseScore(Match.getTeamOne(), this.teamView1);
            } else if (e.getCode() == KeyCode.X) {
                decreaseScore(Match.getTeamTwo(), this.teamView2);
            } else if (e.getCode() == KeyCode.Q) {
                if (this.teamView1.getTeam().getScore() == 0 && this.teamView2.getTeam().getScore() == 0) {
                    Match.reverseTeams();
                }
                Controller.restartScoreboard();
                Match.startOrRefresh();
            }
        });
    }

    /**
     * Increase the score of the specified team when the increase score event is raised
     * @param activeTeam    The team receiving the score
     * @param activeTeamView    The View representing the team receiving the score
     * @param passiveTeamView   The View representing the team that did not receive the score
     */
    private void increaseScore(Team activeTeam, TimedTeamView activeTeamView, TimedTeamView passiveTeamView) {
        if (activeTeam.getScore() < MAX_SCORE) {
            activeTeam.increaseScore();
            updateScoreNode(activeTeam, activeTeamView);

            //TODO: FIX
            //Controller.checkWinner(activeTeamView, passiveTeamView);
        }
    }

    /**
     * Decrease the score of the specified team when the decrease score event is raised
     * @param activeTeam    The team receiving the score
     * @param activeTeamView    The View representing the team receiving the score
     */
    private void decreaseScore(Team activeTeam, TimedTeamView activeTeamView) {
        if (activeTeam.getScore() > MIN_SCORE) {
            activeTeam.decreaseScore();
            updateScoreNode(activeTeam, activeTeamView);
            Blink.stop(activeTeamView.getScoreLabel());
        }
    }

    private void updateScoreNode(Team activeTeam, TimedTeamView activeTeamView) {
        if (activeTeamView.getScoreLabel() instanceof Label) {
            Label label = (Label) activeTeamView.getScoreLabel();
            label.textProperty().setValue(String.valueOf(activeTeam.getScore()));
        } else {
            Text text = (Text) activeTeamView.getScoreLabel();
            text.setText(String.valueOf(activeTeam.getScore()));
        }
    }
}

package com.advancedsportstechnologies.view;

import com.advancedsportstechnologies.Main;
import com.advancedsportstechnologies.controller.Controller;
import com.advancedsportstechnologies.controller.PiController;
import com.advancedsportstechnologies.model.Match;
import com.advancedsportstechnologies.model.Team;
import com.advancedsportstechnologies.view.texteffects.Blink;
import com.advancedsportstechnologies.view.texteffects.Rotate;
import com.advancedsportstechnologies.view.texteffects.Scale;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Abstract GameView class consolidating code between children items
 */
public abstract class GameView {
    private Node view;
    private TeamView teamView1;
    private TeamView teamView2;

    private final int MAX_SCORE = 99;
    private final int MIN_SCORE = 0;
    private final double VERSION_LABEL_SIZE = Main.WIDTH / 130;

    /**
     * Create a new visual representation of a Game
     */
    protected GameView() {
        //Create visual components from team objects
        this.teamView1 = new TeamView(Match.getTeamOne());
        this.teamView2 = new TeamView(Match.getTeamTwo());
        this.view = new HBox(teamView1.getView(), teamView2.getView());

        //Set listeners for keyboard/Pi
        this.setKeyPressListeners();
        if (!Main.debug) {
            PiController.removeEventListeners();
            this.setEventListeners();
        }
    }

    /**
     * Retrieve the View for this object
     * @return  The GameView's fully-configured View
     */
    public Node getView() { return this.view; }

    /**
     * Set the View for this object
     * @param view The GameView's new View
     */
    public void setView(Node view) { this.view = view; }

    /**
     * When scoreboard type is 'switch', the visual representation of the teams must switch sides after each game
     */
    public void reverseTeams() {
        Match.reverseTeams();
        this.teamView1 = new TeamView(Match.getTeamOne());
        this.teamView2 = new TeamView(Match.getTeamTwo());

        this.view = new HBox(teamView1.getView(), teamView2.getView());
    }

    /**
     * Create small middle lines that sit on top/bottom of the informational panel
     * @return  A middle line View
     */
    protected VBox createMiddleLine() {
        VBox separator = new VBox();
        separator.setMinHeight(Main.HEIGHT / 2);
        separator.getStyleClass().add("separator");
        return separator;
    }


    /**
     * Create a View that contains the Logo and scoreboard version
     * @return  A View that contains the Logo and scoreboard version
     */
    protected VBox createLogoBox() {
        //Logo
        ImageView logo = new ImageView(new Image("img/logo/astLogo_" + Match.getTheme() + ".png"));
        Rotate.play(logo);

        //Version
        Label version = new Label(Main.VERSION);
        version.getStyleClass().add("middleText");
        version.setFont(new Font(version.getFont().getName(), VERSION_LABEL_SIZE));

        //Box containing logo & version
        VBox logoBox = new VBox(10, logo, version);
        logoBox.getStyleClass().add("center");

        return logoBox;
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
            //TODO: Fix
            //Match.startOrRefresh();
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
                //TODO: Fix
                //Match.startOrRefresh();
            }
        });
    }

    /**
     * Increase the score of the specified team when the increase score event is raised
     * @param activeTeam    The team receiving the score
     * @param activeTeamView    The View representing the team receiving the score
     * @param passiveTeamView   The View representing the team that did not receive the score
     */
    private void increaseScore(Team activeTeam, TeamView activeTeamView, TeamView passiveTeamView) {
        if (activeTeam.getScore() < MAX_SCORE) {
            activeTeam.increaseScore();
            updateScoreNode(activeTeam, activeTeamView);
            Controller.checkWinner(activeTeamView, passiveTeamView);
            Match.refresh();
        }
    }

    /**
     * Decrease the score of the specified team when the decrease score event is raised
     * @param activeTeam    The team receiving the score
     * @param activeTeamView    The View representing the team receiving the score
     */
    private void decreaseScore(Team activeTeam, TeamView activeTeamView) {
        if (activeTeam.getScore() > MIN_SCORE) {
            activeTeam.decreaseScore();
            updateScoreNode(activeTeam, activeTeamView);
            Blink.stop(activeTeamView.getScoreLabel());
        }
    }

    private void updateScoreNode(Team activeTeam, TeamView activeTeamView) {
        if (activeTeamView.getScoreLabel() instanceof Label) {
            Label label = (Label) activeTeamView.getScoreLabel();
            label.textProperty().setValue(String.valueOf(activeTeam.getScore()));
        } else {
            Text text = (Text) activeTeamView.getScoreLabel();
            text.setText(String.valueOf(activeTeam.getScore()));
        }
        Scale.play(activeTeamView.getScoreLabel(), 200, .1, .1);
    }

}

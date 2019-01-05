package com.advancedsportstechnologies.view;

import com.advancedsportstechnologies.controller.Controller;
import com.advancedsportstechnologies.Main;
import com.advancedsportstechnologies.controller.PiController;
import com.advancedsportstechnologies.model.Match;
import com.advancedsportstechnologies.model.Team;
import com.advancedsportstechnologies.view.texteffects.Blink;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * Scoreboard visual representation of a game. Contains two TeamViews separated by a middle line containing an informational panel
 */
public class GameView {
    private HBox view;
    private TeamView teamView1;
    private TeamView teamView2;

    private final int MAX_SCORE = 99;
    private final int MIN_SCORE = 0;

    /**
     * Create a new visual representation of a Game
     */
    public GameView() {
        //Create visual components from team objects
        this.teamView1 = new TeamView(Match.getTeamOne());
        this.teamView2 = new TeamView(Match.getTeamTwo());
        this.view = new HBox(teamView1.getView(), createSeparator(), teamView2.getView());
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
     * @return  The GameView's fully-configured View
     */
    public HBox getView() { return this.view; }

    /**
     * When scoreboard type is 'switch', the visual representation of the teams must switch sides after each game
     */
    public void reverseTeams() {
        Match.reverseTeams();
        this.teamView1 = new TeamView(Match.getTeamOne());
        this.teamView2 = new TeamView(Match.getTeamTwo());

        this.view = new HBox(teamView1.getView(), createSeparator(), teamView2.getView());
    }

    /**
     * Create a middle Team separator and include informational panel
     * @return  Separator Node element
     */
    private VBox createSeparator() {
        //Top & Bottom lines
        VBox topSeparator = createMiddleLine();
        VBox bottomSeparator = createMiddleLine();

        //Logo and version
        VBox logoBox = createLogoBox();

        //Game x of x
        VBox gameBox = createGameBox();

        //Score needed to win game
        VBox scoreToWinBox = createScoreToWinBox();

        //Games needed to win match
        VBox gamesToWinBox = createGamesToWin();

        //Conditional icons
        VBox infoBox = new VBox(15, logoBox, gameBox, scoreToWinBox, gamesToWinBox);
        infoBox.getStyleClass().add("infoBox");
        addToInfoBox(infoBox);

        //Put it all together
        VBox separator = new VBox(topSeparator, infoBox, bottomSeparator);
        separator.getStyleClass().add("center");

        return separator;
    }

    /**
     * Create small middle lines that sit on top/bottom of the informational panel
     * @return  A middle line View
     */
    private VBox createMiddleLine() {
        VBox separator = new VBox();
        separator.setMinHeight(Main.HEIGHT / 3);
        separator.getStyleClass().add("separator");
        return separator;
    }

    /**
     * Create a View that contains the Logo and scoreboard version
     * @return  A View that contains the Logo and scoreboard version
     */
    private VBox createLogoBox() {
        //Logo
        ImageView logo = new ImageView(new Image("img/logo/astLogo_" + Match.getTheme() + ".png"));

        //Version
        Label version = new Label(Main.VERSION);
        version.getStyleClass().add("smallerText");

        //Box containing logo & version
        VBox logoBox = new VBox(10, logo, version);
        logoBox.getStyleClass().add("center");

        return logoBox;
    }

    /**
     * Create a View that contains 'Game x of x'
     * @return a View that contains 'Game x of x'
     */
    private VBox createGameBox() {
        //'Game' label
        Label game = new Label("Game");
        game.getStyleClass().add("middleText");

        //Current game number
        Label currentGameNum = new Label(String.valueOf(Match.getCurrentGame() + 1));
        currentGameNum.getStyleClass().add("gameStr");

        //'of' label
        Label of = new Label(" of ");
        of.getStyleClass().addAll("smallerText", "topPadding");

        //Max game number
        Label maxGameNum = new Label(String.valueOf(Match.getMaxGames()));
        maxGameNum.getStyleClass().add("gameStr");

        //Box containing current game number, 'of', max game number
        HBox gameNumBox = new HBox(5, currentGameNum, of, maxGameNum);
        gameNumBox.getStyleClass().add("center");

        //Box containing Game + X of X
        VBox gameBox = new VBox(game, gameNumBox);
        gameBox.getStyleClass().add("center");

        return gameBox;
    }

    /**
     * Create a View that contains 'Score To Win = x'
     * @return ScoreToWin Node element
     */
    private VBox createScoreToWinBox() {
        //'Score to Win' label
        Label scoreToWin = new Label("Score to Win");
        scoreToWin.getStyleClass().addAll("middleText", "smallerLabel");

        //Score to win value
        Node scoreToWinVal;
        if (Match.getCurrentGameWinScore() > 0) {
            scoreToWinVal = new Label(String.valueOf(Match.getCurrentGameWinScore()));
        }
        else {
            scoreToWin.getStyleClass().add("bottomPadding");
            scoreToWinVal = new ImageView(new Image("/img/infinity/infinity_" + Match.getTheme() + ".png"));
        }
        scoreToWinVal.getStyleClass().add("gameStr");

        //Box containing 'Score to Win' + score to win value
        VBox scoreToWinBox = new VBox(scoreToWin, scoreToWinVal);
        scoreToWinBox.getStyleClass().add("center");

        return scoreToWinBox;
    }

    /**
     * Create a View that contains 'Games to Win = X'
     * @return GamesToWin Node element
     */
    private VBox createGamesToWin() {
        //'Games to win' label
        Label gamesToWin = new Label("Games to Win");
        gamesToWin.getStyleClass().addAll("middleText", "smallerLabel");

        //Games to win value
        Node gamesToWinVal;
        if (Match.getGamesToWin() > 0) {
            gamesToWinVal = new Label(String.valueOf(Match.getGamesToWin()));
        }
        else {
            gamesToWin.getStyleClass().add("bottomPadding");
            gamesToWinVal = new ImageView(new Image("/img/infinity/infinity_" + Match.getTheme() + ".png"));
        }
        gamesToWinVal.getStyleClass().add("gameStr");

        //Box containing 'Games to Win' + games to win value
        VBox gamesToWinBox = new VBox(gamesToWin, gamesToWinVal);
        gamesToWinBox.getStyleClass().add( "center");

        return gamesToWinBox;
    }

    /**
     * Add icons to the infoBox based off of certain conditions
     * @param infoBox   The VBox View to add icons to
     */
    private void addToInfoBox(VBox infoBox) {
        if (Match.isConnected()) {
            ImageView connected = new ImageView(new Image("/img/bluetooth/bt_" + Match.getTheme() + ".png"));
            connected.getStyleClass().add("bt_icon");
            infoBox.getChildren().add(connected);
        }

        if (Match.getType().equals("switch")) {
            ImageView switchIcon = new ImageView(new Image("/img/switch/switch_" + Match.getTheme() + ".png"));
            infoBox.getChildren().add(switchIcon);
            switchIcon.getStyleClass().add("switch_icon");
        }

        if (Match.isWinByTwo()) {
            ImageView winByTwo = new ImageView(new Image("/img/plus2/plus2_" + Match.getTheme() + ".png"));
            winByTwo.getStyleClass().add("winByTwo");
            infoBox.getChildren().add(winByTwo);
        }
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
    private void increaseScore(Team activeTeam, TeamView activeTeamView, TeamView passiveTeamView) {
        if (activeTeam.getScore() < MAX_SCORE) {
            activeTeam.increaseScore();
            updateScoreNode(activeTeam, activeTeamView);
            Controller.checkWinner(activeTeamView, passiveTeamView);
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
    }
}

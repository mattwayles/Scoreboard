package com.advancedsportstechnologies.view.untimed;

import com.advancedsportstechnologies.model.UntimedMatch;
import com.advancedsportstechnologies.view.GameView;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Scoreboard visual representation of a game. Contains two TeamViews separated by a middle line containing an informational panel
 */
public class UntimedGameView extends GameView {

    public UntimedGameView() {
        HBox gameViewBox = (HBox) super.getView();
        gameViewBox.getChildren().add(1, createSeparator());
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
     * Create a View that contains 'Game x of x'
     * @return a View that contains 'Game x of x'
     */
    private VBox createGameBox() {
        //'Game' label
        Label game = new Label("Game");
        game.getStyleClass().add("middleText");

        //Current game number
        Label currentGameNum = new Label(String.valueOf(UntimedMatch.getCurrentGame() + 1));
        currentGameNum.getStyleClass().add("gameStr");

        //'of' label
        Label of = new Label(" of ");
        of.getStyleClass().addAll("smallerText", "topPadding");

        //Max game number
        Label maxGameNum = new Label(String.valueOf(UntimedMatch.getMaxGames()));
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
        if (UntimedMatch.getCurrentGameWinScore() > 0) {
            scoreToWinVal = new Label(String.valueOf(UntimedMatch.getCurrentGameWinScore()));
        }
        else {
            scoreToWin.getStyleClass().add("bottomPadding");
            scoreToWinVal = new ImageView(new Image("/img/infinity/infinity_" + UntimedMatch.getTheme() + ".png"));
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
        if (UntimedMatch.getGamesToWin() > 0) {
            gamesToWinVal = new Label(String.valueOf(UntimedMatch.getGamesToWin()));
        }
        else {
            gamesToWin.getStyleClass().add("bottomPadding");
            gamesToWinVal = new ImageView(new Image("/img/infinity/infinity_" + UntimedMatch.getTheme() + ".png"));
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
        if (UntimedMatch.isConnected()) {
            ImageView connected = new ImageView(new Image("/img/bluetooth/bt_" + UntimedMatch.getTheme() + ".png"));
            connected.getStyleClass().add("bt_icon");
            infoBox.getChildren().add(connected);
        }

        if (UntimedMatch.getType().equals("switch")) {
            ImageView switchIcon = new ImageView(new Image("/img/switch/switch_" + UntimedMatch.getTheme() + ".png"));
            infoBox.getChildren().add(switchIcon);
            switchIcon.getStyleClass().add("switch_icon");
        }

        if (UntimedMatch.isWinByTwo()) {
            ImageView winByTwo = new ImageView(new Image("/img/plus2/plus2_" + UntimedMatch.getTheme() + ".png"));
            winByTwo.getStyleClass().add("winByTwo");
            infoBox.getChildren().add(winByTwo);
        }
    }
}

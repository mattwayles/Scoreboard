package com.advancedsportstechnologies.view.untimed;

import com.advancedsportstechnologies.Main;
import com.advancedsportstechnologies.model.UntimedMatch;
import com.advancedsportstechnologies.view.GameView;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 * Scoreboard visual representation of a game. Contains two TeamViews separated by a middle line containing an informational panel
 */
public class UntimedGameView extends GameView {

    private final double GAMEBOX_LABEL_SIZE = Main.WIDTH / 40;
    private final double TO_WIN_LABEL_SIZE = Main.WIDTH / 85;
    private final double OF_SIZE = Main.WIDTH / 100;

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
        game.setFont(new Font(game.getFont().getName(), GAMEBOX_LABEL_SIZE));

        //Current game number
        Label currentGameNum = new Label(String.valueOf(UntimedMatch.getCurrentGame() + 1));
        currentGameNum.getStyleClass().add("gameStr");
        currentGameNum.setFont(new Font(currentGameNum.getFont().getName(), GAMEBOX_LABEL_SIZE));

        //'of' label
        Label of = new Label(" of ");
        of.getStyleClass().addAll("middleText", "topPadding");
        of.setFont(new Font(of.getFont().getName(), OF_SIZE));

        //Max game number
        Label maxGameNum = new Label(String.valueOf(UntimedMatch.getMaxGames()));
        maxGameNum.getStyleClass().add("gameStr");
        maxGameNum.setFont(new Font(maxGameNum.getFont().getName(), GAMEBOX_LABEL_SIZE));

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
        scoreToWin.getStyleClass().addAll("middleText");
        scoreToWin.setFont(new Font(scoreToWin.getFont().getName(), TO_WIN_LABEL_SIZE));

        return dynamicToWinBox(scoreToWin);
    }

    /**
     * Create a View that contains 'Games to Win = X'
     * @return GamesToWin Node element
     */
    private VBox createGamesToWin() {
        //'Games to win' label
        Label gamesToWin = new Label("Games to Win");
        gamesToWin.getStyleClass().addAll("middleText");
        gamesToWin.setFont(new Font(gamesToWin.getFont().getName(), TO_WIN_LABEL_SIZE));

        return dynamicToWinBox(gamesToWin);
    }

    /**
     * Consolidation of ScoreToWin and GamesToWin box creation code
     * @param label The labe lto place in the box
     * @return  The X To Win box
     */
    private VBox dynamicToWinBox(Label label) {
        VBox toWinBox;

        //Games to win value
        //if (UntimedMatch.getGamesToWin() > 0) {
            Label gamesToWinVal = new Label(String.valueOf(UntimedMatch.getGamesToWin()));
            gamesToWinVal.setFont(new Font(gamesToWinVal.getFont().getName(), GAMEBOX_LABEL_SIZE));
            gamesToWinVal.getStyleClass().add("gameStr");
            toWinBox = new VBox(label, gamesToWinVal);
            toWinBox.getStyleClass().add("center");
//        }
//        else {
//            label.getStyleClass().add("bottomPadding");
//            ImageView gamesToWinVal = new ImageView(new Image("/img/infinity/infinity_" + UntimedMatch.getTheme() + ".png"));
//            gamesToWinVal.getStyleClass().add("gameStr");
//            toWinBox = new VBox(label, gamesToWinVal);
//            toWinBox.getStyleClass().add( "center");
//        }

        return toWinBox;
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

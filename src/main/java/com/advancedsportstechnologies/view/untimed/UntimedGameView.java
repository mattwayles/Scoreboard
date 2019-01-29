package com.advancedsportstechnologies.view.untimed;

import com.advancedsportstechnologies.Main;
import com.advancedsportstechnologies.model.Match;
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
    private HBox gameViewBox;
    private ImageView bluetoothIcon = new ImageView(new Image("/img/bluetooth/bt_" + UntimedMatch.getTheme() + ".png"));
    private ImageView switchIcon = new ImageView(new Image("/img/switch/switch_" + UntimedMatch.getTheme() + ".png"));
    private ImageView winByTwoIcon = new ImageView(new Image("/img/plus2/plus2_" + UntimedMatch.getTheme() + ".png"));
    private final double GAMEBOX_LABEL_SIZE = Main.WIDTH / 40;
    private final double TO_WIN_LABEL_SIZE = Main.WIDTH / 85;
    private final double OF_SIZE = Main.WIDTH / 100;

    public UntimedGameView() {
        this.gameViewBox = (HBox) super.getView();
        this.gameViewBox.getChildren().add(1, createSeparator());
    }

    public void update() {
        super.update();
        updateSeparator();
    }

    private void updateSeparator() {
        VBox separator = (VBox) gameViewBox.getChildren().get(1);
        VBox infoBox = (VBox) separator.getChildren().get(1);
        updateGameBox(infoBox);
        updateScoreToWinBox(infoBox);
        updateGamesToWinBox(infoBox);
        updateIcons(infoBox);
        HBox gameViewBox = (HBox) super.getView();
        gameViewBox.getChildren().set(1, separator);
    }
    
    private void updateGameBox(VBox infoBox) {
        VBox gameBox = (VBox) infoBox.getChildren().get(1);
        HBox gameNumBox = (HBox) gameBox.getChildren().get(1);
        Label currentGamesLabel = (Label) gameNumBox.getChildren().get(0);
        Label maxGamesLabel = (Label) gameNumBox.getChildren().get(2);
        currentGamesLabel.setText(String.valueOf(UntimedMatch.getCurrentGame() + 1));
        maxGamesLabel.setText(String.valueOf(UntimedMatch.getMaxGames()));
    }

    private void updateScoreToWinBox(VBox infoBox) {
        VBox scoreToWinBox = (VBox) infoBox.getChildren().get(2);
        Label scoreToWinLabel;
        if (UntimedMatch.getCurrentGameWinScore() > 0 && scoreToWinBox.getChildren().get(1) instanceof ImageView) {
            scoreToWinBox.getChildren().remove(1);
            scoreToWinLabel = new Label(String.valueOf(UntimedMatch.getCurrentGameWinScore()));
            scoreToWinLabel.setFont(new Font(scoreToWinLabel.getFont().getName(), GAMEBOX_LABEL_SIZE));
            scoreToWinLabel.getStyleClass().addAll("gameStr", "center");
            scoreToWinBox.getChildren().add(scoreToWinLabel);
        } else if (UntimedMatch.getCurrentGameWinScore() > 0) {
            scoreToWinLabel = (Label) scoreToWinBox.getChildren().get(1);
            scoreToWinLabel.setText(String.valueOf(UntimedMatch.getCurrentGameWinScore()));
        }
    }

    private void updateGamesToWinBox(VBox infoBox) {
        VBox gamesToWinBox = (VBox) infoBox.getChildren().get(3);
        Label gamesToWinLabel;
        if (UntimedMatch.getGamesToWin() > 0 && gamesToWinBox.getChildren().get(1) instanceof ImageView) {
            gamesToWinBox.getChildren().remove(1);
            gamesToWinLabel = new Label(String.valueOf(UntimedMatch.getGamesToWin()));
            gamesToWinLabel.setFont(new Font(gamesToWinLabel.getFont().getName(), GAMEBOX_LABEL_SIZE));
            gamesToWinLabel.getStyleClass().addAll("gameStr", "center");
            gamesToWinBox.getChildren().add(gamesToWinLabel);
        } else if (UntimedMatch.getGamesToWin() > 0) {
            gamesToWinLabel = (Label) gamesToWinBox.getChildren().get(1);
            gamesToWinLabel.setText(String.valueOf(UntimedMatch.getGamesToWin()));
        }
    }

    private void updateIcons(VBox infoBox) {
        int btIndex = infoBox.getChildren().indexOf(bluetoothIcon);
        includeIcon(infoBox, UntimedMatch.isConnected(), btIndex, "bt_icon",
                "/img/bluetooth/bt_" + Match.getTheme() + ".png", bluetoothIcon);

        int switchIndex = infoBox.getChildren().indexOf(switchIcon);
        includeIcon(infoBox, UntimedMatch.getType().equals("switch"), switchIndex, "switch_icon",
                "/img/switch/switch_" + Match.getTheme() + ".png", switchIcon);

        int winBy2Index = infoBox.getChildren().indexOf(winByTwoIcon);
        includeIcon(infoBox, UntimedMatch.isWinByTwo(), winBy2Index, "winByTwo",
                "/img/plus2/plus2_" + Match.getTheme() + ".png", winByTwoIcon);
    }

    private void includeIcon(VBox infoBox, boolean condition, int index, String style, String url, ImageView icon) {
        if (condition && index == -1) {
            icon.getStyleClass().add(style);
            infoBox.getChildren().add(icon);
        } else if(condition && index > -1) {
            ImageView winImgView = (ImageView) infoBox.getChildren().get(index);
            winImgView.setImage(new Image(url));
        }
        else if (!condition && index != -1) {
            infoBox.getChildren().remove(icon);
        }
    }

    /**
     * Create a middle Team separator and include informational panel
     * @return  Separator Node element
     */
    private VBox createSeparator() {
        //Top & Bottom lines
        createMiddleLine();
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
        label.getStyleClass().add("bottomPadding");
        ImageView gamesToWinVal = new ImageView(new Image("/img/infinity/infinity_" + UntimedMatch.getTheme() + ".png"));
        gamesToWinVal.getStyleClass().add("gameStr");
        toWinBox = new VBox(label, gamesToWinVal);
        toWinBox.getStyleClass().add("center");

        return toWinBox;
    }
}

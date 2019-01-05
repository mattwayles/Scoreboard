package com.advancedsportstechnologies.view.timed;

import com.advancedsportstechnologies.Main;
import com.advancedsportstechnologies.controller.Timer;
import com.advancedsportstechnologies.model.Match;
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

public class TimedGameView extends GameView {

    public TimedGameView() {
        HBox gameViewBox = (HBox) super.getView();
        gameViewBox.getChildren().add(1, createSeparator());
        super.setView(new VBox(createTimer(), gameViewBox));
    }

    private HBox createTimer() {
        //TODO: Break into individual methods

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
        HBox timerBox = new HBox(35, periodLabel, timerLabel, iconBox);
        timerBox.getStyleClass().add("timerBox");

        return timerBox;
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
        logoBox.getStyleClass().add("infoBox");

        //Put it all together
        VBox separator = new VBox(topSeparator, logoBox, bottomSeparator);
        separator.getStyleClass().add("center");

        return separator;
    }
}

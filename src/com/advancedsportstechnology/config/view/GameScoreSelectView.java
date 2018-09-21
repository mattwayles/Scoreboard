package com.advancedsportstechnology.config.view;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import com.advancedsportstechnology.config.model.Match;

public class GameScoreSelectView extends Match {
    private static final String GAME_SCORE_SELECT_ID = "gameScoreSelect";

    private VBox gameScoreSelectView;
    private Text selectionField;

    public GameScoreSelectView(int gameNum) {
        this(gameNum, 21);

    }

    public GameScoreSelectView(int gameNum, int defaultScore) {
        createGameScoreSelectView(gameNum, defaultScore);
        this.setId(GAME_SCORE_SELECT_ID);
    }

    public VBox getGameScoreSelectView() { return this.gameScoreSelectView; }

    public Text getSelectionField() { return this.selectionField; }

    private void setGameScoreSelectView(VBox view) { this.gameScoreSelectView = view; }

    private void setSelectionField(Text field) { this.selectionField = field; }

    private void createGameScoreSelectView(int gameNum, int defaultScore) {
        //Create game score selection label
        Label gameSelectionLabel = new Label("Select Game " + (gameNum + 1) + " Score:");
        gameSelectionLabel.getStyleClass().add("gameSelectionLabel");

        //Create game score selection textfield
        Text gameScoreField = new Text(String.valueOf(defaultScore));
        this.setSelectionField(gameScoreField);
        HBox gameScoreHBox = new HBox(gameScoreField);
        gameScoreHBox.getStyleClass().add("scoreField");


        Label directionLabel = new Label("(Use green and red buttons to select score)");
        directionLabel.getStyleClass().add("directionLabel");

        //Create game select VBox
        VBox gameScoreBox = new VBox(gameSelectionLabel, gameScoreHBox, directionLabel);
        gameScoreBox.getStyleClass().add("gameScoreBox");

        this.setGameScoreSelectView(gameScoreBox);
    }
}

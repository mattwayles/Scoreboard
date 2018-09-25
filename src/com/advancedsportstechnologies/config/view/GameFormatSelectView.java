package com.advancedsportstechnologies.config.view;

import com.advancedsportstechnologies.PiController;
import com.advancedsportstechnologies.Run;
import com.advancedsportstechnologies.config.model.Match;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.Arrays;

public class GameFormatSelectView extends MainView {
    private static final String GAME_SELECT_ID = "gameFormat";

    private VBox gameFormatView;
    private String gameScores;
    private int[][] allScores;
    private ComboBox selectionBox;
    private Label scoreLabel;
    private String[] formats = new String[] {"Regular", "Championship"};

    public GameFormatSelectView(String matchType) {
        this.setId(GAME_SELECT_ID);
        this.allScores = matchType.equals("Cornhole") ? Match.CORNHOLE_DEFAULTS : Match.TRAMPOLINE_VOLLEYBALL_DEFAULTS;
        this.gameScores = this.formatGameScores(this.allScores[0]);

        createGameFormatView();

        if (!Run.debug) {this.setEventListeners(); }
    }

    private String getGameScores() { return this.gameScores; }

    private void setGameScores(String gameScoreStr) { this.gameScores = gameScoreStr; }

    public void updateScoreLabel() {
        int selectionBoxIndex = selectionBox.getSelectionModel().getSelectedIndex();
        int[] scoreArr = this.getAllScores()[selectionBoxIndex];
        this.setGameScores(this.formatGameScores(scoreArr));
        this.scoreLabel.textProperty().setValue(this.getGameScores());
    }

    public int[][] getAllScores() { return this.allScores; }

    public ComboBox getSelectionBox() { return this.selectionBox; }

    public VBox getGameFormatSelectView() { return this.gameFormatView; }

    private String formatGameScores(int[] scores) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < scores.length; i++) {
            sb.append(scores[i]);
            if (i != scores.length - 1) {
                sb.append(" - ");
            }
        }
        return sb.toString();
    }


    private void setGameFormatView(VBox view) { this.gameFormatView = view; }

    private void setSelectionBox(ComboBox box) { this.selectionBox = box; }

    private void createGameFormatView() {
        //Create game selection label
        //TODO: Create label with game score on selection

        Label gameFormatLabel = new Label("Select Format:");
        gameFormatLabel.getStyleClass().add("gameSelectionLabel");

        this.scoreLabel = new Label(this.gameScores);
        scoreLabel.getStyleClass().add("gameFormatLabel");

        //Create game selection drop-down
        ObservableList<String> options = FXCollections.observableArrayList();
        options.addAll(Arrays.asList(this.formats));
        ComboBox gameFormatComboBox = new ComboBox<>(options);
        gameFormatComboBox.getSelectionModel().selectNext();
        this.setSelectionBox(gameFormatComboBox);

        //Create game select VBox
        VBox gameBox = new VBox(gameFormatLabel, scoreLabel, gameFormatComboBox);
        gameBox.getStyleClass().add("gameBox");

        this.setGameFormatView(gameBox);
    }

    private void setEventListeners() {
        PiController.controller1Up.addListener((GpioPinListenerDigital) event -> {
            if (event.getState().isHigh()) {
                Platform.runLater(() -> {
                    selectionBox.getSelectionModel().selectPrevious();
                    this.updateScoreLabel();
                });
            }
        });
        PiController.controller1Down.addListener((GpioPinListenerDigital) event -> {
            if (event.getState().isHigh()) {
                Platform.runLater(() -> {
                    selectionBox.getSelectionModel().selectNext();
                    this.updateScoreLabel();
                });
            }
        });
        PiController.controller2Up.addListener((GpioPinListenerDigital) event -> {
            if (event.getState().isHigh()) {
                Platform.runLater(() -> selectionBox.getSelectionModel().selectPrevious());
                this.updateScoreLabel();
            }
        });
        PiController.controller2Down.addListener((GpioPinListenerDigital) event -> {
            if (event.getState().isHigh()) {
                Platform.runLater(() -> selectionBox.getSelectionModel().selectNext());
                this.updateScoreLabel();
            }
        });
        PiController.reset.addListener((GpioPinListenerDigital) event -> {
            if (event.getState().isHigh()) {
                Platform.runLater(() -> {
                    int selectionBoxIndex = selectionBox.getSelectionModel().getSelectedIndex();
                    int[] scoreArr = this.getAllScores()[selectionBoxIndex];
                    PiController.openTeamSelect(scoreArr);



                });
            }
        });
    }
}

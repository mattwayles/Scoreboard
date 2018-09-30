package com.advancedsportstechnologies.config.view;

import com.advancedsportstechnologies.Run;
import com.advancedsportstechnologies.config.controller.Controller;
import com.advancedsportstechnologies.config.controller.PiController;
import com.advancedsportstechnologies.config.model.Match;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;

import java.util.Arrays;

public class GameFormatSelectView extends MainView {
    private VBox gameFormatView;
    private String gameSelection;
    private int[][] allSelections;
    private ComboBox selectionBox;
    private Label scoreLabel;
    private String[] formats;

    public GameFormatSelectView(String matchType) {
        switch (matchType) {
            case "Cornhole":
                formats = new String[] {"Regular", "Championship"};
                this.allSelections = Match.CORNHOLE_DEFAULTS;
                this.gameSelection = this.formatGameSelections(this.allSelections[0]);
                break;
            case "Trampoline Volleyball":
                formats = new String[] {"Regular", "Championship"};
                this.allSelections = Match.TRAMPOLINE_VOLLEYBALL_DEFAULTS;
                this.gameSelection = this.formatGameSelections(this.allSelections[0]);
                break;
            case "Basketball":
                String[] strs = new String[Match.BASKETBALL_DEFAULTS.length];
                for (int i = 0; i < Match.BASKETBALL_DEFAULTS.length; i++) {
                    strs[i] = String.valueOf(Match.BASKETBALL_DEFAULTS[i]) + " mins";
                }
                formats = strs;
        }
        createGameFormatView();

        if (!Run.debug) {
            this.setEventListeners();
        } else {
            this.setKeyPressListeners();
        }
    }

    private String getGameSelections() { return this.gameSelection; }

    private void setGameSelections(String gameScoreStr) { this.gameSelection = gameScoreStr; }

    private void updateScoreLabel() {
        int selectionBoxIndex = selectionBox.getSelectionModel().getSelectedIndex();
        int[] scoreArr = this.getAllSelections()[selectionBoxIndex];
        this.setGameSelections(this.formatGameSelections(scoreArr));
        this.scoreLabel.textProperty().setValue(this.getGameSelections());
    }

    private int[][] getAllSelections() { return this.allSelections; }

    public VBox getGameFormatSelectView() { return this.gameFormatView; }

    private String formatGameSelections(int[] scores) {
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

        //Create game selection drop-down
        ObservableList<String> options = FXCollections.observableArrayList();
        options.addAll(Arrays.asList(this.formats));
        ComboBox gameFormatComboBox = new ComboBox<>(options);
        gameFormatComboBox.getSelectionModel().selectNext();
        this.setSelectionBox(gameFormatComboBox);

        VBox gameBox;
        Label gameFormatLabel;
        if (this.allSelections != null) {
            gameFormatLabel = new Label("Select Format:");
            this.scoreLabel = new Label(this.gameSelection);
            scoreLabel.getStyleClass().add("gameFormatLabel");

            //Create game select VBox
            gameBox = new VBox(gameFormatLabel, scoreLabel, gameFormatComboBox);
        } else {
            gameFormatLabel = new Label("Select Quarter Length");
            gameBox = new VBox(gameFormatLabel, gameFormatComboBox);
        }
        gameFormatLabel.getStyleClass().add("gameSelectionLabel");
        gameBox.getStyleClass().add("gameBox");

        this.setGameFormatView(gameBox);
    }

    private void selectPrevious(GpioPinDigitalStateChangeEvent event) {
        if (event.getState().isHigh()) {
            Platform.runLater(() -> {
                selectionBox.getSelectionModel().selectPrevious();
                this.updateScoreLabel();
            });
        }
    }

    private void selectNext(GpioPinDigitalStateChangeEvent event) {
        if (event.getState().isHigh()) {
            Platform.runLater(() -> {
                selectionBox.getSelectionModel().selectNext();
                this.updateScoreLabel();
            });
        }
    }

    private void reset(GpioPinDigitalStateChangeEvent event) {
        if (event.getState().isLow()) {
            Controller.getView().setKeyPressTime(System.currentTimeMillis());
        }
        else {
            Platform.runLater(() -> {
                if (Controller.resetButtonHeld()) {
                    int selectionBoxIndex = selectionBox.getSelectionModel().getSelectedIndex();
                    int[] scoreArr = this.getAllSelections()[selectionBoxIndex];
                    PiController.openTeamSelect(scoreArr);
                }
                Controller.getView().setKeyPressTime(0);
            });
        }
    }

    private void setEventListeners() {
        PiController.controller1Up.addListener((GpioPinListenerDigital) this::selectPrevious);
        PiController.controller1Down.addListener((GpioPinListenerDigital) this::selectNext);
        PiController.controller2Up.addListener((GpioPinListenerDigital) this::selectPrevious);
        PiController.controller2Down.addListener((GpioPinListenerDigital) this::selectNext);
        PiController.reset.addListener((GpioPinListenerDigital) this::reset);
    }

    private void setKeyPressListeners() {
        Run.getScene().setOnKeyReleased(e -> {
            MainView view = Controller.getView();
            if (e.getCode() == KeyCode.A || e.getCode() == KeyCode.S) {
                selectionBox.getSelectionModel().selectPrevious();
                this.updateScoreLabel();
            } else if (e.getCode() == KeyCode.Z || e.getCode() == KeyCode.X) {
                selectionBox.getSelectionModel().selectNext();
                this.updateScoreLabel();
            } else if (e.getCode() == KeyCode.Q) {
                if (Controller.resetButtonHeld()) {
                    int selectionBoxIndex = selectionBox.getSelectionModel().getSelectedIndex();
                    int[] scoreArr = this.getAllSelections()[selectionBoxIndex];
                    Controller.openTeamSelect(scoreArr);
                }
                view.setKeyPressTime(0);
                view.getKeysDown().remove(e.getCode());
            }
        });
    }
}

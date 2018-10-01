package com.advancedsportstechnologies.config.view;

import com.advancedsportstechnologies.config.controller.Controller;
import com.advancedsportstechnologies.config.controller.PiController;
import com.advancedsportstechnologies.Run;
import com.advancedsportstechnologies.config.model.Match;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;

import java.util.Arrays;


public class GameSelectView extends MainView {
    private VBox gameSelectView;
    private ComboBox selectionBox;
    private String[] games = new String[] {"Cornhole", "Volleyball", "Basketball", "More Games Soon!"};
    private static final int QUARTERS = 4;
    private static final int HALVES = 2;
    private static final int PERIODS = 3;

    public GameSelectView() {
        createGameSelectView();
        if (!Run.debug) {
            this.setEventListeners();
        }
        else {
            this.setKeyPressListeners();
        }
    }

    public VBox getGameSelectView() { return this.gameSelectView; }

    private void setGameSelectView(VBox view) { this.gameSelectView = view; }

    private void setSelectionBox(ComboBox box) { this.selectionBox = box; }

    private void createGameSelectView() {
        //Create game selection label
        Label gameSelectionLabel = new Label("Select Game:");
        gameSelectionLabel.getStyleClass().add("gameSelectionLabel");

        //Create game selection drop-down
        ObservableList<String> options = FXCollections.observableArrayList();
        options.addAll(Arrays.asList(this.games));
        ComboBox gameSelectComboBox = new ComboBox<>(options);
        gameSelectComboBox.getSelectionModel().selectNext();
        this.setSelectionBox(gameSelectComboBox);

        //Create game select VBox
        VBox gameBox = new VBox(gameSelectionLabel, gameSelectComboBox);
        gameBox.getStyleClass().add("gameBox");

        this.setGameSelectView(gameBox);
    }

    private void setEventListeners() {
        PiController.controller1Up.addListener((GpioPinListenerDigital) event -> {
            if (event.getState().isHigh()) {
                Platform.runLater(() -> selectionBox.getSelectionModel().selectPrevious());
            }
        });
        PiController.controller1Down.addListener((GpioPinListenerDigital) event -> {
            if (event.getState().isHigh()) {
                Platform.runLater(() -> selectionBox.getSelectionModel().selectNext());
            }
        });
        PiController.controller2Up.addListener((GpioPinListenerDigital) event -> {
            if (event.getState().isHigh()) {
                Platform.runLater(() -> selectionBox.getSelectionModel().selectPrevious());
            }
        });
        PiController.controller2Down.addListener((GpioPinListenerDigital) event -> {
            if (event.getState().isHigh()) {
                Platform.runLater(() -> selectionBox.getSelectionModel().selectNext());
            }
        });
        PiController.reset.addListener((GpioPinListenerDigital) event -> {
            if (event.getState().isHigh()) {
                Platform.runLater(() -> {
                    String matchType = selectionBox.getSelectionModel().getSelectedItem().toString();
                    PiController.setDebounce();
                    PiController.removeEventListeners();
                    selectGame(matchType);

                });
            }
        });
    }

    private void setKeyPressListeners() {
        Run.getScene().setOnKeyReleased(e -> {
            MainView view = Controller.getView();
            if (e.getCode() == KeyCode.A || e.getCode() == KeyCode.S) {
                selectionBox.getSelectionModel().selectPrevious();
            } else if (e.getCode() == KeyCode.Z || e.getCode() == KeyCode.X) {
                selectionBox.getSelectionModel().selectNext();
            } else if (e.getCode() == KeyCode.Q) {
                String matchType = selectionBox.getSelectionModel().getSelectedItem().toString();
                selectGame(matchType);
            }
            view.setKeyPressTime(0);
            view.getKeysDown().remove(e.getCode());
        });
    }

    private void selectGame(String game) {
        switch (game) {
            case "Cornhole":
                Controller.openGameFormatSelectView(game, Match.CORNHOLE_DEFAULTS);
                break;
            case "Volleyball":
                Controller.openGameFormatSelectView(game, Match.TRAMPOLINE_VOLLEYBALL_DEFAULTS);
                break;
            case "Basketball":
                Controller.openTimedGameFormatSelectView(game, QUARTERS, Match.BASKETBALL_DEFAULTS);
                break;
        }
    }
}

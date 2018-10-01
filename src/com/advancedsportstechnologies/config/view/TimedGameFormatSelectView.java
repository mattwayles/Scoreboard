package com.advancedsportstechnologies.config.view;

import com.advancedsportstechnologies.Run;
import com.advancedsportstechnologies.config.controller.Controller;
import com.advancedsportstechnologies.config.controller.PiController;
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

public class TimedGameFormatSelectView extends MainView {
    private VBox gameFormatView;
    private ComboBox selectionBox;
    private Label scoreLabel;

    public TimedGameFormatSelectView(int[] times) {
        createGameFormatView(times);

        if (!Run.debug) {
            this.setEventListeners();
        } else {
            this.setKeyPressListeners();
        }
    }

    public VBox getGameFormatSelectView() { return this.gameFormatView; }


    private void setGameFormatView(VBox view) { this.gameFormatView = view; }

    private void setSelectionBox(ComboBox box) { this.selectionBox = box; }

    private void createGameFormatView(int[] lengths) {
        //Create game selection label
        //TODO: Create label with game score on selection

        Label gameFormatLabel = new Label("Select Format:");
        gameFormatLabel.getStyleClass().add("gameSelectionLabel");
        //Create game selection drop-down
        ObservableList<String> options = FXCollections.observableArrayList();
        options.addAll(String.valueOf(Arrays.asList(lengths)));
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
                });
            }
        });
        PiController.controller1Down.addListener((GpioPinListenerDigital) event -> {
            if (event.getState().isHigh()) {
                Platform.runLater(() -> {
                    selectionBox.getSelectionModel().selectNext();
                });
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
                    int selectionBoxIndex = selectionBox.getSelectionModel().getSelectedIndex();
                    //PiController.openTeamSelect(scoreArr);



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
                if (!Controller.resetButtonHeld()) {
                    int selectionBoxIndex = selectionBox.getSelectionModel().getSelectedIndex();
                    //Controller.openTeamSelect(scoreArr);
                }
                view.setKeyPressTime(0);
                view.getKeysDown().remove(e.getCode());
            }
        });
    }
}

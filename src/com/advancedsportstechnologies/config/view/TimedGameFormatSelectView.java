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

public class TimedGameFormatSelectView extends MainView {
    private VBox gameFormatView;
    private ComboBox selectionBox;
    private int periods;
    private int[] times;

    public TimedGameFormatSelectView(int periods, int[] times) {
        this.periods = periods;
        this.times = times;
        createGameFormatView();

        if (!Run.debug) {
            this.setEventListeners();
        } else {
            this.setKeyPressListeners();
        }
    }

    public VBox getGameFormatSelectView() { return this.gameFormatView; }


    private void setGameFormatView(VBox view) { this.gameFormatView = view; }

    private void setSelectionBox(ComboBox box) { this.selectionBox = box; }

    private void createGameFormatView() {
        //Create game selection label
        //TODO: Create label with game score on selection

        Label gameFormatLabel =
                periods == 2 ?  new Label("Select Half Length:") :
                        periods == 3 ? new Label("Select Period Length:") :
                                periods == 4 ? new Label("Select Quarter Length:") :
                                        new Label("Select Length:");
        gameFormatLabel.getStyleClass().add("gameSelectionLabel");
        //Create game selection drop-down
        ObservableList<String> options = FXCollections.observableArrayList();
        for (int i : this.times) {
            options.add(i + " mins");
        }
        ComboBox gameFormatComboBox = new ComboBox<>(options);
        gameFormatComboBox.getSelectionModel().selectNext();
        this.setSelectionBox(gameFormatComboBox);

        //Create game select VBox
        VBox gameBox = new VBox(gameFormatLabel, gameFormatComboBox);
        gameBox.getStyleClass().add("gameBox");

        this.setGameFormatView(gameBox);
    }

    private void selectPrevious(GpioPinDigitalStateChangeEvent event) {
        if (event.getState().isHigh()) {
            Platform.runLater(() -> {
                selectionBox.getSelectionModel().selectPrevious();
            });
        }
    }

    private void selectNext(GpioPinDigitalStateChangeEvent event) {
        if (event.getState().isHigh()) {
            Platform.runLater(() -> {
                selectionBox.getSelectionModel().selectNext();
            });
        }
    }

    private void reset(GpioPinDigitalStateChangeEvent event) {
        if (event.getState().isLow()) {
            Controller.getView().setKeyPressTime(System.currentTimeMillis());
        }
        else {
            Platform.runLater(() -> {
                Platform.runLater(() -> {
                    int selectionBoxIndex = selectionBox.getSelectionModel().getSelectedIndex();
                    PiController.removeEventListeners();
                    Controller.openTeamSelect(periods , this.times[selectionBoxIndex]);
                });
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
            } else if (e.getCode() == KeyCode.Z || e.getCode() == KeyCode.X) {
                selectionBox.getSelectionModel().selectNext();
            } else if (e.getCode() == KeyCode.Q) {
                if (!Controller.resetButtonHeld()) {
                    int selectionBoxIndex = selectionBox.getSelectionModel().getSelectedIndex();
                    Controller.openTeamSelect(periods , this.times[selectionBoxIndex]);
                }
                view.setKeyPressTime(0);
                view.getKeysDown().remove(e.getCode());
            }
        });
    }
}

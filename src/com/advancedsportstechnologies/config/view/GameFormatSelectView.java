package com.advancedsportstechnologies.config.view;

import com.advancedsportstechnologies.PiController;
import com.advancedsportstechnologies.Run;
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
    private ComboBox selectionBox;
    private String[] formats = new String[] {"Regular", "Championship"};

    public GameFormatSelectView() {
        createGameFormatView();
        this.setId(GAME_SELECT_ID);
        if (!Run.debug) {this.setEventListeners(); }
    }

    public ComboBox getSelectionBox() { return this.selectionBox; }

    public VBox getGameFormatSelectView() { return this.gameFormatView; }

    private void setGameFormatView(VBox view) { this.gameFormatView = view; }

    private void setSelectionBox(ComboBox box) { this.selectionBox = box; }

    private void createGameFormatView() {
        //Create game selection label
        //TODO: Create label with game score on selection

        Label gameFormatLabel = new Label("Select Format:");
        gameFormatLabel.getStyleClass().add("gameSelectionLabel");

        //Create game selection drop-down
        ObservableList<String> options = FXCollections.observableArrayList();
        options.addAll(Arrays.asList(this.formats));
        ComboBox gameFormatComboBox = new ComboBox<>(options);
        gameFormatComboBox.getSelectionModel().selectNext();
        this.setSelectionBox(gameFormatComboBox);

        //Create game select VBox
        VBox gameBox = new VBox(gameFormatLabel, gameFormatComboBox);
        gameBox.getStyleClass().add("gameBox");

        this.setGameFormatView(gameBox);
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
                    String format = selectionBox.getSelectionModel().getSelectedItem().toString();
                    PiController.openTeamSelect(format);

                });
            }
        });
    }
}

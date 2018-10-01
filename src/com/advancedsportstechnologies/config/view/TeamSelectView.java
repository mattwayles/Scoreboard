package com.advancedsportstechnologies.config.view;

import com.advancedsportstechnologies.Run;
import com.advancedsportstechnologies.config.controller.Controller;
import com.advancedsportstechnologies.config.controller.PiController;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class TeamSelectView extends MainView {
    private VBox teamSelectView;
    private ComboBox team1Select;
    private ComboBox team2Select;

    public TeamSelectView(String id) {
        try {
            this.populateTeamOptions(id);
        } catch (IOException e) {
            e.printStackTrace();
        }
        HBox teamSelectBoxes = createTeamSelectBoxes();
        this.teamSelectView = createTeamSelectView(teamSelectBoxes);
        if (!Run.debug) {
            this.setEventListeners();
        } else {
            this.setKeyPressListeners();
        }
    }

    public VBox getTeamSelectView() { return this.teamSelectView; }
    public ComboBox getTeam1Select() { return this.team1Select; }
    public ComboBox getTeam2Select() { return this.team2Select; }

    private VBox createTeamSelectView(HBox selectBoxes) {

        VBox teamSelectView = new VBox(selectBoxes);
        teamSelectView.getStyleClass().add("teamSelectView");

        return teamSelectView;
    }

    private HBox createTeamSelectBoxes() {
        Label team1SelectionLabel = new Label("Select Team 1:");
        team1SelectionLabel.getStyleClass().add("team1SelectionLabel");
        VBox team1Box = new VBox(team1SelectionLabel, team1Select);
        team1Box.getStyleClass().add("teamBox");
        Label team2SelectionLabel = new Label("Select Team 2:");
        team2SelectionLabel.getStyleClass().add("team2SelectionLabel");
        VBox team2Box = new VBox(team2SelectionLabel, team2Select);
        team2Box.getStyleClass().add("teamBox");

        HBox selectBoxes = new HBox(150);
        selectBoxes.getStyleClass().add("selectBoxes");
        selectBoxes.getChildren().addAll(team1Box, team2Box);

        return selectBoxes;
    }

    private void populateTeamOptions(String id) throws IOException {
        ObservableList<String> options = FXCollections.observableArrayList();
        File file = new File(System.getProperty("user.dir") + "/" + id + "Teams.txt");
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                options.add(scanner.nextLine());

            }
        }
        options = options.sorted();
        this.team1Select = new ComboBox<>(options);
        team1Select.getSelectionModel().selectNext();
        this.team2Select = new ComboBox<>(options);
        team2Select.getSelectionModel().selectNext();
    }

    private void setEventListeners() {
        PiController.controller1Up.addListener((GpioPinListenerDigital) event -> {
            if (event.getState().isHigh()) {
                Platform.runLater(() -> team1Select.getSelectionModel().selectPrevious());
            }
        });
        PiController.controller1Down.addListener((GpioPinListenerDigital) event -> {
            if (event.getState().isHigh()) {
                Platform.runLater(() -> team1Select.getSelectionModel().selectNext());
            }
        });
        PiController.controller2Up.addListener((GpioPinListenerDigital) event -> {
            if (event.getState().isHigh()) {
                Platform.runLater(() -> team2Select.getSelectionModel().selectPrevious());
            }
        });
        PiController.controller2Down.addListener((GpioPinListenerDigital) event -> {
            if (event.getState().isHigh()) {
                Platform.runLater(() -> team2Select.getSelectionModel().selectNext());
            }
        });
        PiController.reset.addListener((GpioPinListenerDigital) event -> {
            if (event.getState().isLow()) {
                Controller.getView().setKeyPressTime(System.currentTimeMillis());
            }
            else {
                Platform.runLater(() -> {
                    if (!Controller.resetButtonHeld()) {
                        Platform.runLater(() -> PiController.startMatch(this));
                    }
                    Controller.getView().setKeyPressTime(0);
                });
            }
        });
    }

    private void setKeyPressListeners() {
        Run.getScene().setOnKeyReleased(e -> {
            MainView view = Controller.getView();
            if (e.getCode() == KeyCode.A || e.getCode() == KeyCode.S) {
                team1Select.getSelectionModel().selectPrevious();
            } else if (e.getCode() == KeyCode.Z || e.getCode() == KeyCode.X) {
                team1Select.getSelectionModel().selectNext();
            } else if (e.getCode() == KeyCode.Q) {
                if (!Controller.resetButtonHeld()) {
                    Controller.startMatch(this);
                }
            }
            view.setKeyPressTime(0);
            view.getKeysDown().remove(e.getCode());
        });
    }
}

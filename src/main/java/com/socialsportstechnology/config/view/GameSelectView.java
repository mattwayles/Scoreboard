package main.java.com.socialsportstechnology.config.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import main.java.com.socialsportstechnology.config.model.Match;

import java.util.Arrays;

public class GameSelectView extends Match {
    private static final String GAME_SELECT_ID = "gameSelect";

    private VBox gameSelectView;
    private ComboBox selectionBox;
    private String[] games = new String[] {"Cornhole", "More Games Soon!"};

    GameSelectView() {
        createGameSelectView();
        this.setId(GAME_SELECT_ID);
    }

    public ComboBox getSelectionBox() { return this.selectionBox; }

    VBox getGameSelectView() { return this.gameSelectView; }

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
}

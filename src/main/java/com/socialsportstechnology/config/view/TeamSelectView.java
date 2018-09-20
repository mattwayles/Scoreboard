package main.java.com.socialsportstechnology.config.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.java.com.socialsportstechnology.config.model.Match;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class TeamSelectView extends Match {
    private static final String TEAM_SELECT_ID = "teamSelect";

    private VBox teamSelectView;
    private ComboBox team1Select;
    private ComboBox team2Select;

    public TeamSelectView() {
        try {
            this.populateTeamOptions();
        } catch (IOException e) {
            e.printStackTrace();
        }
        HBox teamSelectBoxes = createTeamSelectBoxes();
        this.teamSelectView = createTeamSelectView(teamSelectBoxes);
        this.setId(TEAM_SELECT_ID);

    }

    public VBox getTeamSelectView() { return this.teamSelectView; }
    public ComboBox getTeam1Select() { return this.team1Select; }
    public ComboBox getTeam2Select() { return this.team2Select; }

    private VBox createTeamSelectView(HBox selectBoxes) {

        VBox teamSelectView = new VBox(selectBoxes);
        //TODO: Move this to CSS?
        teamSelectView.setSpacing(150);
        teamSelectView.setAlignment(Pos.CENTER);

        return teamSelectView;
    }

    private HBox createTeamSelectBoxes() {
        Label team1SelectionLabel = new Label("Select Team 1:");
        team1SelectionLabel.getStyleClass().add("team1SelectionLabel");
        VBox team1Box = new VBox(team1SelectionLabel, team1Select);
        //TODO: Move to CSS?
        team1Box.setSpacing(10);
        team1Box.setAlignment(Pos.CENTER);
        Label team2SelectionLabel = new Label("Select Team 2:");
        team2SelectionLabel.getStyleClass().add("team2SelectionLabel");
        VBox team2Box = new VBox(team2SelectionLabel, team2Select);
        //TODO: Move to CSS?
        team2Box.setSpacing(10);
        team2Box.setAlignment(Pos.CENTER);

        HBox selectBoxes = new HBox(250);
        selectBoxes.setAlignment(Pos.CENTER);
        selectBoxes.getChildren().addAll(team1Box, team2Box);

        return selectBoxes;
    }

    private void populateTeamOptions() throws IOException {
        ObservableList<String> options = FXCollections.observableArrayList();

        //TODO: Send in the teamName textfile as a main() arg
        //TODO: Sort list
        File file = new File(System.getProperty("user.dir") + "/TeamNames.txt");
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                options.add(scanner.nextLine());

            }
        }
        this.team1Select = new ComboBox<>(options);
        team1Select.getSelectionModel().selectNext();
        this.team2Select = new ComboBox<>(options);
        team2Select.getSelectionModel().selectNext();
    }
}

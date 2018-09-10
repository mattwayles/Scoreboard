package scoreboard.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class TeamSelect extends Main {
    private VBox teamSelectView;
    private ComboBox team1Select;
    private ComboBox team2Select;


    TeamSelect() {
        try {
            this.populateTeamOptions();
        } catch (IOException e) {
            e.printStackTrace();
        }
        HBox teamSelectBoxes = createTeamSelectBoxes();
        this.teamSelectView = createTeamSelectView(teamSelectBoxes);

    }

    VBox getTeamSelectView() { return this.teamSelectView; }
    public ComboBox getTeam1Select() { return this.team1Select; }
    public ComboBox getTeam2Select() { return this.team2Select; }

    private VBox createTeamSelectView(HBox selectBoxes) {
        Label title = new Label("Chucktown Social Cornhole Scoreboard");
        title.getStyleClass().add("mainTitle");
        Label version = new Label("v0.1");
        version.getStyleClass().add("version");
        VBox titleBox = new VBox(title, version);
        titleBox.setAlignment(Pos.CENTER);
        Label beginLabel = new Label("Press Start to Begin (Q / W until controllers work)");
        beginLabel.getStyleClass().add("pressStartLabel");
        VBox teamSelectView = new VBox(titleBox, selectBoxes, beginLabel);
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

        HBox selectBoxes = new HBox(WIDTH / 2);
        selectBoxes.setAlignment(Pos.CENTER);
        selectBoxes.getChildren().addAll(team1Box, team2Box);

        return selectBoxes;
    }

    private void populateTeamOptions() throws IOException {
        ObservableList<String> options = FXCollections.observableArrayList();

        File file = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("TeamNames.txt")).getFile());
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

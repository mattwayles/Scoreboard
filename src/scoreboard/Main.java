package scoreboard;

import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Main extends Application {
    private static final int HEIGHT = 768;
    private static final int WIDTH = 1280;


    //TODO: Clean up code, set style properties in CSS
    //TODO: Wider separator
    //TODO: Center everything based on dynamic page width
    //TODO: "startGame" method, preferably in controller, to reset and start game
    //TODO: Set team names
    //TODO: Stop at 21, display winner
    //TODO: "New Game" / "New Match" buttons
    //TODO: Game counter, third game only to 15, display games won, display MATCH winner

    @Override
    public void start(Stage primaryStage) {
        StackPane root = new StackPane();
        primaryStage.setTitle("Chucktown Social");
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        scene.getStylesheets().add(getClass().getResource("css/style.css").toExternalForm());

        Team team1 = new Team("Team 1", 0);
        Team team2 = new Team("Team 2", 0);

        scene.setOnKeyPressed(e -> changeScore(e, team1, team2, root));

        //SEPARATOR
        Separator separator = new Separator();
        separator.setPrefHeight(HEIGHT);
        separator.setOrientation(Orientation.VERTICAL);


        //VBOX
        VBox teamOneScoreBox = new VBox(team1.getScore());
        teamOneScoreBox.setAlignment(Pos.CENTER);
        teamOneScoreBox.setPrefHeight(HEIGHT);
        VBox team1Box = new VBox(team1.getTeamName(), teamOneScoreBox);
        team1Box.setAlignment(Pos.CENTER);
        team1Box.setPrefWidth(WIDTH / 2);

        VBox teamTwoScoreBox = new VBox(team2.getScore());
        teamTwoScoreBox.setAlignment(Pos.CENTER_LEFT);
        teamTwoScoreBox.setAlignment(Pos.CENTER);
        teamTwoScoreBox.setPrefHeight(HEIGHT);
        VBox team2Box = new VBox(team2.getTeamName(), teamTwoScoreBox);
        team2Box.setAlignment(Pos.CENTER);
        team2Box.setPrefWidth(WIDTH / 2);

        //HBOX
        HBox teams = new HBox(team1Box, separator, team2Box);




        root.getChildren().add(teams);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }


    private void changeScore(KeyEvent e, Team team1, Team team2, Pane root) {
        Integer teamOneScore = Integer.parseInt(team1.getScore().textProperty().getValue());
        Integer teamTwoScore = Integer.parseInt(team2.getScore().textProperty().getValue());
        if (e.getCode() == KeyCode.A) {
            team1.setScore(teamOneScore + 1);
        }
        else if (e.getCode() == KeyCode.S) {
            team2.setScore(teamTwoScore + 1);
        }
        else if (e.getCode() == KeyCode.Z && teamOneScore > 0 ) {
            team1.setScore(teamOneScore - 1);
        }
        else if (e.getCode() == KeyCode.X && teamTwoScore > 0) {
            team2.setScore(teamTwoScore - 1);
        }

        if (teamOneScore == 5) {
            Label winner = new Label("TEAM ONE WINS");
            winner.setLayoutX(300/2);
            winner.setLayoutY(275/2);
            root.getChildren().add(winner);
        }
    }
}

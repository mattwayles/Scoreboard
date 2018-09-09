package scoreboard.view;

import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Separator;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import scoreboard.controller.Controller;
import scoreboard.model.Team;

public class Main extends Application {
    static final int HEIGHT = 768;
    static final int WIDTH = 1280;


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

        Match match = new Match();

        scene.setOnKeyPressed(e -> {
            Controller.changeScore(e, match.team1, match.team2, root);
            match.updateView();
            root.getChildren().remove(0);
            root.getChildren().add(match.view);
        });
        root.getChildren().add(match.view);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

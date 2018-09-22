package com.advancedsportstechnologies;

import com.advancedsportstechnologies.config.view.GameSelectView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import com.advancedsportstechnologies.config.view.MainView;

public class Run extends Application {
    private static final double HEIGHT = Screen.getPrimary().getVisualBounds().getHeight();
    private static final double WIDTH = Screen.getPrimary().getVisualBounds().getWidth();
    private static Stage stage;
    private static boolean debug;

    //TODO: This Version Goals:
        //TODO: GPIO Pins
        //TODO: Select team name color, select background
        //TODO: Dark theme
        //TODO: Label CSS buffing; shadows, gradients, add personality to labels
        //TODO: CSS Transitions; make "Press Start to Play" bounce

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        StackPane root = new StackPane();
        root.setId("root");
        primaryStage.setTitle("Chucktown Social");

        Controller controller = new Controller();

        GameSelectView gameSelection = new GameSelectView();
        MainView mainView = new MainView(gameSelection);
        gameSelection.setMainView(mainView.getMainView());
        PiController.setView(mainView);

        root.getChildren().add(mainView.getMainView());
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        scene.setOnKeyPressed(e ->
                controller.routeKeyPress(e, mainView));
        scene.setOnKeyReleased(e -> controller.releaseKey(e, mainView));

        scene.getStylesheets().add(getClass().getResource("config/css/configStyle.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("modules/shared/css/sharedStyle.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("modules/games/cornhole/css/cornholeStyle.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("modules/games/trampolinevolleyball/css/volleyballStyle.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    static void close() {
        stage.close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

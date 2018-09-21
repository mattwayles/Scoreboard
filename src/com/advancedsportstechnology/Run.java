package com.advancedsportstechnology;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import com.advancedsportstechnology.config.view.MainView;

public class Run extends Application {
    private static final double HEIGHT = Screen.getPrimary().getVisualBounds().getHeight();
    private static final double WIDTH = Screen.getPrimary().getVisualBounds().getWidth();
    private static Stage stage;

    //TODO: This Version Goals:
        //TODO: Java .jar run
        //TODO: GPIO Pins
        //TODO: Label CSS buffing; shadows, gradients, add personality to labels
        //TODO: CSS Transitions; make "Press Start to Play" bounce

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        StackPane root = new StackPane();
        root.setId("root");
        primaryStage.setTitle("Chucktown Social");

        MainView mainView = new MainView();
        Controller controller = new Controller();
        root.getChildren().add(mainView.getMainView());

        Scene scene = new Scene(root, WIDTH, HEIGHT);
        scene.setOnKeyPressed(e -> controller.routeKeyPress(e, mainView));
        scene.setOnKeyReleased(e -> controller.releaseKey(e, mainView));
        scene.getStylesheets().add(getClass().getResource("config/css/configStyle.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("modules/games/cornhole/css/cornholeStyle.css").toExternalForm());
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

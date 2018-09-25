package com.advancedsportstechnologies;

import com.advancedsportstechnologies.config.controller.Controller;
import com.advancedsportstechnologies.config.controller.PiController;
import com.advancedsportstechnologies.config.view.GameSelectView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import com.advancedsportstechnologies.config.view.MainView;

public class Run extends Application {
    public static final double HEIGHT = Screen.getPrimary().getVisualBounds().getHeight();
    public static final double WIDTH = Screen.getPrimary().getVisualBounds().getWidth();

    private static Stage stage;
    private static Scene scene;
    public static boolean debug;

    //TODO: Bugs / Features:
        //TODO: Complete GPIO Pin operations (Reset game/ restart match/ Easter Egg/ Volleyball)
        //TODO: Volleyball easter egg not working, prolly cuz of VolleyballMatchView setKeyPressListener

    //TODO: Aesthetic:
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

        Scene rootScene = new Scene(root, WIDTH, HEIGHT);
        scene = rootScene;
        GameSelectView gameSelection = new GameSelectView();
        MainView mainView = new MainView(gameSelection);
        gameSelection.setMainView(mainView.getMainView());

        root.getChildren().add(mainView.getMainView());
        Controller controller = new Controller();
        controller.setView(mainView);
        scene.setOnKeyPressed(e -> Controller.keyPress());

        rootScene.getStylesheets().add(getClass().getResource("config/css/configStyle.css").toExternalForm());
        rootScene.getStylesheets().add(getClass().getResource("modules/shared/css/sharedStyle.css").toExternalForm());
        rootScene.getStylesheets().add(getClass().getResource("modules/games/cornhole/css/cornholeStyle.css").toExternalForm());
        rootScene.getStylesheets().add(getClass().getResource("modules/games/trampolinevolleyball/css/volleyballStyle.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static Scene getScene() { return scene; }

    public static void close() {
        stage.close();
    }

    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("debug")) {
            debug = true;
        }
        launch(args);
    }
}

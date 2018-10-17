package com.advancedsportstechnologies;

import com.advancedsportstechnologies.bluetooth.WaitThread;
import com.advancedsportstechnologies.controller.PiController;
import com.advancedsportstechnologies.model.Match;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {

    public static final String VERSION = "v0.2";
    public static final double HEIGHT = 1024;
    //public static final double HEIGHT = Screen.getPrimary().getVisualBounds().getHeight();
    public static final double WIDTH = 1280;
    //public static final double WIDTH = Screen.getPrimary().getVisualBounds().getWidth();
    public static boolean debug;
    private static StackPane root;
    private static Scene scene;

    //Main: 1280x1024

    //TODO: Comment and cleanup


    @Override
    public void start(Stage primaryStage){
        root = new StackPane();
        root.setId("root");
        primaryStage.setTitle("Scoreboard");
        scene = new Scene(root, WIDTH, HEIGHT);
        Match.setTeams();
        Match.startOrRefresh();
        primaryStage.setScene(scene);

        scene.getStylesheets().add(getClass().getResource("css/style.css").toExternalForm());


        primaryStage.show();

        if (!Main.debug) {
            PiController.setDebounce();
        }


        Thread waitThread = new Thread(new WaitThread());
        waitThread.start();

    }

    public static StackPane getRoot() { return root; }

    public static Scene getScene() { return scene; }


    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("debug")) {
            debug = true;
        }

        launch(args);
    }
}

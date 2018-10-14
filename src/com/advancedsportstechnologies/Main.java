package com.advancedsportstechnologies;

import com.advancedsportstechnologies.bluetooth.WaitThread;
import com.advancedsportstechnologies.model.Match;
import com.advancedsportstechnologies.view.GameView;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {

    public static final String VERSION = "v0.1";
    public static final double HEIGHT = 1080;
    public static boolean debug;

   // public static final double HEIGHT = Screen.getPrimary().getVisualBounds().getHeight();
    public static final double WIDTH = 1980;
    //public static final double WIDTH = Screen.getPrimary().getVisualBounds().getWidth();
    private static StackPane root;
    private static Scene scene;

    @Override
    public void start(Stage primaryStage){
        root = new StackPane();
        root.setId("root");
        primaryStage.setTitle("Scoreboard");
        scene = new Scene(root, WIDTH, HEIGHT);
        Match.setTeams();
        Match.start();
        primaryStage.setScene(scene);

        scene.getStylesheets().add(getClass().getResource("css/style.css").toExternalForm());


        primaryStage.show();

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

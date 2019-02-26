package com.advancedsportstechnologies;

import com.advancedsportstechnologies.bluetooth.WaitThread;
import com.advancedsportstechnologies.controller.PiController;
import com.advancedsportstechnologies.model.Match;
import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * Create a default scoreboard to visually represent changing scores for two teams playing a game. The scoreboard defaults
 * to two teams (Team 1 and Team 2), playing a game with a score up to 99. Players can user keyboard presses or Raspberry Pi GPIO pins
 * to change the score.
 *
 * Keys:
 *      Team 1 Score Increase: A
 *      Team 1 Score Decrease: Z
 *      Team 2 Score Increase: S
 *      Team 2 Score Decrease: X
 *      Reset Scoreboard: Q
 *
 * The Scoreboard is configurable through Bluetooth connection, and expands its functionality. To configure the Scoreboard via
 * Bluetooth, please download the Advanced Sports Technologies Custom Scoreboard app from Google Play (iOS coming soon!)
 *
 * A configured scoreboard can contain custom team names, a max number of games in a match, # of games required to win a match,
 * win scores for each game in the match. This allows the scoreboard to track activities that can contain multiple games.
 *
 * Code developed independently by Matthew Wayles (mattwayles@gmail.com). All Rights Reserved.
 */

public class Main extends Application {

    public static final String VERSION = "v1.2";
    public static double HEIGHT;
    public static double WIDTH;

    private static Scene scene;
    private static StackPane root;

    public static boolean debug;

    /**
     * Start the application by initializing and displaying a default keyboard
     * @param primaryStage The window the displays the scoreboard
     */
    @Override
    public void start(Stage primaryStage){
        HEIGHT = Screen.getPrimary().getVisualBounds().getHeight();
        WIDTH = Screen.getPrimary().getVisualBounds().getWidth();

        primaryStage.setTitle("Scoreboard " + VERSION);

        //Create root pane, which will hold all other UI Views
        root = new StackPane();
        root.setId("root");

        //Create a scene, which displays the components in the root pane
        scene = new Scene(root, WIDTH, HEIGHT);

        //Set default teams and begin a new match
        Match.setTeams();
        Match.start();

        //Open a new window and display the scoreboard
        primaryStage.setScene(scene);
        scene.getStylesheets().add("css/style.css");
        scene.getStylesheets().add("css/theme/traditional.css");
        scene.setCursor(Cursor.NONE);
        primaryStage.setFullScreen(true);
        primaryStage.show();

        //Initialize Pi buttons if applicable
        if (!Main.debug) {
            PiController.setDebounce();
        }

        //Wait for Bluetooth connections in the background, in case a user wants to configure a custom scoreboard
        Thread waitThread = new Thread(new WaitThread());
        waitThread.start();

    }

    /**
     * Retrieve the root pane
     * @return  The root pane
     */
    public static StackPane getRoot() { return root; }

    /**
     * Retrieve the application scene
     * @return The application scene
     */
    public static Scene getScene() { return scene; }

    /**
     * Entrypoint to the application, which launches the JavaFX framework
     * @param args  Any command-line arguments
     */
    public static void main(String[] args) {
        //Debug mode disables the Pi controller and allows the app to run without Pi connection. Use keyboard to navigate.
        if (args.length > 0 && args[0].equals("debug")) {
            debug = true;
        }

        launch(args);
    }
}
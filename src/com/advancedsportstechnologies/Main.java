package com.advancedsportstechnologies;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {

    private static final double HEIGHT = Screen.getPrimary().getVisualBounds().getHeight();
    private static final double WIDTH = Screen.getPrimary().getVisualBounds().getWidth();

    @Override
    public void start(Stage primaryStage){
        Parent root = new StackPane();
        primaryStage.setTitle("Scoreboard");
        primaryStage.setScene(new Scene(root, WIDTH, HEIGHT));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

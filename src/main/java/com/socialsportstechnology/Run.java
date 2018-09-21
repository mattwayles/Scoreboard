package main.java.com.socialsportstechnology;

        import javafx.application.Application;
        import javafx.scene.Scene;
        import javafx.scene.layout.*;
        import javafx.stage.Screen;
        import javafx.stage.Stage;
        import main.java.com.socialsportstechnology.config.view.MainView;

public class Run extends Application {
    private static final double HEIGHT = Screen.getPrimary().getVisualBounds().getHeight();
    private static final double WIDTH = Screen.getPrimary().getVisualBounds().getWidth();
    private static Stage stage;

    //TODO: Next Version Improvements:
        //TODO: Java .jar run
        //TODO: Set style properties in CSS
        //TODO: Center everything based on dynamic page width

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
        scene.getStylesheets().add(getClass().getResource("games/cornhole/css/cornholeStyle.css").toExternalForm());
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

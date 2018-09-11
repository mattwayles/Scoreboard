package scoreboard.view;

        import javafx.application.Application;
        import javafx.scene.Scene;
        import javafx.scene.input.KeyCode;
        import javafx.scene.layout.*;
        import javafx.stage.Stage;
        import scoreboard.controller.Controller;

public class Main extends Application {
    static final int HEIGHT = 1000;
    static final int WIDTH = 1980;
    private StackPane root = new StackPane();
    private Scene scene = new Scene(root, WIDTH, HEIGHT);
    private Stage primaryStage;

    //TODO: Set style properties in CSS
    //TODO: Center everything based on dynamic page width



    @Override
    public void start(Stage primaryStage) {
        root.setId("root");
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Chucktown Social");
        scene.getStylesheets().add(getClass().getResource("css/style.css").toExternalForm());
        TeamSelect teamSelect = new TeamSelect();
        scene.setOnKeyPressed(e -> Controller.chooseTeams(e, teamSelect, this));
        root.getChildren().add(teamSelect.getTeamSelectView());
        this.primaryStage.setScene(scene);
        this.primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void newMatch(TeamSelect teamSelect) {
        String team1Name = teamSelect.getTeam1Select().getValue().toString();
        String team2Name = teamSelect.getTeam2Select().getValue().toString();
        root.getChildren().remove(0);

        Match match = new Match(team1Name, team2Name);
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.Q || e.getCode() == KeyCode.W) {
                restart();
            }
            else{
                Controller.changeScore(e, match, this);
            }
        });
        scene.setOnKeyReleased(e -> Controller.releaseKey(e, match, this));

        root.getChildren().add(match.getMatchView());
    }

    public void refresh(Match match) {
        root.getChildren().remove(0);
        root.getChildren().add(match.getMatchView());
    }

    private void restart() {
        Controller.resetMatch();
        root.getChildren().remove(0);
        start(primaryStage);
    }
}

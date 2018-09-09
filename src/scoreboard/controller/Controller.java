package scoreboard.controller;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import scoreboard.model.Team;

public class Controller {
    public static void changeScore(KeyEvent e, Team team1, Team team2, Pane root) {
        if (e.getCode() == KeyCode.A) {
            team1.setScore(team1.getScore() + 1);
        }
        else if (e.getCode() == KeyCode.S) {
            team2.setScore(team2.getScore() + 1);
        }
        else if (e.getCode() == KeyCode.Z && team1.getScore() > 0 ) {
            team1.setScore(team1.getScore() - 1);
        }
        else if (e.getCode() == KeyCode.X && team2.getScore() > 0) {
            team2.setScore(team2.getScore() - 1);
        }

        if (team1.getScore() == 21) {
            team1.setGamesWon(team1.getGamesWon() + 1);
        }
        else if (team2.getScore() == 21) {
            team2.setGamesWon(team2.getGamesWon() + 1);
        }
    }
}

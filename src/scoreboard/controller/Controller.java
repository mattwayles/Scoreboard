package scoreboard.controller;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import scoreboard.view.TeamView;

public class Controller {
    public static void changeScore(KeyEvent e, TeamView team1, TeamView team2) {
        if (e.getCode() == KeyCode.A) {
            team1.setScore(team1.getScore() + 1);
            team1.setScoreLabel(team1.getScore());
        }
        else if (e.getCode() == KeyCode.S) {
            team2.setScore(team2.getScore() + 1);
            team2.setScoreLabel(team2.getScore());
        }
        else if (e.getCode() == KeyCode.Z && team1.getScore() > 0 ) {
            team1.setScore(team1.getScore() - 1);
            team1.setScoreLabel(team1.getScore());
        }
        else if (e.getCode() == KeyCode.X && team2.getScore() > 0) {
            team2.setScore(team2.getScore() - 1);
            team2.setScoreLabel(team2.getScore());
        }

        if (team1.getScore() == 21) {
            team1.setGamesWonLabel(team1.getGamesWon() + 1);
        }
        else if (team2.getScore() == 21) {
            team2.setGamesWonLabel(team2.getGamesWon() + 1);
        }
    }
}

package scoreboard.controller;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import scoreboard.view.Main;
import scoreboard.view.Match;
import scoreboard.view.TeamSelect;
import scoreboard.view.TeamView;

public class Controller {
    private static int winScore = 21;
    private static int gameNum = 1;

    public static void chooseTeams(KeyEvent e, TeamSelect teamSelect, Main main) {
        if (e.getCode() == KeyCode.A) {
            teamSelect.getTeam1Select().getSelectionModel().selectPrevious();
        }
        else if (e.getCode() == KeyCode.Z) {
            teamSelect.getTeam1Select().getSelectionModel().selectNext();
        }
        else if (e.getCode() == KeyCode.S) {
            teamSelect.getTeam2Select().getSelectionModel().selectPrevious();
        }
        else if (e.getCode() == KeyCode.X) {
            teamSelect.getTeam2Select().getSelectionModel().selectNext();
        }
        else if (e.getCode() == KeyCode.Q || e.getCode() == KeyCode.W)
            main.newMatch(teamSelect);
    }

    public static void changeScore(KeyEvent e, Match match, Main main) {
        TeamView team1 = match.getTeam1();
        TeamView team2 = match.getTeam2();

        if (e.getCode() == KeyCode.A) {
            team1.setScore(team1.getScore() + 1);
            team1.setScoreLabel(team1.getScore());
        } else if (e.getCode() == KeyCode.S) {
            team2.setScore(team2.getScore() + 1);
            team2.setScoreLabel(team2.getScore());
        } else if (e.getCode() == KeyCode.Z && team1.getScore() > 0) {
            team1.setScore(team1.getScore() - 1);
            team1.setScoreLabel(team1.getScore());
        } else if (e.getCode() == KeyCode.X && team2.getScore() > 0) {
            team2.setScore(team2.getScore() - 1);
            team2.setScoreLabel(team2.getScore());
        }

        if (team1.getScore() == winScore || team2.getScore() == winScore) {
            if (team1.getScore() == winScore) {
                winner(team1, match, main);
            }
            else if (team2.getScore() == winScore) {
                winner(team2, match, main);
            }
            resetGame(team1, team2);
        }
    }
    
    private static void winner(TeamView team, Match match, Main main) {
            team.setGamesWon(team.getGamesWon() + 1);
            if (team.getGamesWon() == 1) {
                team.getGamesWonImgs().getChildren().remove(0);
            }
            else if (team.getGamesWon() == 2) {
                match.displayWinner(team.getTeamName(), gameNum);
                main.refresh(match);
            }
            if (gameNum < 3) {
                team.getGamesWonImgs().getChildren().add(new ImageView(new Image("/img/game" + gameNum + ".png")));
            }
            winScore = ++gameNum == 3 ? 15 : winScore;
    }

    private static void resetGame(TeamView team1, TeamView team2) {
        team1.setScore(0);
        team1.setScoreLabel(team1.getScore());
        team2.setScore(0);
        team2.setScoreLabel(team2.getScore());
    }

    public static void resetMatch() {
        gameNum = 1;
        winScore = 21;
        TeamView.resetCount();
    }
}

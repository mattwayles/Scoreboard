package com.advancedsportstechnologies.controller;

import com.advancedsportstechnologies.Main;
import com.advancedsportstechnologies.model.Match;
import com.advancedsportstechnologies.view.*;
import javafx.application.Platform;

/**
 * Base controller for performing scoreboard actions. Accessed directly from the keyboard, or from the PiController
 */
public class Controller {

    /**
     * After each score is registered, check to see if the team won
     * @param winningTeam   The team registering a score
     * @param losingTeam    The team not registering a score
     */
    public static void checkWinner(TeamView winningTeam, TeamView losingTeam) {
        int winningTeamScore = winningTeam.getTeam().getScore();
        int losingTeamScore = losingTeam.getTeam().getScore();

        if (Match.isWinByTwo()) {
            if (winningTeamScore >= Match.getCurrentGameWinScore() &&
                    winningTeamScore >= losingTeamScore + 2) {
                handleGameWon(winningTeam, losingTeam);
            }
        }
        else {
            if (winningTeamScore == Match.getCurrentGameWinScore()) {
                handleGameWon(winningTeam, losingTeam);
            }
        }
    }


    /**
     * Handle if a team has won a game or match
     * @param winningTeam   The winning team of the game or match
     * @param losingTeam    The losing team of the game or match
     */
    private static void handleGameWon(TeamView winningTeam, TeamView losingTeam) {
        //The game has been won
        winningTeam.getTeam().increaseGamesWon();

        if (winningTeam.getTeam().getGamesWon() >= Match.getGamesToWin()) {

            //The team registering a score has won the MATCH
            MatchWinnerView winnerView = new MatchWinnerView(winningTeam.getTeam());
            Main.getRoot().getChildren().set(0, winnerView.getView());
        } else {
            //The game is over, but the match is still going
            GameWinnerView gameWinnerView = new GameWinnerView(winningTeam.getTeam(), losingTeam.getTeam());
            Main.getRoot().getChildren().set(0, gameWinnerView.getView());
            Thread thread = displayGameWinnerView(gameWinnerView);
            thread.start();
        }
    }

    /**
     * Create a new thread to display the Game Winner page and count down to the next game
     * @param winnerView    The GameWinnerView to display
     * @return  The new thread
     */
    private static Thread displayGameWinnerView(GameWinnerView winnerView) {
        return new Thread(() -> {
            int i = 2;
            while (i >= 0) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                Platform.runLater(() -> {
                    //Each second, count down and update the view
                    winnerView.decrementSeconds();
                    Main.getRoot().getChildren().set(0, winnerView.getView());
                });
                i--;
            }
            Platform.runLater(() -> {
                //After 3 seconds, diplay a new GameView with preserved Match settings
                resetGame();

                GameView newGameView = new GameView();

                //Scoreboards of type 'switch' switch sides after each game
                if(Match.getType().equals("switch")) {
                    newGameView.reverseTeams();
                }

                Main.getRoot().getChildren().set(0, newGameView.getView());
            });
        });
    }

    /**
     * Set match information to prepare for a new game
     */
    private static void resetGame() {
        Match.getTeamOne().setScore(0);
        Match.getTeamTwo().setScore(0);
        Match.nextGame();
    }

    /**
     * Reset Match state entirely
     */
    public static void restartScoreboard() {
        Match.getTeamOne().setScore(0);
        Match.getTeamOne().setGamesWon(0);
        Match.getTeamTwo().setScore(0);
        Match.getTeamTwo().setGamesWon(0);
        Match.setCurrentGame(0);
    }

}

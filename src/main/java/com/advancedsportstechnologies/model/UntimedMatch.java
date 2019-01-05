package com.advancedsportstechnologies.model;

/**
 * Created by mattw on 1/5/2019.
 */
public class UntimedMatch extends Match {
    private static int gamesToWin;
    private static int maxGames = 1;
    private static int currentGame = 0;
    private static boolean winByTwo = false;
    private static int[] gameScores = new int[1];

    /**
     * Retrieve the number of games for this match. Sent via Bluetooth.
     * @return  The number of games for this match.
     */
    public static int getMaxGames() { return maxGames; }

    /**
     * Set the number of games in this match from Bluetooth message
     * @param games The number of games in this Match
     */
    public static void setMaxGames(int games) { maxGames = games; }

    /**
     * Get the current game being played in the match
     * @return The current game being played in the match
     */
    public static int getCurrentGame() { return currentGame; }

    /**
     * Set the current game being played in the match
     * @param game The current game being played in the match
     */
    public static void setCurrentGame(int game) { currentGame = game; }

    /**
     * Increment the match's current game when each game has completed
     */
    public static void nextGame() { currentGame = currentGame + 1; }

    /**
     * Retrieve the number of games required to win the match. Sent via Bluetooth.
     * @return  The number of games required to win the match.
     */
    public static int getGamesToWin() { return gamesToWin; }

    /**
     * Set the number of games required to win the match from Bluetooth message
     * @param win   The number of games required to win the match
     */
    public static void setGamesToWin(int win) { gamesToWin = win; }

    /**
     * Get the required score to win the current game in this match. Sent via Bluetooth.
     * @return The required score to win the current game in this match.
     */
    public static int getCurrentGameWinScore() { return gameScores[currentGame]; }

    /**
     * Set the required score to win each game in this match from Bluetooth message
     * @param scores    The required scores to win each game in this match.
     */
    public static void setGameScores(int[] scores) { gameScores = scores; }

    /**
     * Retrieve this match's Win By Two status
     * @return This match's Win By Two status
     */
    public static boolean isWinByTwo() { return winByTwo; }

    /**
     * Set win by 2 configuration sent from Bluetooth message
     * @param winByTwoStatus Boolean value indicating whether the winning team must win by 2
     */
    public static void setWinByTwo(boolean winByTwoStatus) { winByTwo = winByTwoStatus; }


}

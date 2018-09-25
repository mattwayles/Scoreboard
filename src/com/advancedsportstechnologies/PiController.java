package com.advancedsportstechnologies;

import com.advancedsportstechnologies.config.model.Match;
import com.advancedsportstechnologies.config.view.*;
import com.advancedsportstechnologies.modules.games.cornhole.view.CornholeMatchView;
import com.advancedsportstechnologies.modules.games.trampolinevolleyball.view.VolleyballMatchView;
import com.advancedsportstechnologies.modules.shared.view.TeamView;
import com.pi4j.io.gpio.*;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PiController {
    private final static GpioController gpio = GpioFactory.getInstance();
    final public static GpioPinDigitalInput controller2Down = gpio.provisionDigitalInputPin(RaspiPin.GPIO_02, PinPullResistance.PULL_UP);
    final public static GpioPinDigitalInput controller2Up = gpio.provisionDigitalInputPin(RaspiPin.GPIO_03, PinPullResistance.PULL_UP);
    final public static GpioPinDigitalInput reset = gpio.provisionDigitalInputPin(RaspiPin.GPIO_04, PinPullResistance.PULL_UP);
    final public static GpioPinDigitalInput controller1Down = gpio.provisionDigitalInputPin(RaspiPin.GPIO_05, PinPullResistance.PULL_UP);
    final public static GpioPinDigitalInput controller1Up = gpio.provisionDigitalInputPin(RaspiPin.GPIO_06, PinPullResistance.PULL_UP);

    private static MainView view;
    private static Match match;

    public static Match getMatch() { return match; }
    public static void setView(MainView mainView) { view = mainView; }

    public static void openGameFormatSelectView(String matchType) {
        removeEventListeners();
        GameFormatSelectView gameFormatSelectView = new GameFormatSelectView(matchType);
        view.setCurrentControl(gameFormatSelectView);
        view.updateConfigView(gameFormatSelectView.getGameFormatSelectView());

        //TODO: Create match with the correct scores based off selections!
        match = new Match(matchType);
    }

    public static void openTeamSelect() {
        openTeamSelect(match.getGameScores());
    }

    public static void openTeamSelect(int[] scores) {
        removeEventListeners();
        match.setGameScores(scores);
        TeamSelectView teamSelectView = new TeamSelectView(match.getType());
        view.setCurrentControl(teamSelectView);
        view.updateConfigView(teamSelectView.getTeamSelectView());
    }

    public static void startMatch(TeamSelectView teamSelect) {
        switch (match.getType()) {
            case "Cornhole":
                CornholeMatchView cornholeMatchView = new CornholeMatchView(teamSelect.getTeam1Select().getValue().toString(),
                        teamSelect.getTeam2Select().getValue().toString());
                view.setCurrentControl(cornholeMatchView);
                view.updateMainView(cornholeMatchView.getView());
                break;
            case "Trampoline Volleyball":
                VolleyballMatchView volleyballMatchView = new VolleyballMatchView(teamSelect.getTeam1Select().getValue().toString(),
                        teamSelect.getTeam2Select().getValue().toString());
                view.setCurrentControl(volleyballMatchView);
                view.updateMainView(volleyballMatchView.getView());
                break;
        }
    }

    public static void checkWinner(TeamView winner, TeamView loser) {
        if (winner.getScore() == match.getGameScores()[match.getCurrentGame()]) {
            winner.setGamesWon(winner.getGamesWon() + 1);
            if (winner.getGamesWon() == 1) {
                winner.getGamesWonImgs().getChildren().remove(0);

                GameWinnerView winnerView = new GameWinnerView(3, winner.getScore(), loser.getScore());
                winnerView.displayGameWinner(winner, match.getCurrentGame());
                view.updateMainView(winnerView.getView());

                Thread thread = createNewThread(winnerView, winner);
                thread.start();

                winner.getGamesWonImgs().getChildren().add(new ImageView(new Image("/img/gameWon.png")));
            }
            else if (winner.getGamesWon() == 2) {
                MatchWinnerView winnerView = new MatchWinnerView();
                winnerView.displayMatchWinner(winner, match.getCurrentGame());
                view.setCurrentControl(winnerView);
                view.updateMainView(winnerView.getView());
            }
            match.setCurrentGame(match.getCurrentGame() + 1);
        }
    }

    private static Thread createNewThread(GameWinnerView winnerView, TeamView winner) {
        return new Thread(() -> {
            int i = 2;
            while (i >= 0) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                Platform.runLater(() -> {
                    winnerView.decrementSeconds();
                    winnerView.displayGameWinner(winner, match.getCurrentGame());
                    view.updateMainView(winnerView.getView());
                });
                i--;
            }
            Platform.runLater(() -> {
                switch (match.getType()) {
                    case "Cornhole":
                        CornholeMatchView cornholeMatchView = (CornholeMatchView) view.getCurrentControl();
                        view.updateMainView(cornholeMatchView.getView());
                        break;
                    case "Trampoline Volleyball":
                        VolleyballMatchView volleyballMatchView = (VolleyballMatchView) view.getCurrentControl();
                        view.updateMainView(volleyballMatchView.getView());
                        break;
                }
            });

        });
    }

    private static void removeEventListeners() {
       controller1Up.removeAllListeners();
       controller1Down.removeAllListeners();
       controller2Up.removeAllListeners();
       controller1Down.removeAllListeners();
       reset.removeAllListeners();
    }

}

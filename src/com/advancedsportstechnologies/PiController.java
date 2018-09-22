package com.advancedsportstechnologies;

import com.advancedsportstechnologies.config.model.Match;
import com.advancedsportstechnologies.config.view.GameScoreSelectView;
import com.advancedsportstechnologies.config.view.MainView;
import com.advancedsportstechnologies.config.view.TeamSelectView;
import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

public class PiController {
    private final static GpioController gpio = GpioFactory.getInstance();
    final public static GpioPinDigitalInput controller2Reset = gpio.provisionDigitalInputPin(RaspiPin.GPIO_00, PinPullResistance.PULL_UP);
    final public static GpioPinDigitalInput controller2Down = gpio.provisionDigitalInputPin(RaspiPin.GPIO_02, PinPullResistance.PULL_UP);
    final public static GpioPinDigitalInput controller2Up = gpio.provisionDigitalInputPin(RaspiPin.GPIO_03, PinPullResistance.PULL_UP);
    final public static GpioPinDigitalInput controller1Reset = gpio.provisionDigitalInputPin(RaspiPin.GPIO_04, PinPullResistance.PULL_UP);
    final public static GpioPinDigitalInput controller1Down = gpio.provisionDigitalInputPin(RaspiPin.GPIO_05, PinPullResistance.PULL_UP);
    final public static GpioPinDigitalInput controller1Up = gpio.provisionDigitalInputPin(RaspiPin.GPIO_06, PinPullResistance.PULL_UP);

    private static final int[] CORNHOLE_DEFAULTS = new int[] {21, 21, 15};
    private static final int[] TRAMPOLINE_VOLLEYBALL_DEFAULTS = new int[] {25, 25, 15};
    private static MainView view;
    private static Match match;
    private static int gameNum;

    public static void setView(MainView mainView) { view = mainView; }

    public static void openGameScore(String matchType) {
        int[] defaultScores = getGameScoreDefaults(matchType);
        match = new Match(matchType, defaultScores);
        if (!matchType.equals("More Games Soon!"))
            openGameScoreSelectView();
    }

    public static void openTeamSelect(GameScoreSelectView gameScoreSelectView) {
        match.setGameScore(gameNum - 1, Integer.parseInt(gameScoreSelectView.getSelectionField().textProperty().getValue()));
        if (gameNum < match.getGameScores().length) {
            openGameScoreSelectView();
        }
        else {
            TeamSelectView teamSelectView = new TeamSelectView(match.getType());
            gameScoreSelectView.setCurrentControl(teamSelectView);
            gameScoreSelectView.updateConfigView(teamSelectView.getTeamSelectView());
        }
    }

    private static void openGameScoreSelectView() {
        GameScoreSelectView gameScoreSelectView = new GameScoreSelectView(gameNum, match.getGameScores()[gameNum]);
        gameScoreSelectView.setMainView(view.getMainView());
        view.setCurrentControl(gameScoreSelectView);
        view.updateConfigView(gameScoreSelectView.getGameScoreSelectView());
        gameNum++;
    }

    private static int[] getGameScoreDefaults(String matchType) {
        switch (matchType) {
            case "Cornhole":
                return CORNHOLE_DEFAULTS;
            case "Trampoline Volleyball":
                return TRAMPOLINE_VOLLEYBALL_DEFAULTS;
            default:
                return new int[] {21, 21, 15};
        }
    }
}

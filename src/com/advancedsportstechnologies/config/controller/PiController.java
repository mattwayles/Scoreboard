package com.advancedsportstechnologies.config.controller;

import com.advancedsportstechnologies.config.model.Match;
import com.advancedsportstechnologies.config.view.*;
import com.pi4j.io.gpio.*;

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
    public static MainView getView() { return view; }
    public static void setView(MainView mainView) { view = mainView; }

    public static void openGameSelectView() {
        Controller.openGameSelectView();
    }
    
    public static void openGameFormatSelectView(String matchType, int[][] scores) {
        removeEventListeners();
        Controller.openGameFormatSelectView(matchType, scores);
    }

    public static void openTimedGameFormatSelectView(String matchType, int[] times) {
        removeEventListeners();
        Controller.openTimedGameFormatSelectView(matchType, times);
    }

    public static void openTeamSelect() {
        openTeamSelect(match.getGameScores());
    }

    public static void openTeamSelect(int[] scores) {
        removeEventListeners();
        Controller.openTeamSelect(scores);
    }

    public static void startMatch(TeamSelectView teamSelect) {
        Controller.startMatch(teamSelect);
    }
    
    public static void easterEgg(String location) {
       Controller.easterEgg(location);
    }


    public static void removeEventListeners() {
        controller1Up.removeAllListeners();
        controller1Down.removeAllListeners();
        controller2Up.removeAllListeners();
        controller1Down.removeAllListeners();
        reset.removeAllListeners();
    }
}

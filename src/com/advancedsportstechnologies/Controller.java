package com.advancedsportstechnologies;

import com.advancedsportstechnologies.config.controller.ConfigController;
import com.advancedsportstechnologies.config.model.Match;
import com.advancedsportstechnologies.config.view.MainView;
import com.advancedsportstechnologies.modules.games.cornhole.controller.CornholeController;
import com.advancedsportstechnologies.modules.games.trampolinevolleyball.controller.VolleyballController;
import com.advancedsportstechnologies.modules.shared.view.TeamView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.util.HashSet;
import java.util.Set;

public class Controller extends Run {
    protected static Match match;
    private static long keyPressTime;
    private static KeyCode lastKeyPressed;
    private static String lastId;
    private static Set<KeyCode> keysDown = new HashSet<>();

    void routeKeyPress(KeyEvent e, MainView view) {
        String id = view.getCurrentControl().getId();
        lastId = id;

        keysDown.add(e.getCode());
        if (!keysDown.contains(e.getCode()) && e.getCode() != lastKeyPressed) {
            lastKeyPressed = e.getCode();
            keyPressTime = System.currentTimeMillis();
        }


        switch (id) {
            case "gameSelect":
               ConfigController.gameSelection(e, view);
                break;
            case "gameScoreSelect":
                ConfigController.scoreSelection(e, view);
                break;
            case "teamSelect":
                ConfigController.teamSelection(e, view);
                break;
            case "cornhole":
                CornholeController.changeScore(e, view, match);
                break;
            case "volleyball":
                VolleyballController.changeScore(e, view, match);
        }
    }

    void releaseKey(KeyEvent e, MainView view) {
        if (keysDown.contains(KeyCode.A) && keysDown.contains(KeyCode.Z)) {
            if (System.currentTimeMillis() - keyPressTime >= 3000L) {
                if (view.getCurrentControl().getId().equals("matchWinner")) {
                    CornholeController.easterEgg(view);
                }
            }
        } else if (keysDown.contains(KeyCode.Q) || keysDown.contains(KeyCode.W)) {
            String id = view.getCurrentControl().getId();
            if (!id.equals("gameSelect") && !id.equals("gameScoreSelect") && !id.equals("teamSelect") && !lastId.equals("teamSelect")) {
                restoreState();
                if (System.currentTimeMillis() - keyPressTime >= 3000L) {
                    close();
                    start(new Stage());
                }
                else {
                    ConfigController.restartMatch(view);
                }
            }
        }
        keyPressTime = System.currentTimeMillis();
        keysDown.remove(e.getCode());
    }

    private void restoreState() {
        ConfigController.setGameNum(0);
        match.setCurrentGame(0);
        TeamView.resetCount();
    }
}

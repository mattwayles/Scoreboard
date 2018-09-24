package com.advancedsportstechnologies.config.model;

import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGNode;
import javafx.scene.Node;

public class Match {
    private static final int[][] CORNHOLE_DEFAULTS = new int[][] {{21, 21, 15}, {21, 21, 21}};
    private static final int[][] TRAMPOLINE_VOLLEYBALL_DEFAULTS = new int[][] {{25, 25, 15}, {25, 25, 25}};

    private String type;
    private String format;
    private int currentGame;
    private int[] gameScores;

    public Match(String type) {
        this.type = type;
        this.currentGame = 0;
    }

    public String getType() { return this.type; }

    public String getFormat() { return this.format; }

    public int getCurrentGame() { return currentGame; }

    public void setCurrentGame(int game) { this.currentGame = game; }

    public int[] getGameScores() { return this.gameScores; }

    public void setFormat(String format) {
        this.format = format;
        switch (type) {
            case "Cornhole":
                this.gameScores = format.equals("Regular") ? CORNHOLE_DEFAULTS[0] : CORNHOLE_DEFAULTS[1];
            case "Trampoline Volleyball":
                this.gameScores = format.equals("Regular") ? TRAMPOLINE_VOLLEYBALL_DEFAULTS[0] : TRAMPOLINE_VOLLEYBALL_DEFAULTS[1];
            default:
                this.gameScores = new int[]{21, 21, 15};
        }
    }
}

package main.java.com.socialsportstechnology.config.model;

import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGNode;
import javafx.scene.Node;

public class Match extends Node {
    private String type;
    private int currentGame;
    private int[] gameScores;

    protected Match() {
        this.gameScores = new int[3];
    }

    public Match(String type, int[] defaultScores) {
        this.type = type;
        this.gameScores = defaultScores;
        this.currentGame = 0;
    }

    public String getType() { return this.type; }

    public int getCurrentGame() { return currentGame; }

    public void setCurrentGame(int game) { this.currentGame = game; }

    public int[] getGameScores() { return this.gameScores; }

    public void setGameScore(int gameNum, int score) { this.gameScores[gameNum] = score; }

    @Override
    protected NGNode impl_createPeer() {
        return null;
    }

    @Override
    public BaseBounds impl_computeGeomBounds(BaseBounds bounds, BaseTransform tx) {
        return null;
    }

    @Override
    protected boolean impl_computeContains(double localX, double localY) {
        return false;
    }

    @Override
    public Object impl_processMXNode(MXNodeAlgorithm alg, MXNodeAlgorithmContext ctx) {
        return null;
    }
}

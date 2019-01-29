package com.advancedsportstechnologies.view.texteffects;

import javafx.animation.ScaleTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class Scale {
    private static ScaleTransition scaleTransition;

    static {
        scaleTransition = new ScaleTransition();
    }

    /**
     * Play a scale transition
     * @param node  The node to scale
     * @param duration  The length of the scale transition
     * @param x The X scale value
     * @param y The Y scale value
     */
    public static void play(Node node, int duration, double x, double y) {
        scaleTransition.setDuration(Duration.millis(duration));
        scaleTransition.setNode(node);
        scaleTransition.setByY(y);
        scaleTransition.setByX(x);
        scaleTransition.setCycleCount(2);
        scaleTransition.setAutoReverse(true);
        scaleTransition.play();
    }
}

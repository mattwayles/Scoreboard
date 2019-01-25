package com.advancedsportstechnologies.view.texteffects;

import com.advancedsportstechnologies.model.Match;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class Rotate {
    private static ScaleTransition scaleTransition;

    static {
        scaleTransition = new ScaleTransition();
    }

    /**
     * Play a scale transition
     * @param node The node to rotate
     */
    public static void play(Node node) {
        Thread thread = new Thread(() -> {
            long startTime = System.currentTimeMillis();
            while (Match.isActive()) {
                if (System.currentTimeMillis() - startTime > 30000) {
                    RotateTransition rotateTransition = new RotateTransition();
                    ScaleTransition scaleTransition = new ScaleTransition();
                    rotateTransition.setDuration(Duration.millis(2000));
                    scaleTransition.setDuration(Duration.millis(1000));
                    rotateTransition.setNode(node);
                    scaleTransition.setNode(node);
                    rotateTransition.setByAngle(360);
                    scaleTransition.setByX(-.3);
                    scaleTransition.setByY(-.3);
                    rotateTransition.setCycleCount(2);
                    scaleTransition.setCycleCount(4);
                    rotateTransition.setAutoReverse(true);
                    scaleTransition.setAutoReverse(true);
                    scaleTransition.play();
                    rotateTransition.play();
                    startTime = System.currentTimeMillis();
                    }
                    }
        });
        thread.start();
    }

    public static void instaPlay(Node node) {
        RotateTransition rotateTransition = new RotateTransition();
        ScaleTransition scaleTransition = new ScaleTransition();
        rotateTransition.setDuration(Duration.millis(2000));
        scaleTransition.setDuration(Duration.millis(1000));
        rotateTransition.setNode(node);
        scaleTransition.setNode(node);
        rotateTransition.setByAngle(360);
        scaleTransition.setByX(-.3);
        scaleTransition.setByY(-.3);
        rotateTransition.setCycleCount(2);
        scaleTransition.setCycleCount(4);
        rotateTransition.setAutoReverse(true);
        scaleTransition.setAutoReverse(true);
        scaleTransition.play();
        rotateTransition.play();
    }
}

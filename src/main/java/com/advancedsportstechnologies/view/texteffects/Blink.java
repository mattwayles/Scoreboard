package com.advancedsportstechnologies.view.texteffects;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Create a blinking text animation on game point or match point
 */
public class Blink {
    private static Map<Node, Timeline> timelines = new HashMap<>();

    /**
     * Play a new blink animation
     * @param label The label to apply the animation to
     */
    public static void play(Node label) {
        Timeline timeline;

        if (label instanceof Label) { //Labels should transition text color
            Label scoreLabel = (Label) label;
            timeline = new Timeline(new KeyFrame(Duration.seconds(0.5), evt -> scoreLabel.setTextFill(Color.RED)),
                    new KeyFrame(Duration.seconds(1), evt ->scoreLabel.setTextFill(Color.BLACK)));
        }
        else { //Text should transition opacity
            timeline = new Timeline(new KeyFrame(Duration.seconds(0.5), evt -> label.setVisible(false)),
                    new KeyFrame(Duration.seconds(1), evt -> label.setVisible(true)));
        }
        timeline.setCycleCount(Animation.INDEFINITE);
        timelines.put(label, timeline);
        timeline.play();
    }

    /**
     * Stop a blink animation applied to a provided label
     * @param label The label containing the animation to stop
     */
    public static void stop(Node label) {
        Timeline timeline = timelines.get(label);
        if (timeline != null) {
            timeline.stop();
        }

        //Set the label back to default
        if (label instanceof Label) {
            Label scoreLabel = (Label) label;
            scoreLabel.setTextFill(Color.RED);
        }
        else {
            label.setVisible(true);
        }
    }

    /**
     * Remove all blink animations when game/match is restarted
     */
    public static void reset() {
        timelines.values().forEach(Timeline::stop);
        timelines.clear();
    }
}

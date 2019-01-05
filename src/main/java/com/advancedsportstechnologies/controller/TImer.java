package com.advancedsportstechnologies.controller;

import javafx.application.Platform;
import javafx.scene.control.Label;

import java.util.concurrent.TimeUnit;

/**
 * Create a Timer
 */
public class Timer {

    private static int current;

    public static void startCountdown(int seconds, Label updateLabel) {
        current = seconds;
        Thread thread = new Thread(() -> {
            while (current > 0) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                Platform.runLater(() -> updateLabel.textProperty().setValue(String.format("%d:%02d",
                        TimeUnit.SECONDS.toMinutes(current),
                        TimeUnit.SECONDS.toSeconds(current) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.SECONDS.toMinutes(current))
                )));
                current--;
            }
        });
        thread.start();
    }
}

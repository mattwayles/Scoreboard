package com.advancedsportstechnologies.modules.shared.model;

import javafx.application.Platform;
import javafx.scene.control.Label;

import java.util.concurrent.TimeUnit;

public class Timer {
    private static int current;

    public static void start(int seconds, Label updateLabel) {
        Thread thread = new Thread(() -> {
            while (current <= seconds) {
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
               current++;
            }
        });
        thread.start();
    }
}

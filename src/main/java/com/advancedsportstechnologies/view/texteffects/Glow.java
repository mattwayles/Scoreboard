package com.advancedsportstechnologies.view.texteffects;

import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;

/**
 * Produce glowing text, specifically for the Glow theme
 */
public class Glow {

    /**
     * Return a glowing dropshadow effect
     * @param color The color of the dropshadow
     * @return The glowing dropshadow effect
     */
    public static DropShadow setGlow(Color color) {
        DropShadow dropShadow = new DropShadow();
        dropShadow.setBlurType(BlurType.THREE_PASS_BOX);
        dropShadow.setRadius(20);
        dropShadow.setSpread(0.6);
        dropShadow.setOffsetX(2);
        dropShadow.setOffsetY(2);
        dropShadow.setColor(color);

        return dropShadow;
    }
}

package com.advancedsportstechnologies.controller;

import com.pi4j.io.gpio.*;

public class PiController {
    private final static GpioController gpio = GpioFactory.getInstance();
    final public static GpioPinDigitalInput controller2Down = gpio.provisionDigitalInputPin(RaspiPin.GPIO_05, PinPullResistance.PULL_UP);
    final public static GpioPinDigitalInput controller2Up = gpio.provisionDigitalInputPin(RaspiPin.GPIO_04, PinPullResistance.PULL_UP);
    final public static GpioPinDigitalInput reset = gpio.provisionDigitalInputPin(RaspiPin.GPIO_03, PinPullResistance.PULL_UP);
    final public static GpioPinDigitalInput controller1Down = gpio.provisionDigitalInputPin(RaspiPin.GPIO_02, PinPullResistance.PULL_UP);
    final public static GpioPinDigitalInput controller1Up = gpio.provisionDigitalInputPin(RaspiPin.GPIO_00, PinPullResistance.PULL_UP);

    final private static int DEBOUNCE_MS = 10;

    public static void setDebounce() {
        controller2Down.setDebounce(DEBOUNCE_MS);
        controller2Up.setDebounce(DEBOUNCE_MS);
        controller1Down.setDebounce(DEBOUNCE_MS);
        controller1Up.setDebounce(DEBOUNCE_MS);
        reset.setDebounce(DEBOUNCE_MS);
    }

    public static void removeEventListeners() {
        controller1Up.removeAllListeners();
        controller1Down.removeAllListeners();
        controller2Up.removeAllListeners();
        controller1Down.removeAllListeners();
        reset.removeAllListeners();
    }
}

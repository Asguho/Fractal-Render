package com.example;

import processing.core.*;

class IntInputField extends InputField {
    String value;

    IntInputField(PApplet p, String label, int x, int y, String value, boolean showValue) {
        super(p, label, x, y, showValue);
        this.value = value;
    }

    int getValue() {
        return Integer.parseInt(value);
    }

    void reset() {
        value = "0";
    }

    void draw() {
        System.out.println("IntInputField draw: " + label);
        // p.fill(0);
        drawLabelAndValue(value);
        checkPressed(value);
        checkInput();
    }

    void checkInput() {
        if (pressed == false)
            return;
        if (p.keyPressed) {
            if (p.keyPressed != keyPressedLastFrame) {
                if (p.key == PConstants.ENTER) {
                    pressed = false;
                    return;
                }
                if (p.key == PConstants.BACKSPACE) {
                    value = value.substring(0, value.length() - 1);
                    if (value.length() == 0)
                        value = "0";
                }
                if (p.key >= '0' && p.key <= '9') {
                    value += p.key;
                    if (value.length() > 0 && value.charAt(0) == '0')
                        value = value.substring(1, value.length());
                }
            }
            p.keyPressed = keyPressedLastFrame;
        }
    }
}
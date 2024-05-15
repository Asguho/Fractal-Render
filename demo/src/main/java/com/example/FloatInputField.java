package com.example;

import processing.core.*;

class FloatInputField extends InputField {
    String value;

    FloatInputField(PApplet p, String label, int x, int y, String value, boolean showValue) {
        super(p, label, x, y, showValue);
        this.value = value;
    }

    float getValue() {
        return Float.parseFloat(value);
    }

    void reset() {
        value = "0";
    }

    void draw() {
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
                    if (value.length() == 0 || value.equals("-"))
                        value = "0";
                }
                if (p.key >= '0' && p.key <= '9') {
                    value += p.key;
                    if (value.length() > 0 && value.charAt(0) == '0' && value.charAt(1) != '.') {
                        value = value.substring(1, value.length());
                    }
                    // if -0 then remove the 0
                    if (value.length() > 1 && value.charAt(0) == '-' && value.charAt(1) == '0'
                            && value.charAt(2) != '.') {
                        value = "-" + value.substring(2, value.length());
                    }
                }
                if (p.key == '.') {
                    if (!value.contains(".")) {
                        value += p.key;
                    }
                }
                // if (p.key == '-') then remove the first character if it is a '-' or add a '-'
                // if it is not
                if (p.key == '-') {
                    if (value.length() > 0 && value.charAt(0) == '-') {
                        value = value.substring(1, value.length());
                    } else {
                        value = "-" + value;
                    }
                }
            }
            p.keyPressed = keyPressedLastFrame;
        }

    }
}
package com.example;

import processing.core.*;

class InputField {
    String label;
    int x;
    int y;
    boolean pressed = false;
    boolean mousePressedLastFrame = false;
    boolean keyPressedLastFrame = false;
    boolean showValue = true;
    int margin = 20;
    PApplet p;

    InputField(PApplet p, String label, int x, int y, boolean showValue) {
        this.p = p;
        this.label = label;
        this.x = x;
        this.y = y;
        this.showValue = showValue;
    }

    boolean isHovering(int x1, int y1, int x2, int y2) {
        return (p.mouseX > x1 && p.mouseX < x2 && p.mouseY > y1 && p.mouseY < y2);
    }

    void drawLabelAndValue(String value) {
        p.textSize(20);
        p.noStroke();
        p.fill(0);
        p.text(label + ": " + (showValue ? value : "") + ((pressed && p.frameCount / 20 % 2 == 0) ? "_" : ""), x, y);
    }

    int getWidth(String value) {
        return (int) p.textWidth(label + ": " + (showValue ? value : ""));
    }

    void checkPressed(String value) {
        if (isHovering(x - margin, y - margin, x + getWidth(value) + margin, y + margin / 2)) {
            p.stroke(255);
            p.strokeWeight(5);
            p.noFill();
            p.rect(x - margin, y - margin, getWidth(value) + margin * 2, (float) (margin * 1.5));
            if (p.mousePressed && (mousePressedLastFrame != p.mousePressed)) {
                pressed = !pressed;
            }
            mousePressedLastFrame = p.mousePressed;
        } else {
            if (p.mousePressed) {
                pressed = false;
            }
        }
    }
}

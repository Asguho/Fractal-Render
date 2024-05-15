package com.example;

import processing.core.*;

public class Button extends UIElement {
    private ClickListener listener;
    private boolean keyPressedLastFrame = false;
    private String label;

    Button(PApplet p, String label, int x, int y, int width, int height, ClickListener listener) {
        super(p, x, y, width, height);
        this.listener = listener;
        this.label = label;
    }

    private boolean isHovering() {
        if (p.mouseX > position.x && p.mouseX < position.x + size.x && p.mouseY > position.y
                && p.mouseY < position.y + size.y) {
            return true;
        } else {
            return false;
        }
    }

    private void onClick() {
        if (p.mousePressed && isHovering() && !keyPressedLastFrame) {
            keyPressedLastFrame = true;
            listener.onClick();
        } else if (!p.mousePressed) {
            keyPressedLastFrame = false;
        }
    }

    public interface ClickListener {
        void onClick();
    }

    public void draw() {
        p.noFill();
        p.stroke(50);
        p.strokeWeight(5);
        p.rect(position.x, position.y, size.x, size.y);
        p.text(label, position.x + 5, position.y + size.y - 5);
        onClick();
    }
}

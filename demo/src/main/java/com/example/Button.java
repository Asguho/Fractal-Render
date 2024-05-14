package com.example;

import processing.core.*;

public class Button {
    private PApplet p;
    private int x, y, width, height;
    private ClickListener listener;
    private String label;

    Button(PApplet p, String label, int x, int y, int width, int height, ClickListener listener) {
        this.p = p;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.listener = listener;
        this.label = label;
    }

    private boolean isHovering() {
        if (p.mouseX > x && p.mouseX < x + width && p.mouseY > y && p.mouseY < y + height) {
            return true;
        } else {
            return false;
        }
    }

    private void onClick() {
        if (p.mousePressed && isHovering()) {
            listener.onClick();
        }
    }

    public interface ClickListener {
        void onClick();
    }

    public void draw() {
        p.noFill();
        p.stroke(255);
        p.strokeWeight(5);
        p.rect(x, y, width, height);
        p.text(label, x + 5, y + height - 5);
        onClick();
    }
}

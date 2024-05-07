package com.example;

import processing.core.*;

public class Button {
    PApplet p;

    Button(PApplet p) {
        this.p = p;
    }

    boolean isHovering(int x1, int y1, int x2, int y2) {
        if (p.mouseX > x1 && p.mouseX < x2 && p.mouseY > y1 && p.mouseY < y2) {
            return true;
        } else {
            return false;
        }
    }

}

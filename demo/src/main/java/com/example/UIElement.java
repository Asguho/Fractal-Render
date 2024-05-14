package com.example;

import processing.core.PApplet;
import processing.core.PVector;

public class UIElement {
    PApplet p;
    PVector position = new PVector(0, 0);
    PVector size = new PVector(0, 0);

    UIElement(PApplet p, int x, int y, int width, int height) {
        this.p = p;
        this.position.x = x;
        this.position.y = y;
        this.size.x = width;
        this.size.y = height;
    }
}
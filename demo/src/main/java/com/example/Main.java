package com.example;

import processing.core.PApplet;

public class Main extends processing.core.PApplet {
    FractalCanvas fractalCanvas = new FractalCanvas(this, 800, 800, 1.0f, 0.0f, 0.0f, 100);

    public static void main(String[] args) {
        PApplet.main("com.example.Main", args);
    }

    public void settings() {
        size(1200, 800);
    }

    public void setup() {
    }

    public void draw() {
        background(200);
        fractalCanvas.draw();
        fill(0);
        text("FPS: " + frameRate, 10, 10);
    }

    public void keyPressed() {
        fractalCanvas.keyPressed(key);
    }

}

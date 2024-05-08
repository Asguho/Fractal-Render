package com.example;

import processing.core.PApplet;

public class Main extends processing.core.PApplet {
    FractalCanvas fractalCanvas = new FractalCanvas(this, 800, 800, 1.0f, 0.0f, 0.0f);
    IntInputField maxIterationsInputField = new IntInputField(this, "Max Iterations", 10, 60, "100", true);
    IntInputField resolutionFactorInputField = new IntInputField(this, "resolutionFactor", 10, 90, "1", true);

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
        text("FPS: " + frameRate, 10, 20);
        maxIterationsInputField.draw();
        resolutionFactorInputField.draw();

        fractalCanvas.setMaxIterations(maxIterationsInputField.getValue());
        fractalCanvas.setResolutionFactor(resolutionFactorInputField.getValue());
    }

    public void keyPressed() {
        fractalCanvas.keyPressed(key);
    }

}

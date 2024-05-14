package com.example;

import com.example.Button.ClickListener;
import processing.core.PApplet;

public class UIManager {
    private PApplet p;
    public FractalCanvas fractalCanvas;
    private IntInputField maxIterationsInputField;
    private IntInputField resolutionFactorInputField;
    private Button calculateFractalDimensionButton;

    UIManager(PApplet p) {
        this.p = p;
        fractalCanvas = new FractalCanvas(p, 400, 0, 800, 800, 1.0f, 0.0f, 0.0f);
        maxIterationsInputField = new IntInputField(p, "Max Iterations", 10, 60, "100", true);
        resolutionFactorInputField = new IntInputField(p, "resolutionFactor", 10, 90, "1", true);
        calculateFractalDimensionButton = new Button(p, "CalculateFractalDimension", 10, 210, 230, 30,
                new ClickListener() {
                    @Override
                    public void onClick() {
                        fractalCanvas.renderCalculateFractalDimension();
                    }
                });
        ;
    }

    public void draw() {

        p.background(200);
        drawFPS();

        fractalCanvas.draw();
        maxIterationsInputField.draw();
        resolutionFactorInputField.draw();
        calculateFractalDimensionButton.draw();
        updateUIValues();

    }

    private void drawFPS() {
        p.fill(0);
        p.text("FPS: " + p.frameRate, 10, 20);
    }

    private void updateUIValues() {
        fractalCanvas.setMaxIterations(maxIterationsInputField.getValue());
        fractalCanvas.setResolutionFactor(resolutionFactorInputField.getValue());
    }
}
package com.example;

import com.example.Button.ClickListener;
import processing.core.PApplet;

public class UIManager {
    private PApplet p;
    public FractalCanvas fractalCanvas;
    private IntInputField maxIterationsInputField;
    private IntInputField resolutionFactorInputField;
    private Button calculateFractalDimensionButton;
    private FloatInputField startingXInputField;
    private FloatInputField startingYInputField;

    UIManager(PApplet p) {
        this.p = p;
        fractalCanvas = new FractalCanvas(p, 400, 0, 800, 800, 1, 0.0f, 0.0f);
        maxIterationsInputField = new IntInputField(p, "Max Iterations", 10, 100, "100", true);
        resolutionFactorInputField = new IntInputField(p, "resolutionFactor", 10, 130, "1", true);
        startingXInputField = new FloatInputField(p, "Starting X", 10, 160, "0", true);
        startingYInputField = new FloatInputField(p, "Starting Y", 10, 190, "0", true);
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

        updateUIValues();

        fractalCanvas.draw();
        maxIterationsInputField.draw();
        resolutionFactorInputField.draw();
        calculateFractalDimensionButton.draw();
        startingXInputField.draw();
        startingYInputField.draw();

    }

    private void drawFPS() {
        p.fill(0);
        p.text("FPS: " + p.frameRate, 10, 20);
    }

    private void updateUIValues() {
        p.text("MagnificationFactor: " + fractalCanvas.getMagnificationFactor() + "x", 10, 50);
        fractalCanvas.setMaxIterations(maxIterationsInputField.getValue());
        fractalCanvas.setResolutionFactor(resolutionFactorInputField.getValue());
        fractalCanvas.setStartingX((double) startingXInputField.getValue());
        fractalCanvas.setStartingY((double) startingYInputField.getValue());
    }
}

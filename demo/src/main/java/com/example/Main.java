package com.example;

import processing.core.PApplet;

public class Main extends processing.core.PApplet {
    private UIManager uiManager = new UIManager(this);

    public static void main(String[] args) {
        PApplet.main("com.example.Main");
    }

    public void settings() {
        size(1200, 800);
    }

    public void draw() {
        uiManager.draw();
    }

    public void keyPressed() {
        uiManager.fractalCanvas.keyPressed(key);
    }

}

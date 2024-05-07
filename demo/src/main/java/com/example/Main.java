package com.example;

import processing.core.*;

public class Main extends processing.core.PApplet {
    int maxiterations = 100;
    float magnificationFactor = 400;
    float moveX = 0;
    float moveY = 0;

    public static void main(String[] args) {
        PApplet.main("com.example.Main", args);
    }

    public void settings() {
        size(1200, 800);
    }

    public void setup() {
        background(0);
    }

    public void draw() {
        clear();
        mandelbrot();

        text("FPS: " + frameRate, 10, 10);
    }

    // calculate a mandelbrot fractal
    public void mandelbrot() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                float zx = 0;
                float zy = 0;
                float cX = (x - width / 2) / magnificationFactor - moveX;
                float cY = (y - height / 2) / magnificationFactor - moveY;
                int iter = maxiterations;
                while (zx * zx + zy * zy < 4 && iter > 0) {
                    float tmp = zx * zx - zy * zy + cX;
                    zy = 2.0f * zx * zy + cY;
                    zx = tmp;
                    iter--;
                }
                stroke(iter);
                point(x, y);
            }
        }
    }

    public void keyPressed() {
        if (key == 'w') {
            moveY += 0.1;
        } else if (key == 's') {
            moveY -= 0.1;
        } else if (key == 'a') {
            moveX += 0.1;
        } else if (key == 'd') {
            moveX -= 0.1;
        } else if (key == 'r') {
            magnificationFactor += 25;
        } else if (key == 'f') {
            magnificationFactor -= 25;
        }
    }

}

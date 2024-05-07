package com.example;

import processing.core.*;

public class Main extends processing.core.PApplet {
    int maxiterations = 100;
    float magnificationFactor = 1;
    float moveX = 0;
    float moveY = 0;

    String lastImageInputs = "";
    PImage img;

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
        lastImageInputs = width + " " + height + " " + magnificationFactor + " " + moveX + " " + moveY + " "
                + maxiterations;
        if (lastImageInputs != width + " " + height + " " + magnificationFactor + " " + moveX + " " + moveY + " "
                + maxiterations) {
            img = mandelbrot(width, height, magnificationFactor, moveX, moveY, maxiterations);
        }
        image(img, 0, 0);

        fill(0);
        text("FPS: " + frameRate, 10, 10);
    }

    public PImage mandelbrot(int width, int height, float magnificationFactor, float moveX, float moveY,
            int maxiterations) {
        PImage img = createImage(width, height, RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                double zx = 0;
                double zy = 0;
                double cX = (x - width / 2) / (magnificationFactor * width / 4) - moveX;
                double cY = (y - height / 2) / (magnificationFactor * height / 4) - moveY;
                int iter = maxiterations;
                while (zx * zx + zy * zy < 4 && iter > 0) {
                    double tmp = zx * zx - zy * zy + cX;
                    zy = 2.0f * zx * zy + cY;
                    zx = tmp;
                    iter--;
                }
                img.pixels[x + y * width] = color(map(iter, 0, maxiterations, 0, 255));
            }
        }
        return img;
    }

    public void keyPressed() {
        if (key == 'w') {
            moveY += 0.1 / magnificationFactor;
        } else if (key == 's') {
            moveY -= 0.1 / magnificationFactor;
        } else if (key == 'a') {
            moveX += 0.1 / magnificationFactor;
        } else if (key == 'd') {
            moveX -= 0.1 / magnificationFactor;
        } else if (key == 'r') {
            magnificationFactor *= 2.0;
        } else if (key == 'f') {
            magnificationFactor /= 2.0;
        }
    }

}

package com.example;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

public class FractalCanvas {
    private int width, height, maxiterations;
    private float magnificationFactor, moveX, moveY;
    private PApplet p;
    String lastImageInputHash = "";
    PImage img;

    public FractalCanvas(PApplet p, int width, int height, float magnificationFactor, float moveX, float moveY,
            int maxiterations) {
        this.p = p;
        this.width = width;
        this.height = height;
        this.magnificationFactor = magnificationFactor;
        this.moveX = moveX;
        this.moveY = moveY;
        this.maxiterations = maxiterations;
    }

    public void draw() {
        if (!lastImageInputHash.equals(getInputHash())) {
            // && (p.frameRate > 30)
            System.err.println();
            img = mandelbrot();
            lastImageInputHash = getInputHash();
        }
        p.image(img, 400, 0);
    }

    public void keyPressed(char key) {
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

    private String getInputHash() {
        return width + " " + height + " " + magnificationFactor + " " + moveX + " " + moveY + " " + maxiterations;
    }

    private PImage mandelbrot() {
        PImage img = p.createImage(width, height, PConstants.RGB);
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
                img.pixels[x + y * width] = p.color(PApplet.map(iter, 0, maxiterations, 0, 255));
            }
        }
        return img;
    }
}

package com.example;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

public class FractalCanvas {
    private int width, height, maxIterations = 50, resolutionFactor = 2;
    private float magnificationFactor, moveX, moveY;
    private PApplet p;
    String lastImageInputHash = "";
    PImage img;

    public FractalCanvas(PApplet p, int width, int height, float magnificationFactor, float moveX, float moveY) {
        this.p = p;
        this.width = width;
        this.height = height;
        this.magnificationFactor = magnificationFactor;
        this.moveX = moveX;
        this.moveY = moveY;
    }

    public void setMaxIterations(int maxIterations) {
        this.maxIterations = PApplet.max(maxIterations, 1);
    }

    public void setResolutionFactor(int resolutionFactor) {
        this.resolutionFactor = PApplet.max(resolutionFactor, 1);
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
        }
        if (key == 's') {
            moveY -= 0.1 / magnificationFactor;
        }
        if (key == 'a') {
            moveX += 0.1 / magnificationFactor;
        }
        if (key == 'd') {
            moveX -= 0.1 / magnificationFactor;
        }
        if (key == 'r') {
            magnificationFactor *= 2.0;
        }
        if (key == 'f') {
            magnificationFactor /= 2.0;
        }
    }

    private String getInputHash() {
        return width + " " + height + " " + magnificationFactor + " " + moveX + " " + moveY + " " + maxIterations + " "
                + resolutionFactor;
    }

    private PImage mandelbrot() {
        PImage img = p.createImage(width / resolutionFactor, height / resolutionFactor, PConstants.RGB);
        for (int x = 0; x < img.width; x++) {
            for (int y = 0; y < img.height; y++) {
                double zx = 0;
                double zy = 0;
                double cX = (x - img.width / 2) / (magnificationFactor * img.width / 4) - moveX;
                double cY = (y - img.height / 2) / (magnificationFactor * img.height / 4) - moveY;
                if (x == 10) {
                    // p.printLn(cX + " " + cY + " " + magnificationFactor);
                    System.err.println(cX + " " + cY + " " + magnificationFactor);
                }
                int iter = maxIterations;
                while (zx * zx + zy * zy < 4 && iter > 0) {
                    double tmp = zx * zx - zy * zy + cX;
                    zy = 2.0f * zx * zy + cY;
                    zx = tmp;
                    iter--;
                }
                img.pixels[x + y * img.width] = p.color(PApplet.map(iter, 0, maxIterations, 0, 255));
            }
        }
        img.resize(width, height);
        return img;
    }
}

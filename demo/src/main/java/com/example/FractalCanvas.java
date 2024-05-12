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
            // img = mandelbrot();
            img = getChunksWithEdge(sobelEdgeDetection(blur(mandelbrot())), 5);
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

    private PImage sobelEdgeDetection(PImage inputImage) {
        PImage outputImage = p.createImage(inputImage.width, inputImage.height, PConstants.RGB);
        int[][] Gx = { { -1, 0, 1 }, { -2, 0, 2 }, { -1, 0, 1 } };
        int[][] Gy = { { 1, 2, 1 }, { 0, 0, 0 }, { -1, -2, -1 } };
        for (int x = 0; x < inputImage.width; x++) {
            for (int y = 0; y < inputImage.height; y++) {
                float sumX = 0;
                float sumY = 0;
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        int loc = (x + i - 1) + (y + j - 1) * inputImage.width;
                        if (loc >= 0 && loc < inputImage.pixels.length) {
                            sumX += p.red(inputImage.pixels[loc]) * Gx[i][j];
                            sumY += p.red(inputImage.pixels[loc]) * Gy[i][j];
                        }
                    }
                }
                float sum = PApplet.sqrt(sumX * sumX + sumY * sumY);
                outputImage.pixels[x + y * inputImage.width] = p.color(sum);
            }
        }
        return outputImage;
    }

    private PImage blur(PImage inputImage) {
        PImage outputImage = p.createImage(inputImage.width, inputImage.height, PConstants.RGB);
        int[][] blurMatrix = { { 1, 1, 1 }, { 1, 1, 1 }, { 1, 1, 1 } };
        for (int x = 0; x < inputImage.width; x++) {
            for (int y = 0; y < inputImage.height; y++) {
                float sum = 0;
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        int loc = (x + i - 1) + (y + j - 1) * inputImage.width;
                        if (loc >= 0 && loc < inputImage.pixels.length) {
                            sum += p.red(inputImage.pixels[loc]) * blurMatrix[i][j];
                        }
                    }
                }
                outputImage.pixels[x + y * inputImage.width] = p.color(sum);
            }
        }
        return outputImage;
    }

    private PImage getChunksWithEdge(PImage inputImage, int chunkSize) {
        PImage outputImage = p.createImage(inputImage.width, inputImage.height, PConstants.RGB);
        int numberOfChunks = 0;
        for (int x = 0; x < inputImage.width; x += chunkSize) {
            for (int y = 0; y < inputImage.height; y += chunkSize) {
                int sum = 0;
                for (int i = 0; i < chunkSize; i++) {
                    for (int j = 0; j < chunkSize; j++) {
                        int loc = (x + i) + (y + j) * inputImage.width;
                        if (loc >= 0 && loc < inputImage.pixels.length) {
                            sum += p.red(inputImage.pixels[loc]);
                        }
                    }
                }
                int avg = sum / (chunkSize * chunkSize);

                if (avg > 0) {
                    // System.err.println("avg > 0: " + avg);
                    numberOfChunks++;

                    for (int i = 0; i < chunkSize; i++) {
                        for (int j = 0; j < chunkSize; j++) {
                            int loc = (x + i) + (y + j) * inputImage.width;
                            if (loc >= 0 && loc < inputImage.pixels.length) {
                                outputImage.pixels[loc] = p.color(255);
                            }
                        }
                    }
                }
            }
        }
        System.err.println("numberOfChunks: " + numberOfChunks + ", chunksize: " + chunkSize);
        return outputImage;
    }
}

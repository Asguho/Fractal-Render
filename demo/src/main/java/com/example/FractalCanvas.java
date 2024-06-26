package com.example;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;
import processing.core.PVector;

public class FractalCanvas extends UIElement {
    private int maxIterations, resolutionFactor;
    double startingX = 0, startingY = 0;
    private long magnificationFactor;
    private float fractalDimension = 0;
    double moveX, moveY;
    private String lastImageInputHash = "";
    private PImage img;
    private boolean shouldDrawChunks = false;

    public FractalCanvas(PApplet p, int x, int y, int width, int height, long magnificationFactor, float moveX,
            float moveY) {
        super(p, x, y, width, height);
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

    public void setStartingX(double startingX) {
        this.startingX = startingX;
    }

    public void setStartingY(double startingY) {
        this.startingY = startingY;
    }

    public long getMagnificationFactor() {
        return magnificationFactor;
    }

    public void draw() {
        if (!lastImageInputHash.equals(getInputHash())) {
            img = mandelbrot(resolutionFactor);
            lastImageInputHash = getInputHash();
        }
        System.err.println(shouldDrawChunks);

        p.image(img, position.x, position.y);

        if (fractalDimension != 0)
            p.text("Fractal Dimension: " + fractalDimension, 10, 260);

        if (shouldDrawChunks)
            drawChunks(img);

    }

    public void enableDrawChunks() {
        shouldDrawChunks = !shouldDrawChunks;
    }

    private void drawChunks(PImage inputImage) {
        int chunkSize = 10;
        PVector[] chunks = getChunksWithEdge(sobelEdgeDetection(img), chunkSize);
        p.noFill();
        p.strokeWeight(1);
        p.stroke(255, 0, 0);
        for (int i = 0; i < chunks.length; i++) {
            p.rect(chunks[i].x + 400, chunks[i].y, chunkSize, chunkSize);
        }

    }

    public void renderCalculateFractalDimension() {
        fractalDimension = calculateFractalDimension(mandelbrot(1));
    }

    private float calculateFractalDimension(PImage inputImage) {
        float[] chunks = new float[10];
        float[] chunksSize = new float[10];
        for (int i = 0; i < 10; i++) {
            chunksSize[i] = (i + 1);
            chunks[i] = getAmountOfChunksWithEdge(sobelEdgeDetection(inputImage), (i + 1));
        }
        float[] x = new float[10];
        float[] y = new float[10];
        for (int i = 0; i < 10; i++) {
            x[i] = PApplet.log(1 / (float) chunksSize[i]);
            y[i] = PApplet.log((float) chunks[i]);
        }
        return linearRegression(x, y)[1];
    }

    private int getAmountOfChunksWithEdge(PImage inputImage, int chunkSize) {
        PVector[] chunks = getChunksWithEdge(sobelEdgeDetection(inputImage), chunkSize);
        return chunks.length;
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
            magnificationFactor = Math.max(magnificationFactor, (long) 1);

        }
    }

    private String getInputHash() {
        return size.x + " " + size.y + " " + magnificationFactor + " " + moveX + " " + moveY + " " + maxIterations + " "
                + resolutionFactor + " " + startingX + " " + startingY;
    }

    private PImage mandelbrot(int resolutionFactor) {
        PImage img = p.createImage((int) (size.x / (float) resolutionFactor), (int) (size.y / (float) resolutionFactor),
                PConstants.RGB);
        for (int x = 0; x < img.width; x++) {
            for (int y = 0; y < img.height; y++) {
                double zR = startingX;
                double zI = startingY;
                double cR = (double) ((double) x - (double) img.width / 2.0)
                        / (double) ((double) magnificationFactor * (double) img.width / 4.0) - moveX;
                double cI = (double) ((double) y - (double) img.height / 2.0)
                        / (double) ((double) magnificationFactor * (double) img.height / 4.0) - moveY;
                int iter = maxIterations;
                while (zR * zR + zI * zI < 4 && iter > 0) {
                    double tmp = zR * zR - zI * zI + cR;
                    zI = 2.0 * zR * zI + cI;
                    zR = tmp;
                    iter--;
                }
                img.pixels[x + y * img.width] = p.color(PApplet.map(iter, 0, maxIterations, 0, 255));
            }
        }
        img.resize((int) size.x, (int) size.y);
        return img;
    }

    // private PImage julia() {
    // PImage img = p.createImage((int) size.x / resolutionFactor, (int) size.y /
    // resolutionFactor, PConstants.RGB);
    // double cX = -0.7;
    // double cY = 0.27015;
    // for (int x = 0; x < img.width; x++) {
    // for (int y = 0; y < img.height; y++) {
    // double zR = (double) ((double) x - (double) img.width / 2)
    // / (double) ((double) magnificationFactor * (double) img.width / 4) - moveX;
    // double zI = (double) ((double) y - (double) img.height / 2)
    // / (double) ((double) magnificationFactor * (double) img.height / 4) - moveY;
    // int iter = maxIterations;
    // while (zR * zR + zI * zI < 4 && iter > 0) {
    // double tmp = zR * zR - zI * zI + cX;
    // zI = 2.0f * zR * zI + cY;
    // zR = tmp;
    // iter--;
    // }
    // img.pixels[x + y * img.width] = p.color(PApplet.map(iter, 0, maxIterations,
    // 0, 255));
    // }
    // }
    // img.resize((int) size.x, (int) size.y);
    // return img;
    // }

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

    private PVector[] getChunksWithEdge(PImage inputImage, int chunkSize) {
        PVector[] chunks = new PVector[0];
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

                if (avg > 255 / 4) {
                    numberOfChunks++;
                    PVector[] newChunks = new PVector[chunks.length + 1];
                    for (int i = 0; i < chunks.length; i++) {
                        newChunks[i] = chunks[i];
                    }
                    newChunks[chunks.length] = new PVector(x, y);
                    chunks = newChunks;
                }
            }
        }
        System.err.println("numberOfChunks: " + numberOfChunks + ", chunkSize: " + chunkSize);

        return chunks;
    }

    private float[] linearRegression(float[] x, float[] y) {
        float sumX = 0;
        float sumY = 0;
        float sumXY = 0;
        float sumX2 = 0;
        for (int i = 0; i < x.length; i++) {
            sumX += x[i];
            sumY += y[i];
            sumXY += x[i] * y[i];
            sumX2 += x[i] * x[i];
        }
        float a = (sumY * sumX2 - sumX * sumXY) / (x.length * sumX2 - sumX * sumX);
        float b = (x.length * sumXY - sumX * sumY) / (x.length * sumX2 - sumX * sumX);
        return new float[] { a, b };
    }

}

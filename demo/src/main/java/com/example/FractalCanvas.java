package com.example;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;
import processing.core.PVector;

public class FractalCanvas {
    private int width, height, maxIterations = 50, resolutionFactor = 2;
    private float magnificationFactor, moveX, moveY;
    private PApplet p;
    String lastImageInputHash = "";
    PImage img;
    int chunkSize = 5;

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

    public void setChunkSize(int chunkSize) {
        this.chunkSize = PApplet.max(chunkSize, 1);
    }

    public void draw() {
        if (!lastImageInputHash.equals(getInputHash())) {
            float dim = calcFrataIDimension();
            System.err.println("dim: " + dim);
            // && (p.frameRate > 30)
            // img = mandelbrot();
            // img = getChunksWithEdge(sobelEdgeDetection(blur(mandelbrot())), 5);
            // img = mandelbrot();
            // loop though chunks with edge

            // img = julia();
            lastImageInputHash = getInputHash();
            // // test linear regression
            // float[] x = { 1.0f, 2.0f, 3.0f, 4.0f, 5.0f };
            // float[] y = { 2.0f, 4.0f, 6.0f, 8.0f, 10.0f };
            // float[] result1 = linearRegression(x, y);
            // float[] result = calculateFractalDimension(img);
            // System.err.println("result1: " + result1[0] + ", " + result1[1]);
            // System.err.println("result: " + result[0] + ", " + result[1]);

        }
        // p.image(img, 400, 0);
        // drawChunks(img);

    }

    private void drawChunks(PImage inputImage) {
        PVector[] chunks = getChunksWithEdge(sobelEdgeDetection(img), chunkSize);
        p.noFill();
        p.strokeWeight(1);
        p.stroke(255, 0, 0);
        for (int i = 0; i < chunks.length; i++) {
            p.rect(chunks[i].x + 400, chunks[i].y, chunkSize, chunkSize);
        }

    }

    private float[] calculateFractalDimension(PImage inputImage) {
        float[] chunks = new float[10];
        float[] chunksSize = new float[10];
        for (int i = 1; i < 10; i++) {
            chunksSize[i] = i * 2;
            chunks[i] = getAmountOfChunksWithEdge(sobelEdgeDetection(img), i * 2);
        }
        float[] x = new float[10];
        float[] y = new float[10];
        for (int i = 1; i < 10; i++) {
            x[i] = PApplet.log(1 / (float) chunksSize[i]);
            y[i] = PApplet.log((float) chunks[i]);
            System.err.println(
                    "x: " + x[i] + ", y: " + y[i] + ", chunks: " + chunks[i] + ", chunksSize: " + chunksSize[i]
                            + " calc: "
                            + PApplet.log((float) chunks[i]) + ", " + (1 / (float) chunksSize[i]));
        }
        return linearRegression(x, y);
    }

    // public float calcFrataIDimension()
    // "Fratat": Unknown word.
    // long[] totatPixets
    // = new tong [18];
    // for (int scalingFactor = 18; scalingFactor 1; scalingFactor—) {
    // // 1600, 1000, scalingFactor, 100, 1.4, -1.2d, O.Od, 9d, Od
    // FractalParams params = new z:1.4d, -1.2d, oy:O.Od,
    // w:40G9,
    // h :4000,
    // scatingFactor) ;
    // Plmage img = drawmandetbrot(params) ;
    // img = twoToneFiIter (img) ;
    // Img = sobeIEdgeDetection (img) ;
    // + scalingFactor +
    // tong whitePixets = O
    // img. toadPixeIs ( ) ;
    // for (int x = O; x <
    // img.width; x++) {
    // png") ;
    // for (int y = 8; y < img.height; Y++) {
    // int c = img.pixets[x + y * img.width] ;
    // = 255 p.blue(c) = 255) {
    // if (p. red(c)
    // = 255 && p. green(c)
    // whitePixe1s ;
    // totatPixets [ scalingFactor
    // pixels:
    // float[] x = new
    // float[] y = new float[10];
    // for (int i = O; i < 18; i++) {
    // whitePixets ;
    // + whitePixets) ;
    // x[i] = PApptet.tog(1.Of / (float) (i + 1));
    // = totatPixets[i]);
    // float[] regression = linearRegression(x, y) ;
    // return regression [1];

    private float calcFrataIDimension() {
        long[] totalPixels = new long[10];
        for (int scalingFactor = 10; scalingFactor >= 1; scalingFactor--) {
            PImage img;
            img = sobelEdgeDetection(mandelbrot(scalingFactor));
            int whitePixels = 0;
            img.loadPixels();
            for (int x = 0; x < img.width; x++) {
                for (int y = 0; y < img.height; y++) {
                    int c = img.pixels[x + y * img.width];
                    if (p.red(c) == 255 && p.green(c) == 255 && p.blue(c) == 255) {
                        whitePixels++;
                    }
                }
            }
            totalPixels[scalingFactor - 1] = whitePixels;
            System.err.println("whitePixels: " + whitePixels);
        }
        float[] x = new float[10];
        float[] y = new float[10];
        for (int i = 0; i < 10; i++) {
            x[i] = PApplet.log(1.0f / (float) (i + 1));
            y[i] = PApplet.log((float) totalPixels[i]);
            System.err.println("x: " + x[i] + ", y: " + y[i] + ", totalPixels: " + totalPixels[i]);
        }
        float[] regression = linearRegression(x, y);
        return regression[0];
    }

    private float[] reverseFloatArray(float[] array) {
        float[] reversedArray = new float[array.length];
        for (int i = 0; i < array.length; i++) {
            reversedArray[array.length - 1 - i] = array[i];
        }
        return reversedArray;
    }

    private int getAmountOfChunksWithEdge(PImage inputImage, int chunkSize) {
        PVector[] chunks = getChunksWithEdge(sobelEdgeDetection(img), chunkSize);
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
        }
    }

    private String getInputHash() {
        return width + " " + height + " " + magnificationFactor + " " + moveX + " " + moveY + " " + maxIterations + " "
                + resolutionFactor + " " + chunkSize;
    }

    private PImage mandelbrot(int resolutionFactor) {
        PImage img = p.createImage(width / resolutionFactor, height / resolutionFactor, PConstants.RGB);
        for (int x = 0; x < img.width; x++) {
            for (int y = 0; y < img.height; y++) {
                double zR = 0;
                double zI = 0;
                double cR = (x - img.width / 2) / (magnificationFactor * img.width / 4) - moveX;
                double cI = (y - img.height / 2) / (magnificationFactor * img.height / 4) - moveY;
                int iter = maxIterations;
                while (zR * zR + zI * zI < 4 && iter > 0) {
                    double tmp = zR * zR - zI * zI + cR;
                    zI = 2.0f * zR * zI + cI;
                    zR = tmp;
                    iter--;
                }
                img.pixels[x + y * img.width] = p.color(PApplet.map(iter, 0, maxIterations, 0, 255));
            }
        }
        img.resize(width, height);
        return img;
    }

    private PImage julia() {
        PImage img = p.createImage(width / resolutionFactor, height / resolutionFactor, PConstants.RGB);
        double cX = -0.7;
        double cY = 0.27015;
        for (int x = 0; x < img.width; x++) {
            for (int y = 0; y < img.height; y++) {
                double zR = (x - img.width / 2) / (magnificationFactor * img.width / 4) - moveX;
                double zI = (y - img.height / 2) / (magnificationFactor * img.height / 4) - moveY;
                int iter = maxIterations;
                while (zR * zR + zI * zI < 4 && iter > 0) {
                    double tmp = zR * zR - zI * zI + cX;
                    zI = 2.0f * zR * zI + cY;
                    zR = tmp;
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
        float m = (x.length * sumXY - sumX * sumY) / (x.length * sumX2 - sumX * sumX);
        float b = (sumY - m * sumX) / x.length;
        return new float[] { m, b };
    }

}

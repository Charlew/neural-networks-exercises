package sketch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

import perceptron.Example;
import perceptron.Examples;
import perceptron.Perceptron;
import processing.core.PApplet;

public class Window extends PApplet {

    private final static int HEIGHT = 50;
    private final static int WIDTH = 50;
    private final static int LENGTH = 12;
    private final static int LEFT_BOARD_SHIFT = 5;
    private final static int RIGHT_BOARD_SHIFT = 70;
    private Button learnButton;
    private Button removeNoiseButton;
    private Button improveButton;
    private Button previousButton;
    private Button refreshButton;
    private Button nextButton;
    private List<Pixel> leftBoardPixels;
    private List<Pixel> rightBoardPixels;
    private int[] leftBoardFilledPixels = new int[2500];
    private int[] rightBoardFilledPixels = new int[2500];
    private List<Perceptron> perceptrons;
    private List<Example> inputs;
    private int exampleNumberOnTheBoard = 0;

    public void settings() {
        var examples = new Examples();
        leftBoardPixels = new ArrayList<>();
        rightBoardPixels = new ArrayList<>();
        inputs = examples.loadExamplesFromFile();
        perceptrons = new ArrayList<>(2500);
        IntStream.range(0, 2500).forEach(i -> perceptrons.add(new Perceptron(this, i, inputs)));
        size(1500, 900);
        learnButton = new Button(this, 1050, 700, 160, 50, color(255), color(255, 0, 0), "Learn");
        removeNoiseButton = new Button(this, 850, 700, 160, 50, color(255), color(255, 0, 0), "Remove noise");
        improveButton = new Button(this, 1250, 700, 160, 50, color(255), color(255, 0, 0), "Improve");
        previousButton = new Button(this, 60, 700, 160, 50, color(255), color(255, 0, 0), "Previous");
        refreshButton = new Button(this, 280, 700, 160, 50, color(255), color(255, 0, 0), "Refresh");
        nextButton = new Button(this, 500, 700, 160, 50, color(255), color(255, 0, 0), "Next");
    }

    public void setup() {
        generateLeftBoard(exampleNumberOnTheBoard);
        generateRightBoard();
        displayBoards();
    }

    public void draw() {
        learnButton.display();
        removeNoiseButton.display();
        improveButton.display();
        previousButton.display();
        refreshButton.display();
        nextButton.display();
        fillPixelOnTheBoard();
    }

    private Pixel chosenPixel(List<Pixel> pixelList) {
        return pixelList.get(leftBoardPixelNumber());
    }

    private int leftBoardPixelNumber() {
        int selectedPixel = (mouseY / LENGTH - LEFT_BOARD_SHIFT) * WIDTH - LEFT_BOARD_SHIFT + (mouseX / LENGTH);
        return selectedPixel <= 2500 ? selectedPixel : 0;
    }

    private void setPixelState(Pixel pixel) {
        leftBoardFilledPixels[leftBoardPixelNumber()] = pixel.isFilled() ? 1 : 0;
    }

    private void fillPixelOnTheBoard() {
        if (learnButton.mouseOver() || removeNoiseButton.mouseOver() || nextButton.mouseOver() || previousButton.mouseOver()) {
            return;
        }

        if (mouseButton == LEFT && mousePressed) {
            var pixel = chosenPixel(leftBoardPixels).fillPixel();
            setPixelState(pixel);
            pixel.display();
        }

        if (mouseButton == RIGHT && mousePressed) {
            var pixel = chosenPixel(leftBoardPixels).clearPixel();
            setPixelState(pixel);
            pixel.display();
        }
    }

    private void clearLeftBoard() {
        clearBoard(leftBoardFilledPixels, leftBoardPixels);
    }

    private void clearRightBoard() {
        clearBoard(rightBoardFilledPixels, rightBoardPixels);
    }

    private void clearBoard(int[] filledPixelsBoard, List<Pixel> boardPixels) {
        Arrays.fill(filledPixelsBoard, 0);
        boardPixels.forEach(Pixel::clear);
    }

    private void generateLeftBoard(int exampleNumber) {
        generateMesh(leftBoardPixels, LEFT_BOARD_SHIFT);
        generateExampleOnTheBoard(exampleNumber);
    }

    private void generateExampleOnTheBoard(int exampleNumber) {
        for (int i = 0; i < 2500; i++) {
            if (inputs.get(exampleNumber).getRepresentation()[i] == 1) {
                var pixel = leftBoardPixels.get(i).fillPixel();
                leftBoardFilledPixels[i] = 1;
                pixel.display();
            }
        }
    }

    private void generateRightBoard() {
        generateMesh(rightBoardPixels, RIGHT_BOARD_SHIFT);
    }

    private void generateMesh(List<Pixel> rightBoardPixels, int rightBoardShift) {
        for (var i = 0; i < HEIGHT; i++) {
            for (var j = 0; j < WIDTH; j++) {
                rightBoardPixels.add(new Pixel(this, j + rightBoardShift, i, LENGTH));
            }
        }
    }

    public void mousePressed() {
        if (learnButton.mouseOver()) {
            System.out.println("Learning started");
            perceptrons.forEach(perceptron -> CompletableFuture.runAsync(perceptron::train));
        }

        if (nextButton.mouseOver()) {
            clearLeftBoard();
            if (exampleNumberOnTheBoard == 4) {
                exampleNumberOnTheBoard = -1;
            }
            generateLeftBoard(++exampleNumberOnTheBoard);
            clearRightBoard();
            redraw();
        }

        if (refreshButton.mouseOver()) {
            clearLeftBoard();
            generateLeftBoard(exampleNumberOnTheBoard);
        }

        if (previousButton.mouseOver()) {
            clearLeftBoard();
            if (exampleNumberOnTheBoard == 0) {
                exampleNumberOnTheBoard = 5;
            }
            generateLeftBoard(--exampleNumberOnTheBoard);
            clearRightBoard();
            redraw();
        }

        if (removeNoiseButton.mouseOver()) {
            System.out.println("remove noise");
            System.out.println(Arrays.toString(leftBoardFilledPixels));
            for (int i = 0; i < 2500; i++) {
                if (perceptrons.get(i).guess(leftBoardFilledPixels) == 1) {
                    fillRightBoardPixel(i);
                } else {
                    clearRightBoardPixel(i);
                }
            }
            redraw();
        }

        if (improveButton.mouseOver()) {
            for (int i = 0; i < 2500; i++) {
//                for (int j = 0; j < 3; j++) {
                    if (perceptrons.get(i).guess(rightBoardFilledPixels) == 1) {
                        fillRightBoardPixel(i);
                    } else {
                        clearRightBoardPixel(i);
                    }
//                }
            }
            redraw();
        }
    }

    private void clearRightBoardPixel(int iterator) {
        var pixel = rightBoardPixels.get(iterator).clearPixel();
        rightBoardFilledPixels[iterator] = 0;
        pixel.display();
    }

    private void fillRightBoardPixel(int iterator) {
        var pixel = rightBoardPixels.get(iterator).fillPixel();
        rightBoardFilledPixels[iterator] = 1;
        pixel.display();
    }

    private void displayBoards() {
        leftBoardPixels.forEach(Pixel::display);
        rightBoardPixels.forEach(Pixel::display);
    }
}

package sketch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
    private List<Pixel> leftBoardPixels;
    private List<Pixel> rightBoardPixels;
    private int[] filledPixels = new int[2500];
    private List<Perceptron> perceptrons;
    private Examples examples;
    private List<Example> inputs;

    public void settings() {
        examples = new Examples();
        leftBoardPixels = new ArrayList<>();
        rightBoardPixels = new ArrayList<>();
        inputs = examples.loadExamplesFromFile();
        perceptrons = new ArrayList<>(2500);
        IntStream.range(0, 2500).forEach(i -> perceptrons.add(new Perceptron(this, i, inputs)));
        size(1500, 900);
        learnButton = new Button(this, 1100, 700, 160, 50, color(255), color(255, 0, 0), "Learn");
        removeNoiseButton = new Button(this, 900, 700, 160, 50, color(255), color(255, 0, 0), "Remove noise");
    }

    public void setup() {
        generateLeftBoard();
        generateRightBoard();
        displayBoards();
    }

    public void draw() {
        learnButton.display();
        removeNoiseButton.display();
        fillPixel();
    }

    private Pixel chosenPixel(List<Pixel> pixelList) {
        return pixelList.get(leftBoardPixelNumber());
    }

    private int leftBoardPixelNumber() {
        return (mouseY / LENGTH - LEFT_BOARD_SHIFT) * WIDTH - LEFT_BOARD_SHIFT + (mouseX / LENGTH);
    }

    private void setPixelState(Pixel pixel) {
        filledPixels[leftBoardPixelNumber()] = pixel.isFilled() ? 1 : 0;
    }

    private void fillPixel() {
        if (learnButton.mouseOver() || removeNoiseButton.mouseOver()) {
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

    private void generateLeftBoard() {
        generateMesh(leftBoardPixels, LEFT_BOARD_SHIFT);

        // test 2

        for (int i = 0; i < leftBoardPixels.size(); i++) {
            if (inputs.get(0).getRepresentation()[i] == 1) {
                var pixel = leftBoardPixels.get(i).fillPixel();
                filledPixels[i] = 1;
                pixel.display();
            }

        }
        System.out.println(Arrays.toString(filledPixels));
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
            perceptrons.forEach(Perceptron::train);
            System.out.println("Learning finished");
        }
    }

    private void displayBoards() {
        leftBoardPixels.forEach(Pixel::display);
        rightBoardPixels.forEach(Pixel::display);
    }

    private int controlPanelEdge() {
        return WIDTH * (LENGTH + 2) + LENGTH;
    }
}

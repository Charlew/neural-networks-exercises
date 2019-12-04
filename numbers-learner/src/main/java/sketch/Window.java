package sketch;

import perceptron.Perceptron;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Window extends PApplet {

    private final static int HEIGHT = 7;
    private final static int WIDTH = 5;
    private final static int LENGTH = 100;
    private Button clearButton;
    private Button learnButton;
    private Button guessButton;
    private List<Pixel> pixelList;
    private int[] filledPixels = new int[35];
    private List<Perceptron> perceptrons = new ArrayList<>(10);

    public void settings() {
        IntStream.range(0, 10).forEach(i -> perceptrons.add(new Perceptron(this, i)));
        pixelList = new ArrayList<>();
        size(WIDTH * LENGTH + 200, HEIGHT * LENGTH);
        clearButton = new Button(this,controlPanelEdge() + 75, 100, 160, 50, color(255), color(255, 0, 0), "Clear");
        learnButton = new Button(this,controlPanelEdge() + 75, 250, 160, 50, color(255), color(255, 0, 0), "Learn");
        guessButton = new Button(this,controlPanelEdge() + 75, 400, 160, 50, color(255), color(255, 0, 0), "Guess");
    }

    public void setup() {
        generateBoard();
        displayBoard();
    }

    public void draw() {
        clearButton.display();
        learnButton.display();
        guessButton.display();
    }

    private void generateBoard() {
        for (var i = 0; i < HEIGHT; i++) {
            for (var j = 0; j < WIDTH; j++) {
                pixelList.add(new Pixel(this, j, i, LENGTH));
            }
        }
    }

    public void mousePressed() {
        if (!clearButton.mouseOver() && !learnButton.mouseOver() && !guessButton.mouseOver()) {
            fillPixel();
        } else if (learnButton.mouseOver()) {
            perceptrons.forEach(Perceptron::train);
        } else if (guessButton.mouseOver()) {
            System.out.println("==============================================");
            perceptrons.forEach(perceptron -> {
                if (perceptron.guess(filledPixels) == 1) {
                    System.out.println(perceptron.getId());
                }
            });
            System.out.println("==============================================");
        } else if (clearButton.mouseOver()) {
            clearBoard();
        }
    }

    private void fillPixel() {
        var pixel = chosenPixel().switchState();

        filledPixels[pixelNumber()] = pixel.isFilled() ? 1 : 0;

        pixel.display();
    }

    private Pixel chosenPixel() {
        return pixelList.get(pixelNumber());
    }

    private int pixelNumber() {
        return (mouseY / LENGTH) * WIDTH + (mouseX / LENGTH);
    }

    private void displayBoard() {
        pixelList.forEach(Pixel::display);
    }

    private void clearBoard() {
        Arrays.fill(filledPixels, 0);
        pixelList.forEach(Pixel::clear);
    }

    private int controlPanelEdge() {
        return (WIDTH * LENGTH) - (LENGTH / 2);
    }
}

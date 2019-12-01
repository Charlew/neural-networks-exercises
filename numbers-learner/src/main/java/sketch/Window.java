package sketch;

import perceptron.Perceptron;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Window extends PApplet {

    private final static int HEIGHT = 7;
    private final static int WIDTH = 5;
    private final static int LENGTH = 100;
    private Button clearButton;
    private Button learnButton;
    private List<Pixel> pixelList;
    private int[] filledPixels = new int[35];
    private Perceptron perceptron;

    public void settings() {
        perceptron = new Perceptron();
        pixelList = new ArrayList<>();
        size(WIDTH * LENGTH + 200, HEIGHT * LENGTH);
        clearButton = new Button(this,controlPanelEdge() + 75, 100, 160, 50, color(255), color(255, 0, 0), "Clear");
        learnButton = new Button(this,controlPanelEdge() + 75, 400, 160, 50, color(255), color(255, 0, 0), "Learn");
    }

    public void setup() {
        generateBoard();
        displayBoard();
    }

    public void draw() {
        clearButton.display();
        learnButton.display();
    }

    private void generateBoard() {
        for (var i = 0; i < HEIGHT; i++) {
            for (var j = 0; j < WIDTH; j++) {
                pixelList.add(new Pixel(this, j, i, LENGTH));
            }
        }
    }

    public void mousePressed() {
        if (!clearButton.mouseOver() && !learnButton.mouseOver()) {
            var pixel = chosenPixel().switchState();
            if (pixel.isFilled()) {
                filledPixels[pixelNumber()] = 1;
            } else {
                filledPixels[pixelNumber()] = 0;
            }
            pixel.display();
        } else if (learnButton.mouseOver()) {
//            System.out.println(Arrays.toString(filledPixels));
            perceptron.train(0); // 0
            perceptron.train(1); // 0
            perceptron.train(2); // 1
            perceptron.train(3); // 1
            perceptron.train(4); // 2
            perceptron.train(5); // 2
            perceptron.train(6); // 3
            perceptron.train(7); // 3
        } else if (clearButton.mouseOver()) {
            clearBoard();
        }
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
        pixelList.forEach(Pixel::clear);
    }

    private int controlPanelEdge() {
        return (WIDTH * LENGTH) - (LENGTH / 2);
    }

    public int[] getFilledPixels() {
        return filledPixels;
    }
}

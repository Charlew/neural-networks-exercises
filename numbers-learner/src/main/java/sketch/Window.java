package sketch;

import perceptron.Perceptron;
import processing.core.PApplet;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

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
        for (int i = 0; i < 10; i++) {
            perceptrons.add(new Perceptron());
        }
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
            var pixel = chosenPixel().switchState();
            if (pixel.isFilled()) {
                filledPixels[pixelNumber()] = 1;
            } else {
                filledPixels[pixelNumber()] = 0;
            }
            pixel.display();
        } else if (learnButton.mouseOver()) {
            System.out.println("Training started at " + LocalTime.now());

            for (int i = 0; i < perceptrons.size(); i++) {
                perceptrons.get(i).setId(i);
                perceptrons.get(i).train(i * 2, i * 2 + 1);
            }

            System.out.println("Training finished at " + LocalTime.now());
        } else if (guessButton.mouseOver()) {
            perceptrons.forEach(perceptron -> {
                if (perceptron.guess(filledPixels) == 1) {
                    System.out.println(perceptron.getId());
                }
            });
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

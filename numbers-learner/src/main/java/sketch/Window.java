package sketch;

import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;

public class Window extends PApplet {

    private final static int HEIGHT = 7;
    private final static int WIDTH = 5;
    private final static int LENGTH = 100;
    private Button clearButton;
    private List<Pixel> pixelList;

    public void settings() {
        pixelList = new ArrayList<>();
        size(WIDTH * LENGTH + 200, HEIGHT * LENGTH);
        clearButton = new Button(this,controlPanelEdge() + 75, 100, 160, 50, color(255), color(255, 0, 0), "Clear");
    }

    public void setup() {
        generateBoard();
        displayBoard();
    }

    public void draw() {
        clearButton.display();
        if (mousePressed && clearButton.mouseOver()) {
            clearBoard();
        }
    }

    private void generateBoard() {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                pixelList.add(new Pixel(this, i, j, LENGTH));
            }
        }
    }

    public void mousePressed() {
        if (!clearButton.mouseOver()) {
            var pixel = chosenPixel().switchState();
            pixel.display();
        }
    }

    private Pixel chosenPixel() {
        return pixelList.get((mouseX / LENGTH) * HEIGHT + (mouseY / LENGTH));
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
}

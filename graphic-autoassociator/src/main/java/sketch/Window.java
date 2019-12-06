package sketch;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;

public class Window extends PApplet {

    private final static int HEIGHT = 50;
    private final static int WIDTH = 50;
    private final static int LENGTH = 15;
    private Button learnButton;
    private Button removeNoiseButton;
    private List<Pixel> pixelList;

    public void settings() {
        pixelList = new ArrayList<>();
        size(WIDTH * LENGTH + 200, HEIGHT * LENGTH);
        learnButton = new Button(this,controlPanelEdge(), 250, 160, 50, color(255), color(255, 0, 0), "Learn");
        removeNoiseButton = new Button(this,controlPanelEdge(), 400, 160, 50, color(255), color(255, 0, 0), "Remove noise");
    }

    public void setup() {
        generateBoard();
        displayBoard();
    }

    public void draw() {
        learnButton.display();
        removeNoiseButton.display();
    }

    private void generateBoard() {
        for (var i = 0; i < HEIGHT; i++) {
            for (var j = 0; j < WIDTH; j++) {
                pixelList.add(new Pixel(this, j, i, LENGTH));
            }
        }
    }

    public void mousePressed() {
        if (learnButton.mouseOver()) {
            System.out.println("Learning");
        }
    }

    private void displayBoard() {
        pixelList.forEach(Pixel::display);
    }

    private int controlPanelEdge() {
        return WIDTH * LENGTH + LENGTH;
    }
}

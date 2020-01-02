package sketch;

import processing.core.PApplet;

public final class Pixel {

    private final PApplet sketch;
    private final int x;
    private final int y;
    private final int length;
    private boolean filled;

    Pixel(PApplet sketch, int x, int y, int length) {
        this.sketch = sketch;
        this.x = x;
        this.y = y + 5;
        this.length = length;
    }

    boolean isFilled() {
        return filled;
    }

    void display() {
        sketch.fill(isFilled() ? 0 : 255);
        sketch.rect(x * length, y * length, length, length);
    }

    public Pixel fillPixel() {
        filled = true;
        return this;
    }

    public Pixel clearPixel() {
        filled = false;
        return this;
    }

    void clear() {
        filled = false;
        display();
    }
}

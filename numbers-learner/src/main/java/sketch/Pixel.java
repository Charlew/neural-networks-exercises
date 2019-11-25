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
        this.y = y;
        this.length = length;
    }

    private boolean isFilled() {
        return filled;
    }

    public void setFilled(boolean filled) {
        this.filled = filled;
    }

    void display() {
        if (isFilled()) {
            sketch.fill(0);
        } else {
            sketch.fill(255);
        }
        sketch.rect(x * length, y * length, length, length);
    }

    public Pixel switchState() {
        filled = !filled;
        return this;
    }

    void clear() {
        filled = false;
        display();
    }
}

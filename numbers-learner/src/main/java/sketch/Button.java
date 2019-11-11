package sketch;

import processing.core.PApplet;

class Button {

    private PApplet sketch;
    private final int x, y, w, h;
    private final int color;
    private final int cOver;
    private final String txt;

    Button(PApplet sketch, int x, int y, int w, int h, int color, int cOver, String txt) {
        this.sketch = sketch;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.color = color;
        this.cOver = cOver;
        this.txt = txt;
    }

    void display() {
        sketch.pushStyle();
        if (mouseOver()) {
            sketch.fill(cOver);
        } else {
            sketch.fill(color);
        }
        sketch.stroke(100);
        sketch.strokeWeight(2);
        sketch.rect(x, y, w, h, 10);
        sketch.fill(0);
        int textSize = 18;
        sketch.textSize(textSize);
        sketch.textAlign(sketch.CENTER);
        sketch.text(txt, x + w / 2, y + h / 2 + textSize / 2);
        sketch.popStyle();
    }

    boolean mouseOver() {
        return (sketch.mouseX >= x && sketch.mouseX <= (x + w) && sketch.mouseY >= y && sketch.mouseY <= (y + h));
    }
}

package perceptron;

import java.util.stream.IntStream;

import processing.core.PApplet;

public class Perceptron {

    private final PApplet sketch;
    private final int id;
    private float[] weights = new float[35];
    private float threshold;
    private static final float LEARNING_RATE = 0.01f;
    private static final int MAX_ITERATIONS = 3000;

    public Perceptron(PApplet sketch, int id) {
        this.sketch = sketch;
        this.id = id;
        IntStream.range(0, weights.length).forEach(i -> weights[i] = sketch.random(-1, 1));
        threshold = sketch.random(-1, 1);
    }

    public int getId() {
        return id;
    }

    public ExampleNumber randomExample() {
        var examples = Examples.inputs;
        var number = (int) sketch.random(0, examples.size());
        return examples.get(number);
    }

    public void train() {
        var maxCounter = 0;
        var counter = 0;
        for (int i = 0; i < MAX_ITERATIONS; i++) {
            var example = randomExample();
            var exampleRepresentation = example.getRepresentation();
            var target = example.getNumber() == id ? 1 : -1;
            var guess = guess(exampleRepresentation);
            var error = target - guess;

            if (error == 0) {
                //
                counter++;
                if (counter > maxCounter) {
//                    var tmpWeights = new float[35];
                    maxCounter = counter;
                }
            } else {
                improve(exampleRepresentation, error);
            }

        }
        for (int i = 0; i < Examples.inputs.size(); i++) {
            int result = guess(Examples.inputs.get(i).getRepresentation());
            var target = Examples.inputs.get(i).getNumber() == id ? 1 : -1;

            if (result == target) {
                counter++;
            }
        }
        System.out.println("Perceptron with id = " + id + " guess " + counter + "/" + Examples.inputs.size() + " examples");
    }

    private void improve(int[] exampleRepresentation, int error) {
        for (int j = 0; j < weights.length; j++) {
            weights[j] += LEARNING_RATE * error * exampleRepresentation[j];
        }
        threshold -= LEARNING_RATE * error;
    }

    public int guess(int[] inputs) {
        var sum = IntStream.range(0, weights.length)
            .map(i -> (int) (inputs[i] * weights[i]))
            .sum();

        return (int) Math.signum(sum - threshold);
    }
}

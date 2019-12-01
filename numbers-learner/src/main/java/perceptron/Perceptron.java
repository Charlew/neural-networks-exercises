package perceptron;

import java.util.stream.IntStream;

public class Perceptron {

    private int id;
    private int[] weights = new int[35];
    private int threshold;
    private static final float LEARNING_RATE = (float) 0.1;
    private static final int MAX_ITERATIONS = 300;

    public Perceptron() {
        IntStream.range(0, weights.length).forEach(i -> weights[i] = randomNumberInRange(-1, 1));
        threshold = randomNumberInRange(-1, 1);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ExampleNumber randomExample(int from, int to) {
        var examples = Examples.inputs;
        var number = randomNumberInRange(from, to);
        return examples.get(number);
    }

    public void train(int from, int to) {
        for (int i = 0; i < MAX_ITERATIONS; i++) {
            var example = randomExample(from, to);
            var exampleRepresentation = example.getRepresentation();
            var exampleTarget = example.getNumber();
            var guess = guess(exampleRepresentation);
            var error = exampleTarget - guess;

            if (error == 0) {
                // continue
            } else {
                for (int j = 0; j < weights.length; j++) {
                    weights[j] += LEARNING_RATE * error * exampleRepresentation[j];
                }
                threshold -= LEARNING_RATE * error;
            }
        }
    }

    public int guess(int[] inputs) {
        var sum = IntStream.range(0, weights.length)
                .map(i -> inputs[i] * weights[i])
                .sum();

        return (int) Math.signum(sum - threshold);
    }

    private static int randomNumberInRange(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        return (int) (Math.random() * ((max - min) + 1)) + min;
    }
}

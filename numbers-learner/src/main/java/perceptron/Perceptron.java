package perceptron;

import java.util.stream.IntStream;

public class Perceptron {
    private int[] weights = new int[35];
    private int threshold;
    private static final float LEARNING_RATE = (float) 0.1;
    private static final int MAX_ITERATIONS = 300;

    public Perceptron() {
        IntStream.range(0, weights.length).forEach(i -> weights[i] = getRandomNumberInRange(-1, 1));
        threshold = getRandomNumberInRange(-1, 1);

    }

    public int[] getWeights() {
        return weights;
    }

    public ExampleNumber randomExample(int from, int to) {
        var examples = Examples.inputs;
        var number = getRandomNumberInRange(from, to);
        return examples.get(number);
    }

    public void train(int numberOfExample) {
        for (int i = 0; i < MAX_ITERATIONS; i++) {
            var example = Examples.inputs.get(numberOfExample);
            var exampleRepresentation = example.getRepresentation();
            var exampleTarget = example.getNumber();
            var guess = guess(exampleRepresentation);
            var error = exampleTarget - guess;

            System.out.println(exampleTarget);

            if (error == 0) {
                // continue
//                System.out.println(exampleTarget);
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

    private static int getRandomNumberInRange(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        return (int)(Math.random() * ((max - min) + 1)) + min;
    }
}

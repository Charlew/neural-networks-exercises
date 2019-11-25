package perceptron;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

public class Perceptron {
    private int[] weights = new int[35];
    private int threshold;
    private static float LEARNING_RATE = (float) 0.1;

    public Perceptron() {
        Arrays.stream(this.weights).forEach(weight -> getRandomNumberInRange(-1, 1));
    }

    public int[] getWeights() {
        return weights;
    }

    public ExampleNumber randomExample() {
        var examples = Examples.getInputs();
        var random = new Random();
        return examples.get(random.nextInt(examples.size()));
    }

    public void train(int[] inputs, int target) {
        var guess = guess(inputs);
        var error = target - guess;

        IntStream.range(0, weights.length).forEach(i -> weights[i] += error * inputs[i] * LEARNING_RATE);
    }

    private int guess(int[] inputs) {
        var sum = IntStream.range(0, weights.length).map(i -> inputs[i] * weights[i]).sum();
        return (int) Math.signum(sum);
    }

    private static int getRandomNumberInRange(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        return (int)(Math.random() * ((max - min) + 1)) + min;
    }
}

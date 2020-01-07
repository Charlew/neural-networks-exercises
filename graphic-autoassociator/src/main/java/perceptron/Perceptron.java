package perceptron;

import processing.core.PApplet;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Perceptron {

    private final PApplet sketch;
    private final int pixelId;
    private float[] weights = new float[2500];
    private float[] pocket = new float[2500];
    private float threshold;
    private static final float LEARNING_RATE = 0.1f;
    private static final int MAX_ITERATIONS = 10_000;
    private List<Example> examples;

    public Perceptron(PApplet sketch, int pixelId, List<Example> examples) {
        this.sketch = sketch;
        this.pixelId = pixelId;
        this.examples = examples;
        IntStream.range(0, weights.length).forEach(i -> weights[i] = sketch.random(-1, 1));
        threshold = sketch.random(-1, 1);
    }

    public int getPixelId() {
        return pixelId;
    }

    public Example randomExample() {
        var number = (int) sketch.random(0, 5);
        return examples.get(number);
    }

    public void train() {
        var maxCounter = 0;
        var counter = 0;
        var iterationsWithoutError = 0;

        for (int i = 0; i < MAX_ITERATIONS; i++) {
            var example = randomExample();
            var exampleRepresentation = example.getRepresentation();
            var target = exampleRepresentation[pixelId] == 1 ? 1 : -1;
            var guess = guess(exampleRepresentation);
            var error = target - guess;

            if (error == 0) {
                counter++;
                iterationsWithoutError++;

                if (iterationsWithoutError > 1500) {
                    if (counter > maxCounter) {
                        pocket = Arrays.copyOf(weights, weights.length);
                    }
                    break;
                }
            } else {
                improve(exampleRepresentation, error);

                if (counter > maxCounter) {
                    maxCounter = counter;
                    pocket = Arrays.copyOf(weights, weights.length);
                }

                counter = 0;
                iterationsWithoutError = 0;
            }
        }
        weights = Arrays.copyOf(pocket, pocket.length);

        if (pixelId == 2499) {
            System.out.println("Learning finished");
        }

        showLearningProcess();
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

    private void showLearningProcess() {
        var iterator = 0;
        for (int i = 0; i < 5; i++) {
            int result = guess(examples.get(i).getRepresentation());
            var target = examples.get(i).getRepresentation()[pixelId] == 1 ? 1 : -1;

            if (result == target) {
                iterator++;
            }
        }
//        System.out.println("Perceptron with id = " + pixelId + " guess " + iterator + "/" + examples.size() + " examples");
    }
}

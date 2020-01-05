package perceptron;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Examples {

    public List<Example> loadExamplesFromFile() {
        try(var scanner = new Scanner(new File("src/main/java/inputs.txt"))) {
            var examples = new ArrayList<Example>();

            for (int i = 0; i < 5; i++) {
                var input = new int[2500];

                for (int j = 0; j < input.length; j++) {
                    input[j] = scanner.nextInt();
                }
                examples.add(new Example(input));
            }
            return examples;
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
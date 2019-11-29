package perceptron;

public class ExampleNumber {

    private int number;
    private int[] representation;

    public ExampleNumber(int number, int[] representation) {
        this.number = number;
        this.representation = representation;
    }

    public int getNumber() {
        return number;
    }

    public int[] getRepresentation() {
        return representation;
    }
}
